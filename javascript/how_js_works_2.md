# JS는 어떻게 동작하는가2 : 이벤트루프와 비동기 프로그래밍

이번 포스팅에서는 싱글쓰레드 환경에서 하는 프로그래밍의 단점을 리뷰하고 이벤트루프와 콜백에 대해 알아보겠습니다.

## 왜 단일쓰레드는 한계가 있는가?
이전 포스팅에서 우리는 호출스택에 어마어마한 스택프레임이 쌓일 경우 어떻게 되는지 공부했습니다. 예를 들어 복잡한 이미지 전송 알고리즘이 브라우저에서 돌아간다고 상상해보세요. 호출스택이 함수들을 스택에서 꺼내 실행하는 동안 브라우저는 아무것도 하지 못합니다. (블로킹 되어버립니다.) UI가 멈춰버리는 거죠.

![브라우저 블로킹](https://cdn-images-1.medium.com/max/800/1*MCt4ZC0dMVhJsgo1u6lpYw.jpeg)

## JS프로그램의 기본 요소
당신은 아마 JS프로그램을 하나의 .js파일에 작성할 것입니다. 그러나 당신의 프로그램은 여러개의 작업으로 구성되어 있습니다. 오직 한 개의 작업만 현재 실행중에 있고, 나머지는 나중에 실행될 것입니다. 여기서 작업은 대개 함수를 의미합니다.

JS를 시작한지 얼마 안 된 개발자들이 대부분 겪는 문제는 '나중에'라는 것이 필연적으로 지금의 직후라는 것을 보장하지 않는다는 것입니다. 말하자면, 지금 당장 종료될 수 없는 작업들은 비동기적으로 종료된다는 것입니다. 당신이 무의식적으로 예상한 것과 다르게 동기 방식으로 작업들이 처리되지 않을 것입니다.

```javascript
// ajax(..) is some arbitrary Ajax function given by a library

var response = ajax('https://example.com/api');

console.log(response);

// `response` won't have the response
```
당신은 Ajax가 동기적으로 종료되지 않을 거란 걸 알고 있을 겁니다. ajax함수가 코드를 완료할 때까지 response 변수는 아직 값을 가질 수 없음을 뜻합니다. **비동기 함수가 결과를 리턴할 때까지 '기다리도록' 하는 한 가지 방법은 콜백함수를 쓰는 것입니다.**
```javascript
ajax('https://example.com/api', function(response) {

   console.log(response); // `response` is now available

});
```

Ajax를 동기적으로 만들 수는 있으나, 절대 그러지 마세요. 만약 Ajax요청을 동기적으로 작성할 경우 당신의 JS 어플리케이션 UI는 멈추게 될 것입니다. 사용자는 클릭, 데이터입력, 이동, 스크롤 등 아무것도 할 수 없게 됩니다.


## 이벤트루프 해부하기
ES6까지 비동기를 허용했음에도 JS는 아직 직관적인 비동기의 개념을 스스로 탑재하고 있지 않습니다. JS 엔진은 주어진 한 순간에 한 개의 작업을 실행하는 것 외에는 아무것도 하지 않습니다.

그럼 누가 JS엔진이 작업을 마쳤다고 알려주는 걸까요? 현실적으로 JS엔진은 혼자서 돌아가지 않습니다. JS엔진은 브라우저나 Node.js와 같은 환경에서 돌아갑니다. 최근에 JS는 로봇부터 전구까지 모든 종류의 디바이스들에 내장되어 있습니다. 모든 디바이스들은 JS엔진이 동작하기 위한 각기 다른 종류의 호스팅 환경인 것입니다.

모든 환경의 공통점은 '이벤트루프'라는 내장 메커니즘을 갖고 있다는 것입니다. 이벤트루프는 여러개의 작업들을 실행하고, 실행할 때마다 JS엔진을 건드립니다. 이는 JS엔진은 임의의 코드를 실행하기 위해 그저 요구에 따라 움직이는 실행환경임을 뜻합니다. JS엔진은 이벤트(JS코드의 실행)을 스케줄링하는 주변환경인 것입니다.

예를 들어 JS 프로그램이 서버에서 데이터를 가져오기 위해 Ajax 요청을 했다고 가정해봅시다. 당신은 콜백함수 안에 응답코드를 짤 것이고 JS엔진은 호스팅 환경에게 이렇게 말합니다. "내가 지금 실행을 중지할거야, 하지만 너가 네트워크 요청을 끝내서 데이터를 들고 있으면, 언제든지 이 함수를 호출해줘."

그런 다음 브라우저는 네트워크에서 응답을 수신하도록 설정합니다. 응답을 수신하면 이벤트 루프에 콜백함수를 삽입하여 콜백함수가 실행되도록 할 것입니다. 아래의 그림을 보겠습니다.

![이벤트루프와 콜백](https://cdn-images-1.medium.com/max/800/1*FA9NGxNB6-v1oI2qGEtlRQ.png)

여기서 WebAPIs란 뭘까요? WebAPIs는 당신이 접근할 수 없는 쓰레드들입니다. 그저 호출만 할 수 있습니다. WebAPIs는 동시성이 시작되는 브라우저의 조각들입니다.(번역 애매함) 만약 당신이 Node.js 개발자라면 이들은 C++ API에 해당합니다. 그렇다면 이벤트루프는 과연 무엇일까요?

![이벤트루프](https://cdn-images-1.medium.com/max/800/1*KGBiAxjeD9JT2j6KDo0zUg.png)

**이벤트루프는 간단한 일을 합니다. - 호출스택과 콜백 큐를 모니터링 합니다. 호출스택이 비어있다면, 콜백 큐에서 이벤트를 가져와 호출스택에 삽입하여 호출스택이 효율적으로 운영되도록 합니다.** 이러한 반복작업을 '틱(tick)'이라고 부릅니다. 콜백큐에 삽입되고 빠져나와서 호출스택에 쌓이는 이벤트는 콜백함수입니다. 아래의 코드를 실행할 때 어떤 일이 발생하는지 알아보겠습니다.

```javascript
console.log('Hi');
setTimeout(function cb1() {
   console.log('cb1');
}, 5000);
console.log('Bye');
```
<br><br>
1.깨끗한 상태입니다. 콘솔과 호출스택은 비어있습니다.

![1](https://cdn-images-1.medium.com/max/800/1*9fbOuFXJHwhqa6ToCc_v2A.png)
<br>
2.`console.log('Hi')` 가 호출스택에 쌓입니다.

![2](https://cdn-images-1.medium.com/max/800/1*dvrghQCVQIZOfNC27Jrtlw.png)
<br>
3.`console.log('Hi')` 가 실행됩니다.

![3](https://cdn-images-1.medium.com/max/800/1*yn9Y4PXNP8XTz6mtCAzDZQ.png)
<br>

4.실행된 `console.log('Hi')` 는 호출스택에서 제거됩니다.

![4](https://cdn-images-1.medium.com/max/800/1*iBedryNbqtixYTKviPC1tA.png)
<br>
5.`setTimeout(function cb1() { ... })` 이 호출스택에 삽입됩니다.

![5](https://cdn-images-1.medium.com/max/800/1*HIn-BxIP38X6mF_65snMKg.png)
<br>

6.`setTimeout(function cb1() { ... })` 이 실행됩니다. 콘솔은 WebAPIs에 요청을 날려 타이머를 생성합니다. 타이머는 카운트다운을 처리합니다.

![6](https://cdn-images-1.medium.com/max/800/1*vd3X2O_qRfqaEpW4AfZM4w.png)
<br>
7.`setTimeout(function cb1() { ... })` 자체의 호출이 완료되면 호출스택에서 제거됩니다.

![7](https://cdn-images-1.medium.com/max/800/1*_nYLhoZPKD_HPhpJtQeErA.png)
<br>
8. `console.log('Bye')` 이 호출스택에 삽입됩니다.

![8](https://cdn-images-1.medium.com/max/800/1*1NAeDnEv6DWFewX_C-L8mg.png)
<br>
9.`console.log('Bye')` 이 실행됩니다.

![9](https://cdn-images-1.medium.com/max/800/1*UwtM7DmK1BmlBOUUYEopGQ.png)
<br>
10. `console.log('Bye')`  이 호출스택에서 제거됩니다.

![10](https://cdn-images-1.medium.com/max/800/1*-vHNuJsJVXvqq5dLHPt7cQ.png)
<br>
11. 5000 밀리초가 지나면, 타이머가 종료되고 타이머는 `cb1` 콜백을 콜백 큐에 삽입합니다.

![11](https://cdn-images-1.medium.com/max/800/1*eOj6NVwGI2N78onh6CuCbA.png)
<br>
12. 이벤트루프는 `cb1`을 콜백 큐로부터 꺼내와서 호출스택에 삽입합니다.

![12](https://cdn-images-1.medium.com/max/800/1*jQMQ9BEKPycs2wFC233aNg.png)
<br>
13.`cb1`  이 실행되고 `console.log('cb1')`  이 호출스택에 삽입됩니다.

![13](https://cdn-images-1.medium.com/max/800/1*hpyVeL1zsaeHaqS7mU4Qfw.png)
<br>
14. `console.log('cb1')` 이 실행됩니다.

![14](https://cdn-images-1.medium.com/max/800/1*lvOtCg75ObmUTOxIS6anEQ.png)
<br>

15.`console.log('cb1')` 이 호출스택에서 제거됩니다.

![15](https://cdn-images-1.medium.com/max/800/1*Jyyot22aRkKMF3LN1bgE-w.png)
<br>

16. `cb1` 이 호출스택에서 제거됩니다.

![16](https://cdn-images-1.medium.com/max/800/1*t2Btfb_tBbBxTvyVgKX0Qg.png)
<br>
빠른 사진으로 보겠습니다.

![recap](https://cdn-images-1.medium.com/max/800/1*TozSrkk92l8ho6d8JxqF_w.gif)

ES6부터는 이벤트루프가 어떻게 동작해야 하는지 ES6가 직접 결정합니다. 이는 기술적으로 호스팅 환경에 의존하지 않고 JS엔진의 범위에서 책임진다는 뜻입니다. 이러한 변화의 주된 이유중 하나는 'Promise'의 도입에 있습니다. 이벤트루프의 스케줄링 동작에 직접적이고 세밀한 제어를 가해야 한다는 요구가 있기 때문입니다.

## ES6가 하는 일은 무엇인가 ?

ES6에서는 '작업 큐'라는 새로운 개념이 등장합니다. 작업 큐는 이벤트루프의 맨 위에 위치한 계층입니다. Promise로 비동기를 처리할 때 가장 자주 접하게 될 것입니다. (Promise로 비동기를 처리하는 것은 비동기 행위들이 어떻게 스케줄링되고 처리되는지 알아보고 난 뒤에 ['JS는 어떻게 동작하는가3'](https://github.com/kde6260/Today-I-Learned/blob/master/javascript/how_js_works_3.md)에서 다루도록 하겠습니다.)

작업 큐는 이벤트루프의 각각의 '틱(tick)' 끝에 위치한 큐입니다. 이벤트루프가 '틱'을 할 동안 일어나는 특정 비동기 행위들은 콜백 큐에 새로운 이벤트로서 삽입되는게 아니라, 가장 최근의 틱의 작업큐에 삽입됩니다.
즉, 콜백 큐에 삽입되어있는 다른 어떠한 작업들보다 먼저 실행될 것이라는 확신을 가질 수 있습니다.
> 우선순위 : 현재 '틱'되고 있는 작업의 작업 큐에 있는 이벤트 > 콜백 큐의 head에 위치한 이벤트

작업 큐 내부에 위치한 작업은 같은 큐의 back에 삽입된 다른 작업들을 부를 수 있습니다. 그렇게 되면 이론적으로 '작업루프'가 무한히 돌 가능성이 있으므로, 이벤트 루프에서 '틱'해야할 다른 이벤트들을 영영 실행하지 못하게 됩니다.  `while (true)` 처럼 무한루프가 도는 것과 비슷하다고 볼 수 있습니다.
작업 큐는 `setTimeout (callback, 0)` 해킹과 비슷하지만 가능한 빨리 더 잘 정의되고 잘 보장된 순서를 도입하는 방식으로 구현됩니다.

## **콜백(Callbacks)**

이미 알고 있듯이 콜백은 JS 프로그램에서 비동기를 표현하고 관리하기 위해 가장 흔하게 사용되고 있는 수단입니다. 콜백은 JS에서 가장 기본적인 비동기패턴입니다. 아주 정교하고 복잡한 JS프로그램들 조차 콜백으로 작성되고 있습니다. 아래에서는 이러한 콜백을 기반으로 왜 정교한 비동기작업이 필요하고 요구되는지 알아보겠습니다.


## 중첩콜백(Nested Callbacks)

아래의 코드를 참조하세요.
```javascript
listen('click', function (e){
   setTimeout(function(){
   ajax('https://api.example.com/endpoint', function (text){
      if (text == "hello") {
         doSomething();
      }
      else if (text == "world") {
         doSomethingElse();
      }
   });
  }, 500);
});
```


3개의 함수가 중첩된 걸 볼 수 있습니다. 각각의 함수는 비동기 순서의 한 단계를 나타냅니다. 이러한 코드를 '콜백지옥'이라고 부르기도 합니다. 콜백지옥은 중첩되는 들여쓰기보다 더 큰 문제를 가지고 있습니다.
첫번째로, 우리는 클릭 이벤트를 기다리고, 타이머가 동작하기를 기다리고, 그 다음에 ajax가 응답하기를 기다립니다. 그리고 이러한 순서는 클릭이벤트가 발생할 때마다 반복됩니다. 이 코드는 다음과 같이 단계를 밟아갈 때 비동기성을 더 자연스럽게 보여줍니다.

첫번째,
```javascript
listen('click', function (e) {
// ..
});
```

두번째,
```javascript
setTimeout(function(){
// ..
}, 500);
```
세번째,
```javascript
ajax('https://api.example.com/endpoint', function (text){
// ..
});
```
마지막,
```javascript
if (text == "hello") {
   doSomething();
}
else if (text == "world") {
   doSomethingElse();
}
```
위와같은 순서로 비동기를 표현하는 게 훨씬 더 자연스러워보입니다. 위와 같은 방식으로 코드를 표현하기 위해 Promise를 적용합니다. Promise에 대해서는 ['JS는 어떻게 동작하는가3'](https://github.com/kde6260/Today-I-Learned/blob/master/javascript/how_js_works_3.md)에서 다루겠습니다.

---------------------
본 포스팅은 [Alexander Zlatkov의 글](https://blog.sessionstack.com/how-javascript-works-event-loop-and-the-rise-of-async-programming-5-ways-to-better-coding-with-2f077c4438b5)을 번역, 의역하여 작성된 글입니다.
