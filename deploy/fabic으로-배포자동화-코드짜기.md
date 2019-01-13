## Fabric 으로 배포 자동화 코드 짜기

배포를 할 때는 리모트 리퍼지토리에서 최신버전 땡겨오기, 패키지 설치하기, DB에 마이그레이션하기(Django의 경우)등 일련의 작업들이 필요하다. 이 작업들을 하려면 대부분 쉘 명령어를 입력해야하고 배포를 할 때마다 매번 여러 명령어들을 치는 것은 번거롭다. 그래서 하나의 명령어로 작업들을 수행할 수 있도록 [Fabric](https://www.fabfile.org/)이라는 걸 사용해봤다.  
<br>
Fabric api가 제공하는 [env 딕셔너리](http://docs.fabfile.org/en/1.14/usage/env.html)에 몇 가지 값을 설정할 수 있다.
아래의 값을 정의하면 값에 해당하는 배포환경을 Fabric이 알아서 접속한다.
```python
from fabric.api import env

env.host = '배포 서버의 IP/도메인 주소'
env.user = '배포 서버에 ssh 접속 시 입력하는 유저이름'
env.password = '배포 서버에 ssh 접속 시 입력하는 비밀번호'
```
<br>

Fabric을 이용하여 배포와 관련된 코드를 짜려면 `fabfile`라는 이름을 가진 파이썬 파일에 코드를 작성해야 한다. 이 파일에 `setup()`, `deploy()` 와 같은 함수들을 정의하고 `$ fab setup`, `$ fab deploy` 명령어를 통해 함수들을 실행하는 구조로 되어있다. 
<br><br>
먼저 아무런 준비가 되지 않은 서버에 첫 셋팅을 할 경우에는 `fabfile.py`에 `setup()`이라는 이름의 함수를 정의하고 `$ fab setup --fabfile=부모경로/fabfile.py` 명령어를 실행한다. `--fabfile`옵션에는 `fabfile.py`의 위치를 지정할 수 있다. `setup()`에는 언어/프레임워크/가상환경 설치 및 설정, 프로젝트 디렉토리 생성 등이 포함된다.  

```python
def setup():
    _update_apt()
    _install_apt_requirements(apt_requirements=APT_REQUIREMENTS)
    _make_virtualenv_settings(remote_user=REMOTE_USER)
    _make_virtualenv(env_name=REMOTE_VIRTUAL_ENV)
    _make_project_dir(project_dir=REMOTE_PROJECT_DIR, remote_user=REMOTE_USER)

```

`setup()`을 통해 배포서버에 필요한 것들을 셋팅했다면 주기적인 버전 업그레이드, 버그에 의한 롤백 등을 배포 서버에 반영할 때는 `deploy`라는 이름을 가진 함수에 필요한 것들을 정의하고 `$ fab deploy` 명령어를 사용한다. 
```python
from fabric.api import run

def deploy():
    # 배포용 서버에서 가상환경에 진입.
    run(f'workon {REMOTE_VIRTUAL_ENV}')
    
    # 리퍼지토리가 있는 위치로 이동.
    with cd(f'/home/{REMOTE_USER}/{REMOTE_PROJECT_DIR}'):
         # 리모트 리퍼지토리에서 코드 pull.
        _get_latest_source(repo_dir=PROJECT_NAME, repo_url=REPO_URL)
        
        with cd(f'{PROJECT_NAME}'):
            _install_pip_requirements(remote_user=REMOTE_USER, virtual_env=REMOTE_VIRTUAL_ENV)
            _collect_staticfiles(remote_user=REMOTE_USER, virtual_env=REMOTE_VIRTUAL_ENV)
            _migrate_model_to_db(remote_user=REMOTE_USER, virtual_env=REMOTE_VIRTUAL_ENV)

```
위의 `deploy()`함수를 실행하면 `setup()`을 통해 설정한 가상환경에 진입하고 리퍼지토리 위치로 이동하여 pip설치, 정적파일 nginx에서 제공할 정적파일 수집, 모델 마이그레이션을 차례대로 할 수 있다. 위 코드에서 `run()` 함수는 인자로 들어오는 쉘 명령어를 배포 서버에서 실행하도록 한다. 로컬에서 쉘 명령어를 실행하고 싶다면 `local()` 함수를 사용한다. 
<br>
아래의 코드는 첫 셋팅부터 배포까지 필요한 쉘 명령어들을 fabric을 이용하여 작성한 것이다. nginx 관련 작업들은 생략되어 있다.

```python
from json import load

from fabric.context_managers import cd
from fabric.contrib.files import exists
from fabric.api import env, run, sudo

with open('deploy/compute.json') as data:
    DEPLOY_INFO = load(data)

PROJECT_NAME = 'Fabric Test'
REPO_URL =  DEPLOY_INFO['REPO_URL']

REMOTE_HOST = DEPLOY_INFO['REMOTE_HOST']
REMOTE_USER = DEPLOY_INFO['REMOTE_USER']
REMOTE_PASSWORD = DEPLOY_INFO['REMOTE_PASSWORD']

REMOTE_PROJECT_DIR = PROJECT_NAME + '-DIR'
REMOTE_VIRTUAL_ENV = PROJECT_NAME + '-ENV'

APT_REQUIREMENTS = [
    'git',
    'python3.6',
    'virtualenv',
    'virtualenvwrapper',
    'pip3',
]

env.user = REMOTE_USER
env.password = REMOTE_PASSWORD
env.hosts = [
    REMOTE_HOST, 
]


def _update_apt():
    sudo('apt-get update')
    sudo('apt-get -y upgrade')


def _install_apt_requirements(apt_requirements):
    requirements = ' '.join(apt_requirements)
    sudo(f'apt-get install -y {requirements}')


def _make_virtualenv_settings(remote_user):
    if not exists('~/.virtualenvs'):
        run(f'mkdir /home/{remote_user}/.virtualenvs')

        script = f'''"# python virtualenv settings
                      export WORKON_HOME=/home/{remote_user}/.virtualenvs
                      export VIRTUALENVWRAPPER_PYTHON="$(which python3)"  # location of python3
                      source /home/{remote_user}/.local/bin/virtualenvwrapper.sh"'''
        run(f'echo {script} >> /home/{remote_user}/.bashrc')
        run(f'source /home/{remote_user}/.bashrc')


def _make_virtualenv(env_name):
    run(f'mkvirtualenv {env_name} --python=$(which python3)')


def _make_project_dir(project_dir, remote_user):
    run(f'mkdir /home/{remote_user}/{project_dir}')


def setup():
    _update_apt()
    _install_apt_rerements(apt_requirements=APT_REQUIREMENTS)
    _make_virtualenv_settings(remote_user=REMOTE_USER)
    _make_virtualenv(env_name=REMOTE_VIRTUAL_ENV)
    _make_project_dir(project_dir=REMOTE_PROJECT_DIR, remote_user=REMOTE_USER)


def _get_latest_source(repo_dir, repo_url):
    if exists(f'{repo_dir}'):
        with cd(f'{repo_dir}'):
            run('git fetch')
    else:
        run(f'git clone {repo_url}')


def _install_pip_requirements(remote_user, virtual_env):
    run(f'/home/{remote_user}/.virtualenvs/{virtual_env}/bin/pip3 install -r requirements.txt')


def _collect_staticfiles(remote_user, virtual_env):
    run(f'/home/{remote_user}/.virtualenvs/{virtual_env}/bin/python manage.py collectstatic --noinput')


def _migrate_model_to_db(remote_user, virtual_env):
    run(f'/home/{remote_user}/.virtualenvs/{virtual_env}/bin/python manage.py migrate --noinput')


def deploy():
    run(f'workon {REMOTE_VIRTUAL_ENV}')
    with cd(f'/home/{REMOTE_USER}/{REMOTE_PROJECT_DIR}'):
        _get_latest_source(repo_dir=PROJECT_NAME, repo_url=REPO_URL)
        with cd(f'{PROJECT_NAME}'):
            _install_pip_requirements(remote_user=REMOTE_USER, virtual_env=REMOTE_VIRTUAL_ENV)
            _collect_staticfiles(remote_user=REMOTE_USER, virtual_env=REMOTE_VIRTUAL_ENV)
            _migrate_model_to_db(remote_user=REMOTE_USER, virtual_env=REMOTE_VIRTUAL_ENV)
```
참고 : https://www.obeythetestinggoat.com/book/chapter_automate_deployment_with_fabric.html
