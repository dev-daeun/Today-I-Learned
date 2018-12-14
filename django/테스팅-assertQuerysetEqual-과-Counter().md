## Django Testing Tool의 assertQuerysetEqual()

장고 orm의 쿼리셋을 리턴하는 함수를 테스트하는 코드를 짜고 있었다.
쿼리셋을 내가 기대하는 결과와 비교해야 하므로 [assertQuerysetEqual](https://docs.djangoproject.com/en/2.1/topics/testing/tools/#django.test.TransactionTestCase.assertQuerysetEqual) 메소드를 쓰기로 했다.
```python
def assertQuerysetEqual(self, qs, values, transform=repr, ordered=True, msg=None):
    items = map(transform, qs)
    if not ordered:
        return self.assertEqual(Counter(items), Counter(values), msg=msg)
    values = list(values)
    # For example qs.iterator() could be passed as qs, but it does not
    # have 'ordered' attribute.
    if len(values) > 1 and hasattr(qs, 'ordered') and not qs.ordered:
        raise ValueError("Trying to compare non-ordered queryset "
                         "against more than one ordered values")
    return self.assertEqual(list(items), values, msg=msg)
```

테스트하려는 함수는 아래와 같았다.
``` python
def retrieve_filtered_posts_from_db(start, end):
    return Post.objects.select_related(author).filter(
      date__range=(start, end),
    ).values('title',
             'content',
              author_name=F('author__name'),
            )
```

테스트 코드는 아래와 같았다.
```python
def test_queryset_equal(self):
    returned_posts = retrieve_filtered_posts_from_db(date.today(), date.today())
    expected_posts = [p1, p2, p3, p4]  # p1, p2, p3, p4는 Post모델의 객체.
    self.assertQuerysetEqual(returned_posts, expected_posts, ordered=False)
```


`retrieve_filtered_posts_from_db()`는 `values('title', 'content', ..)`를 호출한 결과를 리턴하는데,
참고로 [values()](https://docs.djangoproject.com/en/2.1/ref/models/querysets/#values)는 쿼리셋이 *evaluation* 될 때 딕셔너리를 리턴하도록 한다.<br>
`assertQuerysetEqual`의 `map`함수는 `qs`파라미터로 들어오는 쿼리셋을 순회하면서 *evaluation*된 결과에 `repr`을 적용한다. 

> evaluation: The moment you start iterating over a queryset, all the rows matched by the queryset are fetched from the database and converted into Django models. This is called *evaluation*. These models are then stored by the queryset’s built-in cache, so that if you iterate over the queryset again, you don’t end up running the same query twice. (출처: [Using Django Queryset Effectively](http://blog.etianen.com/blog/2013/06/08/django-querysets))

이렇게 되면 `assertQuerysetEqual`의 `items` 변수에는 쿼리셋이 evaluation 되면서 리턴한 딕셔너리에 `repr`을 적용한 맵이 저장될 것이고, `items`를 `list`로 캐스팅한다면 


```
[
    "{title: '제목', 'author_name': 'kde6260'...}", 
    "{title: '제목2', 'author_name': 'kde6260'...}"
]
````
이 될 것이다. 한편 `Post`모델의 `__repr__()`메소드는 다음과 같은 문자열을 리턴하고, `expected_posts`는 `Post` 모델 객체들을 리스트로 가지고 있다.

```
f"(title: {self.title}, author: {self.author.name},...)"
```

### Counter()
[Counter](https://docs.python.org/2/library/collections.html#counter-objects)는 리스트, 맵, 문자열 등의 객체에서 공통된 요소들과 요소들의 갯수를 key-value 형태로 제공하는 컬렉션이다.
```python
In [17]: from collections import Counter                           
In [18]: c = Counter(['apple', 'banana', 'apple', 'grape'])
In [19]: c
Out[19]: Counter({'apple': 2, 'banana': 1, 'grape': 1})
In [20]: c['apple']
Out[20]: 2
In [21]: c['banana']                      
Out[21]: 1
In [22]: c['melon']                                
Out[22]: 0
```

`assertQuerysetEqual`에서는 `ordered` 파라미터가 False일 때 `Counter(list(items))`와 `Counter(values)`를 비교한다. 위에서 설명한 경우라면 `Counter(list(items))`는 아래와 같을 것이고

```python
Counter({
    "{title: '제목', 'author_name': 'kde6260'...}": 1,
    "{title: '제목2', 'author_name': 'kde6260'...}": 1,
    ...
})

```
`Counter(values)`는 아래와 같을 것이다.
```python
Counter({
    (title: 제목, author: kde6260, ..): 1,
    (title: 제목2, author: kde6260, ..): 1,
    ...
})

```
결국 서로 다른 key를 같는 딕셔너리를 비교하기 때문에 테스트에 실패하게 된 것이다. 테스트케이스가 통과되기 위해서는 `expected_posts`이 `Post`모델 객체 리스트가 아니라 `map(repr, [{title: '제목', author_name: 'kde6260', ...}, {...}])` 이 되어야 한다.

