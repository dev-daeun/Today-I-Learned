

# JS는 어떻게 동작하는가3 - Promises 

아래의 코드를 봅시다.
```javascript
var x = 1;
var y = 2;
console.log(x + y);
```
아주 단순합니다. `x`와 `y`의 값을 더하여 콘솔로 출력합니다. 그러나 만약에, 서버에서 값을 불러오지 않아서
`x`와`y`가 아직 초기화되지 않은 상태라면 어떻게 할까요? 서버에서 `x`와 `y`값을 불러오는 메서드 `loadX`, `loadY`와 
이들을 더하는 `sum` 메서드가 있다고 가정합시다. 코드는 아래와 같이 구현될 수 있습니다. (꽤 이상한 코드입니다.)
```javascript
function sum(getX, getY, callback) {
    var x, y;
    getX(function(result) {
        x = result;
        if (y !== undefined) {
            callback(x + y);
        }
    });
    getY(function(result) {
        y = result;
        if (x !== undefined) {
            callback(x + y);
        }
    });
}
// A sync or async function that retrieves the value of `x`
function fetchX() {
    // ..
}


// A sync or async function that retrieves the value of `y`
function fetchY() {
    // ..
}
sum(fetchX, fetchY, function(result) {
    console.log(result);
});
```


여기에서 아주 중요한 사항이 있습니다. 우리는 **sum을 호출할 때 `x`와 `y`를 이미 값이 정해진 것으로 취급하고, `sum(...)` 함수가 `x`와 `y`값이 지금 당장 참조가능한 상태인지 아닌지의 여부를 따지지 않는 것처럼 쓴다는 것**입니다.


## Promise Value
```javascript
function sum(xPromise, yPromise) {
	return Promise.all([xPromise, yPromise])
.then(function(values){
	return values[0] + values[1];
});
}
// `fetchX()` and `fetchY()` return promises for
// their respective values, which may be ready
// *now* or *later*.
sum(fetchX(), fetchY())
.then(function(sum){
	console.log(sum);
});
```
프로미스로 `x + y`를 표현하는 방법을 잠깐 살펴보겠습니다. 이 부분에서는 프로미스의 2가지 계층을 소개합니다.
첫번째, `fetchX()` 와 `fetchY()` 에서 리턴하는 프로미스입니다. 두 함수들은 직접 호출되고, 이 두가지 함수가 리턴하는 프로미스는 `sum(...)`으로 전달됩니다. 이 프로미스들은*나중* 혹은 *지금*을 나타낼수 있으나, 이것과 관계없이 각각의 프로미스는 동일하게 행동합니다. `x`와 `y`를 시간으로부터 독립적인 방법으로 판단하는 것입니다. 

두번째는 `sum(...)`메소드가 만들고 리턴하는 프로미스입니다. (`sum`함수는 `fetchX`, `fetchY`를 받기 때문에`Promise.all[...]`을 통해 프로미스를 만들고 리턴할 것입니다.) 이 프로미스는 `then()`을 호출함으로써 처리합니다. `sum(...)` 함수의 실행이 완료되면, `then()` 함수에서 `sum(...)`함수가 리턴하는 값을 출력할 수 있습니다. 우리는 `sum(...)` 함수에서 `x`, `y` 값들을 기다리는 로직을 숨길 수 있습니다.

**참고** : `sum(...)` 내부에서  `Promise.all([ … ])` 는 `fetchX`와 `fetchY`가 리턴하는 프로미스들이 resolve 될 때까지 기다리는 프로미스 객체를 생성합니다. 중첩으로 호출된 `then()`은 또 다른 프로미스를 생성하는데, `return values[0] + values[1]` 구문이 프로미스를 리턴하는 부분입니다. 따라서,  `sum(...).then(...)`에서 `then(...)`이 받는 프로미스는 `sum(...)`의 `Promise.all([ … ])`이 리턴하는 프로미스가 아니라, 두 값이 더해진 결과로 resolve하는 `then(function(values){
	return values[0] + values[1];
})`의 프로미스입니다. `then(...)`을 통해 체인호출을 하지 않고 다른 프로미스 객체를 만들어도 마찬가지입니다.

```javascript
sum(fetchX(), fetchY())
.then(function(sum) {
	console.log( sum ); //resolve handler
}, function(err) { // reject handler
	console.error( err ); // bummer!
});
```

`then()` 메서드는 사실 두 가지 함수를 파라미터로 받을 수 있습니다. 첫번째 파라미터는 프로미스가 fulfill 또는 resolve 되었을 때의 핸들러, 두 번째 파라미터는 reject가 발생했을 때의 핸들러입니다.
`x`와 `y`를 받아오거나 두 값을 더하는 과정에서 오류가 발생한다면, `sum(...)`이 리턴하는 프로미스 객체는 reject될 수 있습니다. 그리고 `then()`에서 받는 두 번째 콜백함수에서 프로미스로부터 받아온 reject에 대한 값을 받아서 처리할 수 있습니다.

