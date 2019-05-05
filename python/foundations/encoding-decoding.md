
파이썬3의 `str()`은 항상 유니코드 문자열을 리턴하지만 파이썬2는 경우가 다르다. <br>


```python
def str(s):  # python2 
    """Return a nice string representation of the object.  The
    return value is a str or unicode instance.
    """
    if type(s) is str or type(s) is unicode:
        return s
    r = s.__str__()
    if not isinstance(r, (str, unicode)):
        raise TypeError('__str__ returned non-string')
    return r
```

> In Python 2, objects can specify both a string and unicode representation of themselves. In Python 3, though, there is only a string representation. This becomes an issue as people can inadvertently do things in their __str__() methods which have unpredictable...
https://docs.python.org/3.3/howto/pyporting.html

## 인코딩 & 디코딩

인코딩은 codepoint를 byte로 변환하는 것을, 디코딩은 byte를 codepoint로 변환하는 것을 말한다.

코드포인트(code point)는 10진수 0부터 1,114,111 까지의 숫자를 범위로 갖는 문자의 단위원소다. <br>
유니코드 표준에서는 `U+` 접두사를 붙여서 4~6자리의 16진수료 표현한다. <br>
유니코드 6.3에서 가용한 코드포인트의 약 10퍼센트 정도가 문자에 할당되어 있다.
> 예 : 문자 A = U+0041

문자를 표현하는 실제 바이트는 사용하는 인코딩 코덱에 따라 달라진다. 

## 기본 인코더/디코더

텍스트를 바이트로, 바이트를 텍스트로 변환하기 위해 파이썬에는 100여개의 코덱이 포함되어있다.<br>
코덱은 `open(), str.encode(), str.decode()`등의 함수를 호출할 때 `encoding`인수에 전달해서 사용한다.


```python
for codec in ['latin_1', 'utf_8', 'utf_16']:
    print(codec, 'CAFÉ,'.encode(codec), sep='\t')
```

    latin_1	b'CAF\xc9,'
    utf_8	b'CAF\xc3\x89,'
    utf_16	b'\xff\xfeC\x00A\x00F\x00\xc9\x00,\x00'


ascii나, latin_1은 유니코드 문자를 모두 표현할 수 없으나, UTF-8은 모든 유니코드 문자를 처리한다.


```python
for codec in ['utf_8', 'latin_1', 'utf_16']:    
    print(codec, '서울,'.encode(codec), sep='\t')
```

    utf_8	b'\xec\x84\x9c\xec\x9a\xb8,'



    ---------------------------------------------------------------------------

    UnicodeEncodeError                        Traceback (most recent call last)

    <ipython-input-3-61e0d852c6b5> in <module>
          1 for codec in ['utf_8', 'latin_1', 'utf_16']:
    ----> 2     print(codec, '서울,'.encode(codec), sep='\t')
    

    UnicodeEncodeError: 'latin-1' codec can't encode characters in position 0-1: ordinal not in range(256)


## UnicodeEncodeError & UnicodeDecodeError

### UnicodeEncodeError
대부분의 none-UTF 코덱은 유니코드 문자의 일부만 처리할 수 있다. <br>
텍스트를 바이트로 변환할 때 문자가 대상 코덱에 정의되어 있지 않으면 `UnicodeEncodeError`가 발생한다. <br>
`encode()`메서드의 `errors` 인수에 별도의 처리기를 지정한다면 발생하지 않을 수도 있다. 


```python
city = u'서울'  
# 파이썬2에서 유니코드 문자열을 정의할 때는 문자열 앞에 'u'를 붙여서 unicode 객체를 만든다. u를 붙이지 않으면 디폴트로 ascii가 코덱으로 정해진다.
type(city)
print(len(city))
```

    2



```python
en8_city = city.encode('utf-8')
print(en8_city)
print(len(en8_city))
```

    b'\xec\x84\x9c\xec\x9a\xb8'
    6



```python
en16_city = city.encode('utf-16')
print(en16_city)
print(len(en16_city))
```

    b'\xff\xfe\x1c\xc1\xb8\xc6'
    6



```python
city.encode('cp437')  # cp437 코덱은 한글을 인코딩할 수 없으므로 UnicodeEncodeError를 발생시킨다.
```


    ---------------------------------------------------------------------------

    UnicodeEncodeError                        Traceback (most recent call last)

    <ipython-input-7-e069bdeb8ca7> in <module>
    ----> 1 city.encode('cp437')  # cp437 코덱은 한글을 인코딩할 수 없으므로 UnicodeEncodeError를 발생시킨다.
    

    /usr/local/Cellar/python/3.7.2_2/Frameworks/Python.framework/Versions/3.7/lib/python3.7/encodings/cp437.py in encode(self, input, errors)
         10 
         11     def encode(self,input,errors='strict'):
    ---> 12         return codecs.charmap_encode(input,errors,encoding_map)
         13 
         14     def decode(self,input,errors='strict'):


    UnicodeEncodeError: 'charmap' codec can't encode characters in position 0-1: character maps to <undefined>



```python
city.encode('cp437', errors='ignore') # 인코딩이 불가능한 문자는 건너뛰어버린다.
```




    b''




```python
city.encode('cp437', errors='replace') # 인코딩이 불가능한 문자는 물음표로 치환한다.
```




    b'??'



### UnicodeDecodeError

codepoint를 byte로 인코딩한 후에, byte를 다시 codepoint로 디코딩할 경우, 모든 byte는 정당한 ascii문자가 될 수 없으며 모든 byte sequence가 정당한 UTF-8 또는
UTF-16 문자가 되는 것은 아니다. 따라서 이진시퀸스를 텍스트로 변환할 때 정당한 문자로 변환할 수 없으면 `UnicdeDecodeError`가 발생한다.


