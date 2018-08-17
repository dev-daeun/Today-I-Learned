
# JS는 어떻게 동작하는가4 - async/await 
#### **What’s happening in ES8? Async/await**
자바스크립트 ES8은 프로미스 작업을 더 쉽게 만들어주는 `async/await`를 도입했습니다. `async/await`의 가능성과 비동기코드를 작성하기 위해 이것을 어떻게 이용하는지 살펴보겠습니다. 먼저, `async/await`가 어떻게 동작하는지 알아보겠습니다.<br><br>

`async`함수 선언을 통해 당신은 비동기 함수를 정의할 수 있습니다. `async`함수는 객체를 리턴하는데, 이 객체는  [async function](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/AsyncFunction)객체입니다. 이 객체는 함수 내부의 코드를 실행하는 비동기함수를 나타냅니다. 
`async function`이 호출되면 함수는 프로미스 객체를 리턴합니다.  그런데 `async`함수가 어떤 값을 리턴할 때 그 값은 프로미스 객체가 아닙니다. 프로미스 객체는 자동으로 생성되고 함수가 리턴하고자 하는 값과 함께 `resolve`됩니다. <br> `async`함수가 exception을 던질 때 프로미스는 exception과 함께 `reject`됩니다.
`async`함수에는 `await`를 선언할 수 있습니다. `await`는 함수의 실행을 멈추고 다른 함수로부터 넘겨받은 프로미스가 `resolve`되기를 기다립니다. 프로미스 객체가 `resolve`되면 다시 `async`함수의 실행을 재개하고 `async`함수에서 `resolve`시킨 값을 리턴합니다.<br>

JS의 프로미스는 Java에서 `Future`, C#에서 Task를 연상케 합니다.
> `async/await`의 목적은 프로미스를 이용한 작업을 간단하게 만들기 위함입니다.

아래의 코드를 예로 들겠습니다.
```javascript
// 일반적인 JS 함수
function getNumber1() {
	return Promise.resolve('374');
}

// getNumber1과 같은 역할을 하는 함수
async function getNumber2() {
	return 374;
}
```

`async/await`에서 예외를 던지는 함수는 `reject`된 프로미스 객체를 리턴하는 함수와 동일합니다. 
```javascript
function f1() {
	return Promise.reject('Some error');
}
async function f2() {
	throw 'Some error';
}
```
`await`키워드는 오직 `async`함수에서만 선언될 수 있고 프로미스 객체를 동기적으로 기다릴 수 있도록 허용합니다. `async`함수 밖에서 프로미스를 사용한다면 우리는 `then`을 이용해 콜백을 처리해야 할 것입니다.
```javascript
async function loadData() {
// `rp`는 프로미스에 기반한 리퀘스트 함수입니다.
	var promise1 = rp('https://api.example.com/endpoint1');
	var promise2 = rp('https://api.example.com/endpoint2');
	// 두 리퀘스트가 발생하면 응답이 돌아올 때 까지 기다립니다.
	var response1 = await promise1;
	var response2 = await promise2;
	return response1 + ' ' + response2;
}

// 이 부분은 async 함수의 내부가 아니기 때문에 then으로 콜백을 처리합니다.
loadData().then(() => console.log('Done'));

```
또한 우리는 '비동기 함수 표현식' 으로 `async`함수를 사용할 수 있습니다. 비동기 함수 표현식은 `async`함수가 갖는 구문과 거의 흡사한 구문을 가집니다. 두 가지의 차이점은 함수이름을 짓는 데 있습니다. `async`함수와 다르게 비동기 함수 표현식은 익명함수를 생성할 때 함수의 이름을 생략 할 수 있다는 점입니다. 비동기 함수 표현식은 정의되자마자 곧바로 실행되는 함수인 '즉시실행함수'(IIFE, Immediately Invoked Function Expression)로 사용될 수 있습니다.  IIFE로 정의되는 비동기 함수 표현식의 예시는 아래와 같습니다.
```javascript
function resolveAfter2Seconds(x) {
  return new Promise(resolve => {
    setTimeout(() => {
      resolve(x);
    }, 2000);
  });
};

// 비동기 함수 표현식은 익명함수로서 변수에 할당 가능합니다.
var add = async function(x) { 
  var a = await resolveAfter2Seconds(20);
  var b = await resolveAfter2Seconds(30);
  return x + a + b;
};

add(10).then(v => {
  console.log(v);  // 4초 뒤에 60 출력
});


(async function(x) { // 비동기 함수 표현식을 IIFE로 사용할 수 있습니다.
  var p_a = resolveAfter2Seconds(20);
  var p_b = resolveAfter2Seconds(30);
  return x + await p_a + await p_b;
})(10).then(v => {
  console.log(v);  // 2초 뒤에 60 출력
});
```
더 중요한 것은 `async/await`는 모든 주요 브라우저에서 지원 가능하다는 점입니다.

