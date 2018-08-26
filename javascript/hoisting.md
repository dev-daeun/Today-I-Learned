#  hoisting


모 기업의 면접에서 JS의'호이스팅(hoisting)'이 무엇이냐는 질문을 받았습니다. 저는 '일반적인 언어와 다르게 JS는 지역변수의 유효범위를 블록이 아닌 함수로 지정하기 때문에 블록 내부에서 정의한 지역변수라도 그 블록이 함수 내부에 정의되어있다면 함수까지 그 변수가 유효한 범위를 가지는 것을 뜻한다.'고 대답했습니다. 하지만 면접관님으로부터 함수 내부에서 외부의 변수를 참조할 수 있는 건 '클로저(closure)'가 아니냐는 질문이 다시 돌아왔습니다. 순간 제 설명이 클로저와 혼동될 수도 있다는 점과 제가 알고 있던 호이스팅의 정의가 틀렸다는 것을 알게 되었습니다. JS를 공부하고 개발한 사람이라면 호이스팅이 무엇인지 당연히 잘 알고 있어야 할텐데 지금까지 잘못알고 있었다는 것에 안타까움을 느끼며 이 포스팅에서 호이스팅이 무엇인지 제대로 알아보려고 합니다.

## 호이스팅이란?

```javascript
function hello(){
	console.log(greeting); // undefined
	var greeting = "hi";
	console.log(greeting); // hi
}

hello();
```

위의 예시의 `hello()`함수에서는 아직 선언되지 않은 `greeting`변수를 출력합니다. 선언되지 않은 변수를 출력하려고 했으므로 에러를 발생시키며 프로그램이 멈추는 것을 예상하지만 프로그램은 예상 외로 잘 동작하고 대신에 `console.log(greeting)`은 `undefined`를 출력합니다. 그 이유는 `greeting`변수가 `hello()`의 맨 위로 끌어올려졌기(hoisted) 때문입니다. **호이스팅이란 변수를 선언하고 초기화했을 때 선언 부분이 최상단으로 끌어올려지는 현상을 의미합니다.** 위의 예시에서는 `hello()`함수의 내부의 최상단 위치로 `greeting`변수가 끌어올려집니다. V8엔진은 위 코드를 아래와 같이 해석합니다.

```javascript
function hello(){
	var greeting;
	console.log(greeting);
	greeting = "hi";
	console.log(greeting);
}
```
`greeting`과 같이 변수가 최상단으로 끌어올려졌을 때 '변수 호이스팅'이라고 하며 '변수 호이스팅'은 변수의 선언문이 함수의 최상단으로 올라가도록 합니다. 변수의 호이스팅에서 주의할 점은 변수의 선언만 호이스팅이 되고 변수의 값은 호이스팅 되는 게 아니라는 점입니다. `var greeting = "hi"`라고 변수의 선언과 초기화를 한 줄에 같이 했음에도 바로 위에서 `greeting`을 콘솔로 출력했을 때 `undefined`가 출력되는 이유입니다. `"hi"`라는 값까지 호이스팅이 된게 아니라 `greeting`이라는 변수의 선언만 끌어올려진 것입니다. 

## 함수의 호이스팅
호이스팅은 변수 뿐만 아니라 함수에서도 이뤄집니다. 단, 선언식으로 만들어진 함수만 호이스팅이 오류없이 일어날 수 있고 표현식으로 만들어진 함수는 호이스팅이 일어날 경우 오류가 발생합니다. 아래는 함수 호이스팅의 예시입니다.
```javascript
hello();  // undefined hi 출력 

function hello(){
	console.log(greeting);
	var greeting = "hi";
	console.log(greeting);
}

hello2(); // 에러 발생
var hello2 = function(){
	console.log(greeting);
	var greeting = "hi 2";
	console.log(greeting);
}
```
`hello()`함수는 함수선언식으로 만들어짐으로써 함수의 범위가 전역으로 끌어올려집니다. 그래서 `hello()`가 선언되기 전에 `hello()`를 호출했을 때 오류가 발생하지 않고 정상적으로 동작합니다. 하지만 함수표현식으로 만들어진 `hello2()`를 호출했을 때 없는 함수를 호출한다고 간주하여 에러가 발생합니다. 위의 코드를 V8엔진은 아래와 같이 해석하기 때문입니다.
```javascript
function hello(){
	console.log(greeting);
	var greeting = "hi";
	console.log(greeting);
}
hello(); // undefined hi 출력

var hello2;
hello2(); // 에러 발생

hello2 = function(){
	console.log(greeting);
	var greeting = "hi";
	console.log(greeting);
}
```
함수표현식의 호이스팅에서 오류가 발생하는 이유는 위와 같이 `var`로 선언된 `hello2`변수가 전역의 맨 위로 끌어올려지기 때문입니다. 익명함수로 초기화된 것은 호이스팅되지 않고 `hello2`의 선언만이 호이스팅 되어 초기화되지 않은 변수를 함수처럼 호출한다고 간주하기 때문에 오류가 발생합니다.<br>
예전에는 호이스팅이 변수의 범위가 확장된다는 의미에서 끌어올려진다는 뜻인줄 알았는데, 이 포스트를 통해 말 그대로 어떤 범위의 맨 위로 변수의 위치가 올라간다는 뜻임을 알게 되었습니다.
<br><br>
출처 : [함수선언식vs함수표현식](https://joshua1988.github.io/web-development/javascript/function-expressions-vs-declarations/), [스코프와 호이스팅을 알아보자](http://web-front-end.tistory.com/23)