```python
octet = b'Montr\xe9al'  # octet은 latin-1 코덱으로 인코딩된 문자열이다.
octet.decode('latin-1')
```




    'Montréal'




```python
octet.decode('cp1252')  # cp1252는 latin-1 코덱의 상위집합이므로 정상적으로 디코딩된다.
```




    'Montréal'




```python
octet.decode('utf-8')  # utf-8은 latin-1로 인코딩된 문자열을 디코딩할 수 없음을 알리기 위해 UnicodeDecodeError를 발생시킨다. (utf-8은 ascii와 호환된다.)
```


    ---------------------------------------------------------------------------

    UnicodeDecodeError                        Traceback (most recent call last)

    <ipython-input-12-72743da66a5b> in <module>
    ----> 1 octet.decode('utf-8')  # utf-8은 latin-1로 인코딩된 문자열을 디코딩할 수 없음을 알리기 위해 UnicodeDecodeError를 발생시킨다. (utf-8은 ascii와 호환된다.)
    

    UnicodeDecodeError: 'utf-8' codec can't decode byte 0xe9 in position 5: invalid continuation byte


그러나 `cp1252`, `iso8859_1`, `koi8_r`같은 레거시 코덱은 에러를 발생시키지 않고 쓰레기문자로 디코딩한다.


```python
print(octet.decode('cp1252'))
print(octet.decode('iso8859_7'))
print(octet.decode('koi8_r'))
```

    Montréal
    Montrιal
    MontrИal


## bytes와 bytearray

파이썬3에 새로 도입된 이진시퀸스형은 파이썬2의 `str`과 여러모로 다르다.<br>
이진시퀸스를 위해 사용되는 내장자료형은 `bytes`와 `bytearray`이다.<br>
`bytes`는 파이썬3에서 소개된 불변형이고, `bytearray`는 파이썬2.6에 추가된 가변형이다.<br>
`bytes`와 `bytearray`에 들어있는 각 항목은 0 ~ 255 사이의 정수로, 파이썬2의 `str`의 문자 1개와는 다르다.<br>
그러나 이진시퀸스를 슬라이싱하면 언제나 동일한 자료형의 이진시퀸스가 만들어진다.


```python
cafe = bytes('CAFÉ', encoding='utf_8')
print(cafe)
print(cafe[0])
print(type(cafe[0]))
print(cafe[:1])
print(type(cafe[:1]))
```

    b'CAF\xc3\x89'
    67
    <class 'int'>
    b'C'
    <class 'bytes'>



```python
cafe_arr = bytearray(cafe)
print(cafe_arr)
print(cafe_arr[0])
print(type(cafe_arr[0]))
print(cafe_arr[:1])
print(type(cafe[:1]))
```

    bytearray(b'CAF\xc3\x89')
    67
    <class 'int'>
    bytearray(b'C')
    <class 'bytes'>


`bytes`와 `bytearray`의 각 항목을 조회하면 0~255 사이의 정수를 반환하지만, 슬라이싱을 할 경우에는 `bytes`객체를 반환한다.<br>
`s[0] == s[:1]` 을 만족하는 시퀸스형은 `str`이 유일하다.

`bytes`와 `bytearray`는 실질적으로 정수형 시퀸스이지만, 리터럴 표기법을 보면 실제로 ascii텍스트가 들어간다.<br>
따라서 각 바이트 값에 따라 다음과 같이 세가지 형태로 출력한다.

####  화면에 출력가능한 ascii문자는 ascii문자 그대로 출력한다.
#### 탭, 개행문자, 캐리지 리턴, 백슬래시는 (\t, \n, \r, \\)와 같은 이스케이스 시퀸스로 출력한다.
#### 그 외의 값은 null byte를 나타내는 `\x00`처럼 16진수 이스케이프 시퀸스로 출력한다.

그래서 위 `cafe`를 출력할 때 `b'CAF\xc3\x89'`로 출력된 것이다. <br>
처음 3바이트는 ascii코드 범위 내에 있는 문자이나, 나머지 1바이트는 ascii코드에 포함되지 않기 때문이다.

------------------------------------------

원시데이터를 갖는 리스트를 이진시퀸스로 형변환 할 수도 있다.


```python
import array
numbers = array.array('h', [-2, 1, 0, 1, 2]) # 'h'는 short int(2byte)를 나타내는 타입코드.
octets = bytes(numbers)
print(octets)
print(len(octets)) # 2byte * 5개 = 10byte 만큼의 이진시퀸스로 형변환.
print(type(octets))
```

    b'\xfe\xff\x01\x00\x00\x00\x01\x00\x02\x00'
    10
    <class 'bytes'>


`bytes`타입은 `ascii`코드만 인코딩을 허용하므로, 0~127 범위에 없는 문자(`octets`의 경우 -2)는 이스케이프 처리되어 형변환된다. 
>Only ASCII characters are permitted in bytes literals (regardless of the declared source code encoding). Any binary values over 127 must be entered into bytes literals using the appropriate escape sequence.


```python
octets.decode('ascii', errors='replace')
```




    '��\x01\x00\x00\x00\x01\x00\x02\x00'



`struct`모듈은 패킹된 바이트를 다양한 형의 필드로 구성된 튜플로 분석한다.<br>
바이트를 언패킹할 때에는 바이트순서와 타입, 갯수를 지정해야 한다.<br>
`octets`는 빅 엔디언(메모리 증가방향과 동일하게, 큰 단위에서 작은단위 순서로 숫자를 메모리에 저장하는 방식.)이고 <br>
short int 5개를 바이트로 변환한 것이므로 `unpack()`함수를 아래와 같이 호출한다.


```python
import struct
print(struct.unpack('<hhhhh', octets))
```

    (-2, 1, 0, 1, 2)

