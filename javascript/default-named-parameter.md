## JS 함수의 default & named parameter 

파이썬에는 함수 파라미터를 keword argument로 전달할 수 있다.

```python
def print_cheese(kind, weight=1.0, region):
    print(f'{region}에서 온 {kind}치즈 무게는 {weight}kg이다.')
    
print_cheese('모짜렐라', 3.4, '임실')  # 파라미터 순서를 지켜야 함
print_cheese(region='임실', kind='모짜렐라', weight=3.4)  # 키워드로 파라미터를 전달하므로 순서를 지키지 않아도 됨.
```

키워드 혹은 변수이름으로 파라미터를 전달하면 순서를 알 필요가 없으므로 함수 파라미터 갯수가 많을 때 유용하다. 최근에 JS로 파라미터 갯수가 5개 정도되는 함수를 만든 적이 있었는데 JS도 파이썬의 `kwargs`같은 걸 제공해주는지 찾아보았다. ES6부터 `default parameter` 와 `named parameter`를 지원하고 있었다.

### default parameter
```javascript
function multiply(a, b = 1) {
  return a*b;
}

multiply(5); // 5
```

위 `multiply`함수가 `multiply(a, b){...}`였고 `multiply(5)`로 호출했다면 값이 전달되지 않은 변수 `b`에는 `undefined`가 들어갔을 것이다. 그러나 위의 함수의 경우 변수 `b`에 디폴트 값 `1` 이 지정되어 있으므로 변수 `a`에만 값을 전달하면 숫자 `5`를 리턴한다.

<br>

```javascript
multiply(5, undefined)  // 5
multiply(5, null)       // 0
```
디폴트 값이 지정된 파라미터에 `undefined`가 전달되면 `undefined`는 무시되고 디폴트 값을 참조한다. `null`일 경우에는 `null`이 그대로 전달되어서 `0`을 리턴한다.


<br>

파이썬은 함수가 호출되는 시점이 아닌 정의된 시점에서 만들어진 디폴트 값을 참조하므로 파라미터에 디폴트 값을 리스트와 같은 컬렉션으로 둘 경우 아래와 같은 현상이 나타난다. 
```python
def print_list(element, list_=[1,2,3]):
    list_.append(element)
    print(list_)


print_list(4)  # [1,2,3,4]
print_list(5)  # [1,2,3,4,5]
```

그러나 반대로 JS는 호출되는 시점에서의 함수 디폴트 값을 참조한다.
```javascript
function printList(element, list=[1,2,3]){
    list.push(element);
    console.log(list);
}

printList(4)  // [1,2,3,4]
printList(5)  // [1,2,3,5]
```

### named parameter
파이썬의 `kwarg`처럼 JS에서 이름에 의한 파라미터 전달을 하는 방법은 아래와 같다.
```javascript
function printCheese({kind='모짜렐라', weight=1.0, region='임실'} = {}) {
    console.log(kind, weight, region);
}

printCheese({
    kind: '페타',
    weight: 3.4,
    region: '나폴리'
});
// 나폴리에서 온 페타치즈 무게는 3.4kg이다.
```
`printCheese` 함수의 `{kind='모짜렐라', weight=1.0, region='임실'} = {}` 부분은 함수 호출 시 넘어온 인자에 값이 있으면 해당 이름의 지역변수를 갱신하고, 없으면 지역변수의 디폴트 값을 그대로 두는 일종의 문장이라고 볼 수 있다.
<br>



```javascript
function printCheese(kind, {weight=1.0, region='임실'} = {}) {
    console.log(`${region}에서 온 ${kind}치즈 무게는 ${weight}kg이다.`);
}
```

위 함수를 호출하는 방법은 아래와 같을 것이다.
```javascript
printCheese('모짜렐라', {weight: 3.4, region: '나폴리'});
printCheese('모짜렐라', {region: '나폴리', weight: 3.4});
```
한 가지 유의할 점이 있는데 함수를 호출할 때 파이썬에서 키워드 인자로 파라미터를 넘겨주는 방식과 동일할 것이라 착각하고 위 함수를 아래와 같이 호출한다면 `{
    kind: '모짜렐라',
    weight: 3.4,
    region: '나폴리'
}`가 `kind`변수에 들어가게 되므로 예상과 다른 결과가 출력된다는 것이다. 
```javascript
printCheese({
    kind: '모짜렐라',
    weight: 3.4,
    region: '나폴리'
});  
// 임실에서 온 {kind: '모짜렐라', weight: 3.4, region: '나폴리'}치즈 무게는 1.0kg이다.
```
