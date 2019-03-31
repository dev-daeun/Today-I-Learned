
### mapping object

`dict`는 다양한 방식으로 정의할 수 있다.<br>

```python
>>> a = dict(one=1, two=2, three=3)
>>> b = {'one': 1, 'two': 2, 'three': 3}
>>> c = dict(zip(['one', 'two', 'three'], [1, 2, 3]))
>>> d = dict([('one', 1), ('two', 2), ('three', 3)])
>>> e = dict({'three': 3, 'two': 2, 'one': 1})
>>> a == b == c == d == e
True
```
`dict`은 `mapping` 객체 중 하나에 해당하며 `mapping`객체의 정의는 아래와 같다.
<br>
> 임의의 키 검색을 지원하고 `Mapping` 또는 `MutableMapping`추상클래스의 메소드를 재정의한 `container`객체를 의미합니다. `mapping`객체의 예시로 `dict`, `collections.defaultdict`, `collections.OrderedDict` 그리고  `collections.Counter`가 있습니다.
<br>

아래 코드를 통해 `dict`는 `MutableMapping`클래스의 객체임을 알 수 있다.<br>

```python
>>> import collections
>>> my_dict = {}
>>> isinstance(my_dict, collections.MutableMapping)
True
```
`MutableMapping`클래스는 `Mapping`클래스를 상속받고 `Mapping`과 `MutableMapping`클래스는 아래와 같은 메서드들을 지원한다.
<br>

| Mapping        | MutableMapping |
|----------------|----------------|
| `__getitem__`  | `__setitem__`  |
| `__contains__` | `__delitem__`  |
| `__eq__`       | `clear`        |
| `__ne__`       | `pop`          |
| `get`          | `popitem`      |
| `items`        | `setdefault`   |
| `keys`         | `update`       |
| `values`       |                |

<br>파이썬 표준라이브러리에서 제공하는 `mapping`은 모두 `dict`을 이용하여 구현하므로 키는 항상 해싱가능해야 한다. 파이썬 공식문서에서 정의한 '해싱가능'함은 아래와 같다. <br><br>
> 어떤 객체의 수명주기 동안 값이 절대 바뀌지 않다면, 그리고 다른 객체와 비교가능하다면 그 객체는 해시가능합니다.(절때 바뀌지 않는지 확인하려면 `__hash__()`메소드가 필요하고 다른 객체와 비교가능한지 확인하려면 `__eq__()`메소드가 필요합니다.) 동일하다고 간주되는 두 객체는 반드시 같은 해시값을 가져야 합니다.

<br>
사용자 정의 자료형은 기본적으로 해시가능한 자료형이다. 기본적으로 어떤 객체의 해시값은 `id()`함수를 이용해서 구하므로 모든 객체의 해시값이 서로 다르기 때문이다. <br>


### dict comprehension

리터럴 구문이나 생성자 외에도 지능형 딕셔너리(dict comprehension)를 이용해서 딕셔너리를 만들 수 있다. 지능형 딕셔너리(dict comprehension)은 모든 반복형 객체에서 키-값 쌍을 생성함으로써 딕셔너리 객체를 생성한다.<br>

```python
>>> DIAL_CODES = [
...     (86, 'china'),
...     (82, 'korea'),
...     (1, 'us'),
...     (55, 'brazil'),
...     (7, 'russia'),
... ]
>>> country_codes = {country: code for code, country in DIAL_CODES}  # tuple unpacking
>>> country_codes 
{'china': 86, 'korea': 82, 'us': 1, 'brazil': 55, 'russia': 7}
>>> {code: country.upper() for country, code in country_codes.items() if code < 66}
{1: 'US', 55: 'BRAZIL', 7: 'RUSSIA'}
```

`DIAL_CODES`리스트를 순회하면서 각 튜플들을 언패킹한 결과인 `code`, `country`를 가지고 `country`를 키로, `code`를 값으로 갖는 딕셔너리 `country_codes`를 생성했다.
그 다음으로 `code`가 66보다 클 경우에만 `code`를 키로, `country`의 대문자를 값으로 갖는 또다른 딕셔너리를 생성했다. `items()`는 `(키, 값)`을 원소로 갖는 순회가능한 `dict_items`타입의 객체를 반환한다.<br>

### setdefault()
어떤 딕셔너리 `d`에 `d[k]`와 같이 존재하지 않는 키 `k`로 접근하면 `KeyError`가 발생한다. 그래서 대개 `d.get('k', 'replacement')`와 같이 사용하지만 `k`에 대한 값이 없다면 다른 값으로 `d`에 키-값을 추가한 후, 새로 추가된 값을 리턴한다고 할 때 아래와 같이 코드를 짜야 한다.<br>

```python
if not d.get('k'):
    d['k'] = 'new value'
    return d['k']
```
하지만 키를 여러 번 조회하지 않음으로써 더 효율적이고 간결하게 코드를 짜는 방식이 있다.
```python
d.setdefault('k', 'new_value')
```
`get()`을 사용한 코드는 키를 두 번 검색하는 반면 `setdefault()`는 한 번만 검색한다.<br>

### __missing__()
mapping 객체는 `__missing__()`메서드를 이용해서 존재하지 않는 키를 처리한다. `dict`클래스를 상속하고 `__missing__()`메서드를 정의하면 `dict.__getitem__()`메서드에서 키를 발견할 수 없을 때 `__missing__()`메서드를 호출한다. (주의 : 위에서 언급한 `get()`메서드는 `__getitem__()`이 아닌 `in`연산자를 이용하므로 `__missing__()`메서드를 호출하지 않는다.)
예를 들어, 오직 스트링타입의 키만 가지는 딕셔너리에 스트링타입이 아닌 키로 접근해보자.<br>

```python
>>> d = {'1': 'one', '2': 'two', '3': 'three'}
>>> d[1]
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
KeyError: 1
```
`dict`객체인 `d`는 오직 스트링타입의 키만 가지고 있고 `d`에 스트링이 아닌 숫자로 접근하면 `KeyError`를 발생시킨다. 반면에 `__missing__()`메서드를 정의하여 스트링이 아닌 숫자타입의 키로 딕셔너리를 접근할 때 숫자를 스트링으로 변환한 후, 변환한 스트링 키에 해당하는 값을 리턴하도록 `StrKeyDict`클래스를 구현했다.<br>

```python
>>> class StrKeyDict(dict):
...     def __missing__(self, key):
...         if isinstance(key, str):
...             raise KeyError(key)
...         return self[str(key)]
... 
>>>     
... s = StrKeyDict({'1': 'one', '2': 'two', '3': 'three'})
>>> s[1]
'one'
```
한 가지 주의할 점은 `if isinstance(key, str)` 조건문이 없다면 아래와 같은 에러가 발생한다는 것이다.<br>

```python
>>> s[4]
Traceback (most recent call last):
  File "<stdin>", line 1, in <module>
  File "<stdin>", line 5, in __missing__
  File "<stdin>", line 5, in __missing__
  File "<stdin>", line 5, in __missing__
  [Previous line repeated 330 more times]
RecursionError: maximum recursion depth exceeded while calling a Python object
```

`str(key)`가 존재하면 문제가 생기지 않지만 `str(key)`에 해당하는 값이 존재하지 않으면 `return self[str(key)]`가 `__getitem__()`을 호출하고 `__getitem__()`은 다시 `__missing__()`을 호출하므로 무한재귀가 발생한다.