프로미스는 바깥에서 던지는 resolve/reject를 기다리는 상태(시간에 의존적인 상태)를 숨기기 때문에 프로미스 자체는 시간으로부터 독립적이고, 시간과 결과에 상관없이 예상가능한 방법으로 구성됩니다.
게다가 프로미스가 한번 resolve되고나면, 바꿀 수 없는 불변의 값으로 남아있게 되므로 필요한만큼 언제든지 관찰할 수 있게 됩니다.
```javascript
function delay(time) {
	return new Promise(function(resolve, reject){
		setTimeout(resolve, time);
	});
}

delay(1000)
.then(function(){
	console.log("after 1000ms");
	return delay(2000);
})
.then(function(){
	console.log("after another 2000ms");
})
.then(function(){
	console.log("step 4 (next Job)");
	return delay(5000);
})
```
`delay(1000)`을 호출하면 1000밀리초 후에 resolve되는 프로미스가 만들어지고, 1000밀리초 후에 resolve되는 프로미스 객체를 기다리는 콜백을 첫번째 `then(...)` 함수 안에서 정의합니다. 이 콜백에서는 또 다시 `delay(2000)`을 호출함으로써 2000밀리초 후에 resolve되는 프로미스를 리턴합니다. 


**참고**: 프로미스는 한번 resolve되면 변경될 수 없으므로 악의적으로나 사고에 의해서나 resolve된 결과를 변경할 수 없기 때문에 어느 영역에서든 이를 참조할 수 있습니다. 그러므로 한 개의 resolve된 결과를 여러 영역에서 참조할 수 있습니다. 그리고 어느 하나의 영역에서 프로미스의 resolve를 기다리고 있는 다른 영역을 침범할 수 없습니다. 이러한 불변성은 이론적인 내용으로 들릴 수 있으나, 이 개념은 프로미스 디자인의 가장 기본적이고 중요한 부분이므로 대충 넘겨짚어선 안됩니다.

## **To Promise or not to Promise?**

프로미스에서 중요한 사항은 어떤 값이 프로미스 객체인지 아닌지 여부를 확실히 알고있냐는 것입니다. 한 마디로, 특정 값이 프로미스 객체처럼 행동할 것인가? 입니다. 우리는 프로미스 객체가 `new Promise(..)`에 의해 만들어진다는 것을 알고 있습니다. 그렇기 때문에 `p instanceof Promise`와 같은 처리가 불필요하다고 생각할 수도 있습니다. 하지만 사실은 그렇지 않습니다.
브라우저로가 브라우저 고유의 프로미스를 리턴할 수 있기 때문에 그 객체가 프로미스인지 구별하는 게 불가능할 수도 있습니다. 라이브러리나 프레임워크에서 네이티브 ES6의 프로미스가 아닌 특정 밴드(bluebird와 같은)에서 제공하는 프로미스를 채택할 경우에도 `instanceof Promise`는 `false`를 리턴할 수 있습니다. 당신이 오래된 버전의 브라우저를 사용중이라면 그 브라우저는 native가 아닌 외부 라이브러리에서 따온 프로미스를 사용할 수도 있다는 것입니다. 

## Swallowing exceptions
프로미스 객체를 생성하는 모든 과정에서, 혹은 resolve를 관찰하는 과정에서, `TypeError` 또는 `ReferenceError`와 같은 오류가 발생할 수 있습니다. 이 오류들은 예외처리가 가능하고, 따라서 의심되는 프로미스들을 reject 할 수 있습니다. 예를 들어보겠습니다.
```javascript
var p = new Promise(function(resolve, reject){
	foo.bar(); // `foo` is not defined, so error!
	resolve(374); // never gets here :(
});
p.then(function fulfilled(){
// never gets here :(
},function rejected(err){
// `err` will be a `TypeError` exception object
// from the `foo.bar()` line.
});
```
프로미스는 fulfill되었으나 `then(...)`의 resolve 핸들러(첫번째 파라미터로 들어온는 콜백)에서 exception이 발생해다면 어떻게 될까요? 예외가 사라지지는 않겠지만 예외를 찾아내는 방법을 찾아야 할 겁니다.
보기에는  `foo.bar()`가 발생시키는 오류가 삼켜진 것처럼 보입니다만, 사실 그것보다 더 심각한 문제가 있습니다.  `foo.bar()`가 일으키는 `TypeError`에 의해 `p.then(...)`의 프로미스가 reject된다는 것입니다.


## **Handling uncaught exceptions**
잡히지 않는 예외를 잡는 방법이 있습니다. 가장 많이 통용되는 방법으로는 `done(...)`을 프로미스체인에 넣어서 프로미스 체인이 잘 마쳐졌다고(done) 명시하는 것입니다. `done(...)`은 프로미스 객체를 리턴하지 않으므로 `done(...)`으로 전달되는 콜백들은 문제를 만드는 데 휘말리지 않게 됩니다. 앞의 예시와 같은 일반적으로 잡히지 않는 오류 상황에서 `done(...)`을 이용하면 우리가 예상하는 대로 예외를 처리할 수 있습니다. `done(...)` 의 콜백 내부에 있는 모든 예외들은 전역적으로 `uncaught error`로서 던져질 것입니다. (`console`로 출력가능하다는 뜻입니다.)
```javascript
var p = Promise.resolve(374);
p.then(function fulfilled(msg){
// numbers don't have string functions,
// so will throw an error
	console.log(msg.toLowerCase());
})
.done(null, function() {
// If an exception is caused here, it will be thrown globally
});
```

---------------------
본 포스팅은 [Alexander Zlatkov의 글](https://blog.sessionstack.com/how-javascript-works-event-loop-and-the-rise-of-async-programming-5-ways-to-better-coding-with-2f077c4438b5)을 번역, 의역하여 작성된 글입니다.



