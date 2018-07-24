# How Javascript Works: V8, Runtime and Call Stack



Javascript의 동작원리를 배우기 위해 꼭 알아야 할 V8엔진, 런타임, 그리고 호출스택에 관해 다뤄보겠습니다.
<br>
이 글을 통해 Javascript의 동작원리를 깨닫고 non-blocking 중심적인 JS개발에 한 걸음 더 나아가길 바랍니다.

<br>


## V8엔진
V8은 구글에서 개발된 JIT 형식의 자바스크립트 엔진입니다.
> JIT : JIT(Just In Time)는 프로그램을 실제 실행하는 시점에 기계어로 번역하는 컴파일 기법입니다. 이 기법은 프로그램의 실행 속도를 빠르게 하기 위해 사용됩니다. 전통적인 입장에서 컴퓨터 프로그램을 만드는 방법은 두가지가 있는데, 인터프리트 방식과 정적 컴파일 방식입니다. 인터프리트 방식은 실행 중 프로그래밍 언어를 읽어가면서 해당 기능에 대응하는 기계어 코드를 실행하는 반면에, 정적 컴파일은 실행하기 전에 전체 코드를 기계어로 번역합니다.

> JIT 컴파일러는 두 가지의 방식을 혼합한 방식으로 생각할 수 있는데, 실행 시점에서 인터프리트 방식으로 기계어 코드를 생성하고 그 코드를 캐싱하여, 같은 함수가 여러 번 불릴 때 매번 같은 기계어 코드를 생성하는 것을 방지합니다. 최근의 자바 가상 머신과 .NET, V8(node.js)에서는 JIT 컴파일을 지원하고 있습니다.


  V8은 아래와 같이 크게 2가지로 구성되어 있습니다.

![V8엔진구성](https://cdn-images-1.medium.com/max/800/1*X21ybPxqBtfRV5v9rD9J1A.png)


* 메모리 힙(memory heap) : 메모리 할당이 이뤄지는 곳입니다.
* 호출스택(call stack) : 코드가 실행되면서 스택프레임이 쌓이는 곳입니다.

<br>

## 런타임
런타임이란 프로그래밍 언어가 구동되는 환경을 의미합니다. 즉, 프로그램이 실행될 때 존재하는 곳을 뜻합니다. JS의 런타임은 V8, WebAPIs, 이벤트루프, 콜백 큐로 구성됩니다.

WebAPIs는 DOM, AJAX, `setTimeout`과 같은 API들을 제공합니다. Nodejs의 구조를 공부하면서 알게 된 이벤트루프(event loop)와 콜백 큐(callback queue)도
등장합니다.

![webAPI](https://cdn-images-1.medium.com/max/800/1*i9nTlOSPH3q-sCd5-WHg-g.png)


## 호출스택


콜스택은 현재 돌아가는 프로그램이 어디에 있는지를 기록하는 자료 구조입니다. 어떤 함수 안으로 들어가는 순간 해당 함수는 호출스택의 제일 위에 놓이게됩니다. 이 함수에서 돌아오면 스택의 가장 윗 부분이 제거됩니다. JS는 단일쓰레드 기반이므로 호출스택이 1개만 존재합니다.
예를 들어보겠습니다.

```javascript
    function multiply(x, y) {
        return x * y;
    }

    function printSquare(x) {
        var s = multiply(x, x);
        console.log(s);
    }
    printSquare(5);
```

 V8엔진이 기계어로 번역과 실행을 시작할 때 호출스택은 비어있습니다. 이후에 실행이 진행되면서 호출스택은 아래와 같아집니다.

 ![호출스택 변화](https://cdn-images-1.medium.com/max/800/1*1FL2WcODqRrK40rrzA5QQA.png)

 위 그림처럼 호출스택에 하나씩 쌓이는 각각의 함수호출을 스택프레임(Stack Frame)이라고 부릅니다. 아래의 예시는 예외가 발생했을 때 호출스택의 상태를 나타냅니다. 예외가 발생했을 때 호출스택의 상태를 스택트레이스(stacktrace)라고 부릅니다.

 ```javascript
 function foo() {
    throw new Error('SessionStack will help you resolve crashes :)');
}
function bar() {
    foo();
}
function start() {
    bar();
}
start();
```
![스택트레이스](https://cdn-images-1.medium.com/max/800/1*T-W_ihvl-9rG4dn18kP3Qw.png)

'스택날리기'는 호출스택의 최대사이즈를 넘어서 스택에 프레임이 쌓일경우에 발생합니다. 특히 엄격한 테스팅을 거치지 않은 재귀함수가 호출되었을 때 '스택날리기'가 일어날 수 있습니다. 아래의 예시코드를 보겠습니다.

```javascript
function foo() {
    foo();
}
foo();
```

foo()함수 안에서 종료조건없이 foo()를 호출하므로 무한재귀호출이 발생하여, 호출스택에는 foo()가 무한히 쌓이게 됩니다. 결국 호출스택이 허용하는 크기를 넘어서 스택프레임이 계속쌓이게 되고 '스택날리기'가 발생합니다.
![stack-blowing](https://cdn-images-1.medium.com/max/800/1*AycFMDy9tlDmNoc5LXd9-g.png)

호출스택은 1개의 스택프레임을 실행하는 동안 다른 프레임을 실행할 수 없습니다. 예를 들어 브라우저에 대용량 이미지를 전송해야 한다고 가정합시다. 대용량 이미지를 전송하는 함수를 실행하는 동안 브라우저는 사실 아무것도 할 수 없습니다. 블로킹이 되는 것입니다. 이는 브라우저가 이미지가 모두 전송될 때까지 렌더링을 하거나 다른 코드를 실행할 수 없음을 의미합니다. 호출스택에 프레임이 너무많이 쌓여서 브라우저가 스택안에 있는 프레임들을 실행하려고하면, 브라우저는 에러를 발생시키고 사용자에게 브라우저를 종료할 것을 묻습니다.

![브라우저 에러](https://cdn-images-1.medium.com/max/800/1*WlMXK3rs_scqKTRV41au7g.jpeg)

(꽤 좋지 않은 UX입니다.) 이러한 단점을 해결하기 위해 '비동기(asynchronous)' 콜백이 등장합니다.

V8엔진의 메모리 힙에 대한 내용은 [이 글](https://steemit.com/kr/@cicada0014/2wvmzm-javascript)을, WebAPI와 호출스택, 이벤트루프, 콜백 큐의 관계는 [이 글](http://alex-dh.tistory.com/9)을 참조하세요.


<br>
출처 : [JS는 어떻게 동작하는가](https://engineering.huiseoul.com/%EC%9E%90%EB%B0%94%EC%8A%A4%ED%81%AC%EB%A6%BD%ED%8A%B8%EB%8A%94-%EC%96%B4%EB%96%BB%EA%B2%8C-%EC%9E%91%EB%8F%99%ED%95%98%EB%8A%94%EA%B0%80-%EC%97%94%EC%A7%84-%EB%9F%B0%ED%83%80%EC%9E%84-%EC%BD%9C%EC%8A%A4%ED%83%9D-%EA%B0%9C%EA%B4%80-ea47917c8442), [How JavaScript works: an overview of the engine, the runtime, and the call stack](https://blog.sessionstack.com/how-does-javascript-actually-work-part-1-b0bacc073cf)