### auto-commit

django 는 디폴트로 `auto-commit`을 하도록 설정되어 있다. `auto-commit`은
 1개의 sql 쿼리를 실행할 때 마다 트랜잭션을 열고 쿼리의 성공 여부에 따라 트랜잭션을 커밋하거나 롤백하도록 보장한다.
 
 ```python
DATABASES = {
    'default': {
        'ENGINE': 'django.contrib.gis.db.backends.postgis',
        'HOST': 'localhost',
        'NAME': 'test',
        'PASSWORD': '',
        'PORT': '',
        'USER': 'postgres',
        'AUTOCOMMIT': False,
    },
}
```
위와 같이 `settings`의 `DATABASES` 상수에 설정 가능하다.

```python
from datetime import datetime

from application.models import Question


def update_bulk_with_auto_commit(tog):
    start = datetime.now()
    questions = Question.objects.all()
    for qu in questions:
        qu.content = f'question updated with auto-commit {tog}.'
        qu.save()
    end = datetime.now()
    print(f'auto-commit is {tog}: ', end - start)
```

1만개의 row를 수정하는 `update_bulk_with_auto_commit` 함수를 정의했다.
`AUTOCOMMIT`을 각각 `True`와 `False`로 설정하고나서 함수를 호출한 결과는 아래와 같았다.

```
auto-commit is on:  0:00:03.688916
auto-commit is off:  0:00:03.209877
```

`auto-commit`을 이용하도록 설정할 때보다 하지않았을 때가 성능이 더 좋은 것으로
나타나지만 공식문서에서는 `auto-commit`을 이용하도록 권장한다.
> **Deactivating transaction management** <br>
You can totally disable Django’s transaction management for a given database by setting AUTOCOMMIT to False in its configuration. 
If you do this, Django won’t enable autocommit, and won’t perform any commits. 
You’ll get the regular behavior of the underlying database library. 
This requires you to commit explicitly every transaction, even those started by Django or by third-party libraries. 
Thus, this is best used in situations where you want to run your own transaction-controlling middleware or do something really strange.

### atomic

`transaction.atomic()`은 원자성을 보장하는 코드 블럭을 만들어준다.
`atomic`을 걸어주면 트랜잭션을 열기 전에 savepoint를 생성한다.
`savepoint`는 트랜잭션이 롤백될 경우 특정 상태로 돌아가기 위한 기준점이 된다.


트랜잭션이 예외를 발생시키지 않고 성공적으로 수행되면 savepoint는 release된다.
```
(0.000) | SAVEPOINT "s140735595205504_x1"

(0.001) | SELECT "application_question"."id",
        |        "application_question"."content",
        |        "application_question"."created_at",
        |        "application_question"."updated_at"
        | FROM "application_question"
        | WHERE "application_question"."id" <= 3

(0.001) | UPDATE "application_question"
        | SET "content" = '3.',
        |     "created_at" = '2019-03-17T12:19:07.736442+00:00'::timestamptz,
        |     "updated_at" = '2019-03-17T14:49:15.906878+00:00'::timestamptz
        | WHERE "application_question"."id" = 3

(0.000) | UPDATE "application_question"
        | SET "content" = '3.',
        |     "created_at" = '2019-03-17T12:19:07.732893+00:00'::timestamptz,
        |     "updated_at" = '2019-03-17T14:49:15.911091+00:00'::timestamptz
        | WHERE "application_question"."id" = 1

(0.000) | UPDATE "application_question"
        | SET "content" = '3.',
        |     "created_at" = '2019-03-17T12:19:07.735481+00:00'::timestamptz,
        |     "updated_at" = '2019-03-17T14:49:15.915014+00:00'::timestamptz
        | WHERE "application_question"."id" = 2

(0.000) | release SAVEPOINT "s140735595205504_x1"

```
반면에 트랜잭션 실행 도중 예외가 발생할 경우 savepoint로 지정된 곳 까지 롤백 후 savepoint를 release한다.
```python
from datetime import datetime

from django.db import transaction

from application.models import Question

@transaction.atomic
def update_bulk_with_auto_commit():
    questions = Question.objects.all()
    for qu in questions:
        qu.content = '3'
        qu.save()
        if qu.id == 500:
            raise Exception()  # 롤백를 야기하는 예외

```
```
(0.000) | SAVEPOINT "s140735595205504_x1"

(0.001) | SELECT "application_question"."id",
        |        "application_question"."content",
        |        "application_question"."created_at",
        |        "application_question"."updated_at"
        | FROM "application_question"
        | WHERE "application_question"."id" <= 3

(0.000) | UPDATE "application_question"
        | SET "content" = '3',
        |     "created_at" = '2019-03-17T12:19:07.736442+00:00'::timestamptz,
        |     "updated_at" = '2019-03-17T14:54:35.400521+00:00'::timestamptz
        | WHERE "application_question"."id" = 3

(0.000) | UPDATE "application_question"
        | SET "content" = '3',
        |     "created_at" = '2019-03-17T12:19:07.732893+00:00'::timestamptz,
        |     "updated_at" = '2019-03-17T14:54:35.404071+00:00'::timestamptz
        | WHERE "application_question"."id" = 1

(0.000) | UPDATE "application_question"
        | SET "content" = '3',
        |     "created_at" = '2019-03-17T12:19:07.735481+00:00'::timestamptz,
        |     "updated_at" = '2019-03-17T14:54:35.407205+00:00'::timestamptz
        | WHERE "application_question"."id" = 500

(0.000) | ROLLBACK TO SAVEPOINT "s140735595205504_x1"

(0.000) | release SAVEPOINT "s140735595205504_x1"

```
`atomic`으로 쿼리들에 원자성을 부여했기 때문에 `content`를 `3`으로 수정했던 쿼리들은 모두 롤백되었으므로 `content`가 3 row는 . 
하지만 `auto-commit`을 사용하는 상황애서는 예외가 발생하기 전 까지 커밋된 쿼리들이 있기 때문에 `content`가 3으로 수정된 row들이 있을 것이다. 
