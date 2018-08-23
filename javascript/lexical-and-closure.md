# Lexical enviroment & Closure

## Lexical scoping

아래의 코드를 보세요.
```js
function init() {
  var name = 'Mozilla'; // name변수는 init()함수 내부에서 만들어졌습니다.
  function displayName() { // displayName()은 클로저입니다.
    alert(name); // displayName()에서 init()에 있는 name변수에 접근합니다.   
  }
  displayName();    
}
init();
```

`init()`은 `name`이라는 변수와 `displayName()`이라는 함수를 내부에서 생성합니다. `displayName()`은 `init()` 함수 내부에서 만들어졌으므로 `init()`내부에서만 유효합니다. `displayName()`은 지역변수가 없지만 외부함수의 변수에는 접근할 수 있습니다. 따라서 `displayName()`은 `init()`에 정의된 `name`변수에 접근할 수 있습니다. 만약 `displayName()`내부에 같은 이름의 변수가 있다면 그 변수에 먼저 접근할 것입니다.


외부함수에서 정의된 `name`을 창에 띄우는 `alert()`는 `displayName()`에서 아주 잘 동작합니다. 이러한 **lexical scoping, 어휘적 범위**의 예시는 함수가 중첩으로 정의되었을 때 변수이름들을 어떻게 돌려주는지 보여줍니다. "lexical"이라는 단어는 변수가 어디까지 유효한지 결정하는 소스코드 안에서 변수가 선언되는 위치를 scope이 사용한다는 것을 의미합니다. 중첩된 함수들은 그들의 외부 범위에 선언된 변수들에 접근 할 수 있습니다.

## Closure

아래의 예시를 보겠습니다. 

```js
function makeFunc() {
  var name = 'Mozilla';
  function displayName() {
    alert(name);
  }
  return displayName;
}

var myFunc = makeFunc();
myFunc(); // 'Mozilla' 출력.
```
이 예시는 앞에서 본 코드와 동일한 효과를 지닙니다. 하지만 다른 점, 그리고 흥미로운 점은 `displayName()`함수가 외부 함수에 의해 리턴된다는 것입니다.
처음에 봤을 때는 이 코드가 동작한다는 게 그다지 직관적이지 않습니다. 몇몇 프로그래밍 언어에서 함수의 지역변수는 그 함수가 실행되는 동안에만 유효합니다. `makeFunc()`함수가 종료되고나면 `name`이라는 변수는 스택에서 사라지기 때문에 더이상 접근 할 수 없을 것이라 기대할 것입니다. 하지만 JS는 다른 언어와 다르게 코드가 잘 동작합니다.

이 코드가 동작하는 이유는 JS가 클로저를 만들기 때문입니다. **클로저는 함수와 함수를 정의한 어휘적 환경의 조합을 의미합니다.** 이 환경은 클로저가 생성 될 당시 범위 내에있는 모든 로컬 변수로 구성됩니다. `myFunc`는 `makeFunc()`가 동작할 때 만들어진 `displayName()`함수의 인스턴스를 참조합니다. `displayName()`의 인스턴스는 `name`변수가 존재하는 `displayName()`의 어휘적 환경의 참조합니다. 이러한 이유 때문에 `myFunc`가 만들어질 수 있고 `name`변수는 `alert()`에 넘겨질 수 있는 것입니다.
> 추가 설명 : 다른 프로그래밍 언어의 지역변수는 함수가 호출되었을 때만 유효하다면, JS의 지역변수는 함수가 정의된 환경에서 유효합니다. 앞의 예시에서는 `displayName()`함수가 정의된 `makeFunc()`내부에서 `name`에 접근이 가능하다고 했습니다. 'JS에서는 지역변수가 정의된 환경에서 유효하다는' 점을 감안하면 `myFunc()`를 만들어서 간접적으로 `displayName()`을 호출해도 `displayName()`이 '정의된' 환경 안에 있는 `name`변수를 참조하므로 `name`변수에 대해 referenceError가 나지않고 제대로 동작하는 것입니다.

여기에 더 재밌는 예시가 하나 더 있습니다.
```js
function makeAdder(x) {
  return function(y) {
    return x + y;
  };
}

var add5 = makeAdder(5);
var add10 = makeAdder(10);

console.log(add5(2));  // 7
console.log(add10(2)); // 12
```

이 예시에서 `makeAdder(x)`라는 함수를 정의했습니다. `makeAdder(x)`는 1개의 파라미터 `x`를 받아서 새로운 함수를 리턴합니다. 리턴된 함수는 1개의 파라미터 `y`를 받아서 `x`와 `y`를 더한 값을 리턴합니다.
본질적으로 `makeAdder`는 함수 팩토리입니다. 본인의 파라미터에 특정 값을 더하는 함수를 생성합니다. 위의 예시에서는 새로운 함수 2개를 만들기위해 함수 팩토리를 이용했습니다. `add5`와 `add10`은 모두 클로저입니다. 두 변수는 동일한 함수정의를 가지고 있으나 각각 다른 어휘적 환경을 가집니다. `add5`의 어휘적 환경에서 `x`는 5이고, `add10`의 어휘적 환경에서 `x`는 10인 것을 보면 알 수 있습니다.

## Practical closures

클로저는 어떤 데이터와 데이터 위에서 동작하는 함수를 결합시키고자 할 때 유용합니다. 이러한 용도는 객체의 프로퍼티와 1개 이상의 메소드를 결합시키는 객체지향 프로그래밍과 평행선을 달립니다.
따라서 1개의 메소드를 갖는 객체를 사용할 때 언제나 클로저를 이용할 수 있습니다.

클로저를 이용하는 경우는 웹 프론트에서 흔한 일입니다. 이벤트 기반의 JS로 프론트엔드를 개발할 때 우리는 어떤 행위를 정의하고, 유저에 의해 발생하는 이벤트를 그 행위에 연결합니다. 이벤트는 주로 콜백으로 정의합니다. 예를 들어 텍스트의 크기를 조정하는 버튼을 만든다고 하겠습니다. 한 가지 방법은 `body`엘리먼트에 `font-size`를 특정하고, `em`단위로 다른 엘리먼트의 상대적 폰트크기를 조정하는 것입니다.

```css
body {
  font-family: Helvetica, Arial, sans-serif;
  font-size: 12px;
}

h1 {
  font-size: 1.5em;
}

h2 {
  font-size: 1.2em;
}
```
위와 같은 방법으로 텍스트의 상대적 크기를 조정할 수 있지만 JS에서는 클로저를 이용하여 텍스트 크기를 다르게 할 수 있습니다.

JS 코드를 보겠습니다.

```js
function makeSizer(size) {
  return function() {
    document.body.style.fontSize = size + 'px';
  };
}

var size12 = makeSizer(12);
var size14 = makeSizer(14);
var size16 = makeSizer(16);
```

`size12`,  `size14`, 그리고  `size16`는 `body`텍스트를 각각 12, 14, 16으로 조정하는 함수입니다. 그리고 이 함수를 버튼에 연결하여 버튼이 클릭되었을 때 텍스트크기를 즉각 변경할 수 있게 됩니다. 
```js
document.getElementById('size-12').onclick = size12;
document.getElementById('size-14').onclick = size14;
document.getElementById('size-16').onclick = size16;
```

```html
<a href="#" id="size-12">12</a>
<a href="#" id="size-14">14</a>
<a href="#" id="size-16">16</a>
```

----------------------------------------------------------------
이 글은 ['Mozilla - Closure'](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Closures)를 번역, 의역한 글입니다.