![](https://cdn-images-1.medium.com/max/1600/0*z-A-JIe5OWFtgyd2.)

만약 위의 차트에 당신이 원하는 브라우저 또는 브라우저의 버전이 없다면, [Babel](https://babeljs.io/docs/plugins/transform-async-to-generator/)  또는  [TypeScript](https://www.typescriptlang.org/docs/handbook/release-notes/typescript-2-3.html)와 같은 JS transfiler를 이용할 수 있습니다.
가장 중요한 점은 비동기 코드를 작성하기 위해 무조건적으로 가장 최신의 것을 선택하지 않는 것입니다. JS의 내부를 이해하고, 이것이 왜 중요한지 배우며, 당신이 선택한 방법론의 내부를 이해하는 것이 필요합니다. 모든 접근법에는 다른 것들도 그러하듯이 장점과 단점이 존재합니다.


### 비동기 코드를 유지 가능하고 다루기 쉽게 작성하는 5가지 방법 

1.  **깨끗한 코드:** `async/await`는 적은 코드를 작성하게 해줍니다. `then`이나 다른 함수의 호출결과를 처리하기 위한 콜백을 작성하는 과정을 건너뛸 수 있습니다.

	```javascript
	// `rp` is a request-promise function.
		rp(‘https://api.example.com/endpoint1').then(function(data) {
	// …
	});
	```
	와 
	```javascript
	// `rp` is a request-promise function.
	var response = await rp(‘https://api.example.com/endpoint1');
	```
	를 비교해보세요.

2.  **예외 처리:** `async/await`는 동기, 비동기 에러를 모두 같은 코드구조에서 처리합니다. 잘 알려진 `try/catch`구문으로 말이죠. `getJSON()`이 프로미스로 작성되었을 때 `loadData()`의 코드는 아래와 같습니다.
	```javascript
	function loadData() {
		try { // 동기 에러를 잡습니다.
			getJSON().then(function(response) {
			var parsed = JSON.parse(response);
			console.log(parsed);
		}).catch(function(e) { // 비동기 에러를 잡습니다.
			console.log(e);
		});
		} catch(e) {
			console.log(e);
		}
	}
	```
	  `getJSON()`이 `async`함수로 정의되었을 때 `loadData()`는 더 간결하게 작성될 수 있습니다.
	``` javascript
	async function loadData() {
		try {
			var data = JSON.parse(await getJSON());
			console.log(data);
			} catch(e) {
			console.log(e);
		}
	}
	```

3. **조건문:** `async/await`의 조건문은 더 직관적으로 보입니다. 프로미스에서의 조건문은 아래와 같습니다.
	```javascript
	function loadData() {
		return getJSON()
		.then(function(response) {
			if (response.needsAnotherRequest) {
				return makeAnotherRequest(response)
				.then(function(anotherResponse) {
					console.log(anotherResponse)
					return anotherResponse
				})
			} else {
				console.log(response)
				return response
			}
		})
	}
	``` 

	`async/await`에서 조건문을 작성할 때는 아래와 같습니다.
	```javascript
	async function loadData() {
		var response = await getJSON();
		if (response.needsAnotherRequest) {
			var anotherResponse = await makeAnotherRequest(response);
			console.log(anotherResponse)
			return anotherResponse
		} else {
			console.log(response);
			return response;
		}
	}
	```

4.  **스택 프레임:**  `async/await`와 다르게, 프로미스 체인에서 리턴하는 에러스택은 에러가 어디에서 발생했는지 알려주지 않습니다. 아래의 코드를 살펴보겠습니다.
	```javascript
	function loadData() {
		return callAPromise()
		.then(callback1)
		.then(callback2)
		.then(callback3)
		.then(() => {
			throw new Error("boom");
		})
	}
	loadData()
	.catch(function(e) {
		console.log(err);
		// Error: boom at callAPromise.then.then.then.then (index.js:8:13)
	});
	```
	'async/await'의 경우를 살펴보겠습니다.
	```javascript
	async function loadData() {
		await callAPromise1()
		await callAPromise2()
		await callAPromise3()
		await callAPromise4()
		await callAPromise5()
		throw new Error("boom");
	}
	loadData()
	.catch(function(e) {
		console.log(err);
		// output
		// Error: boom at loadData (index.js:7:9)
	});
	```
5.  **디버깅:** 프로미스를 사용해봤다면 이를 디버깅하는 것은 악몽같음을 알고있을 것입니다. 예를 들어 `.then` 내부에 breakpoint를 걸고 "stop-over"와 같은 단축키로 디버그를 한다면 디버거는 바로 다음에 호출되는 `.then` 안으로 들어가지 않습니다. 왜냐하면 디버거는 동기 코드의 안으로만 들어가기 때문입니다. `async`함수에서는 디버거가 `await`키워드를 붙여 호출되는 함수로 이동합니다.

**비동기 JS코드가 중요하다**는 사실은 애플리케이션에만 해당하는 사항이 아니라 라이브러리에도 해당합니다. 
예를 들어, [SessionStack](https://www.sessionstack.com/?utm_source=medium&utm_medium=blog&utm_content=Post-4-eventloop-outro) 라이브러리는 DOM의 변화, 사용자 상호작용, JS exception, 스택트레이싱, 네트워크 요청 실패, 디버깅 메세지 등 당신의 웹/앱 사이트의 모든 것을 기록합니다.
이 모든 것은 UX에 영향을 미치지 않고 당신의 개발환경 내부에서만 발생해야 합니다. 우리는 코드를 최적화시키고 코드를 최대한 비동기적으로 작성함으로써 이벤트 루프에 의해 처리되는 이벤트들의 수를 늘릴 수 있습니다. 또한 라이브러리 뿐만 아니라 session으로 사용자를 관리할 때, 문제가 발생할 경우 그 시점의 브라우저의 모든 것을 다시 만들어야 합니다. 문제가 발생하기 이전의 상태를 다시 만들어야하며 이는 세션의 시간적 기록을 되돌리도록 허용합니다. 이것이 가능하도록 하기 위해서 우리는 JS가 제공하는 비동기를 매우 적극적으로 차용해야 합니다.

[get started for free](https://www.sessionstack.com/?utm_source=medium&utm_medium=blog&utm_content=Post-4-eventloop-GetStarted)에는 sessionStack의 프로덕트를 체험할 수 있습니다..

![](https://cdn-images-1.medium.com/max/1600/0*xSEaWHGqqlcF8g5H.)


Resources:

-   [https://github.com/getify/You-Dont-Know-JS/blob/master/async%20%26%20performance/ch2.md](https://github.com/getify/You-Dont-Know-JS/blob/master/async%20%26%20performance/ch2.md)
-   [https://github.com/getify/You-Dont-Know-JS/blob/master/async%20%26%20performance/ch3.md](https://github.com/getify/You-Dont-Know-JS/blob/master/async%20%26%20performance/ch3.md)
-   [http://nikgrozev.com/2017/10/01/async-await/](http://nikgrozev.com/2017/10/01/async-await/)
---------------------
본 포스팅은 [Alexander Zlatkov의 글](https://blog.sessionstack.com/how-javascript-works-event-loop-and-the-rise-of-async-programming-5-ways-to-better-coding-with-2f077c4438b5)을 번역, 의역하여 작성된 글입니다.
