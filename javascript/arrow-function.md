# TIL : Arrow function

## 문법
[pg-promise](https://github.com/vitaly-t/pg-promise)의 [Performance Boost](https://github.com/vitaly-t/pg-promise/wiki/Performance-Boost) 문서를 참고하여 1개의 insert 명령어가 여러개의 row 삽입을 수행할 수 있도록 고민하고 있었습니다. 명령어에 들어갈 row들을 sql 문법에 맞게 포맷팅하기 위해 ParameterInsert라는 클래스를 만들었습니다. (문서에서는 ES5의 생성자 함수를 이용했지만 저는 ES6에서 제공하는 클래스를 이용했습니다. ES5의 생성자 함수, 프로토타입 객체, 상속 그리고 ES6의 클래스는 ['여기'](https://github.com/kde6260/Today-I-Learned/blob/master/javascript/class.md)에서 다루겠습니다.) 아래의 코드는 ES6 클래스와 화살표 함수(Arrow function)을 활용한 예입니다.

``` javascript
class ParameterInsert{  
    constructor(pgp, template, data){  
        this.rawType = true;  
        this.toPostgres = () => data.map(d => '(' + pgp.as.format(template, d) + ')').join();  
  }  
}
```

화살표 함수(Arrow function)는 함수의 파라미터가 1개일 때, 그리고 함수의 바디에 오직 return문만 존재할 때 아래와 같이 코드를 매우 간결하게 만들어줍니다. 
```javascript
 this.toPostgres = () => data.map(d => '(' + pgp.as.format(template, d) + ')').join();
 ```
 위 코드는 다음과 동일합니다.
 ``` javascript
this.toPostgres = function(){  
    return data.map(function(d){  
        return '(' + pgp.as.format(template, d) + ')'  
  }).join();  
}
```

'function', 바디(중괄호, {}), 'return'이 사라지고 `(매개변수) => return할 표현식` 으로 함수를 표현할 수 있는 것입니다. 단, 매개변수가 2개 이상일 때는 소괄호로 매개변수들을 감싸고, 바디를 가질 때는 'return'문을 명시해줘야 합니다.

```javascript
this.method = (a,b) => {
  let result = a + b;
  return result;
} 
```


## 'function'과의 차이점
위처럼 화살표함수(arrow function)는 코드를 매우 간결하게 만들어줍니다. 하지만 기존의 함수와 다른 점이 있습니다. **화살표 함수(arrow function)는 고유의 this를 가지지 않는다는 것입니다. 화살표 함수(arrow function) 내부의 this는 화살표 함수를 감싸는 외부 scope의 this를 참조합니다.**

```javascript
var global = this;
var foo = () => this;
console.log(foo() == global);
// true
``` 

```javascript
var global = this;
var foo = function(){
    return this;
}
console.log(foo() == global);
//false
```
javascript에는 전역객체가 존재합니다(brower는 window, Node.js는 global). 위의 코드에서 화살표 함수로 정의된 foo의 this는 foo의 바로 바깥에 위치한 전역의 this를 참조하므로 전역객체의 this와 foo의 this가 일치한다고 출력됩니다. 그러나 'function'으로 정의된 foo는 본인만의 context에서 this를 가지고 있으므로 전역의 this와 다르다고 출력합니다. (단, 'use strict' 모드에서 'function'으로 정의된 this는 call() 또는 apply() 메소드를 통해 콘텍스트를 주입하지 않으면 undefined로 정의됩니다. ['this'에 관한 글](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/this) 참조) 한 가지 예시를 더 보겠습니다.

```javascript
var obj = {
  i: 10,
  b: () => console.log(this.i, this),
  c: function() {
    console.log(this.i, this);
  }
}

obj.b(); // undefined global{..}
obj.c(); // 10 { i: 10, b: [Function: b], c: [Function: c] }
```
b는 obj의 프로퍼티에 속하지만 b의 this는 obj의 바깥에 있는 전역객체의 this를 참조하므로, undefined와 전역객체를 출력합니다. b.call(obj), b.apply(obj)를 호출해도 결과는 동일합니다.  하지만 c의 this는 c의 콘텍스트인 obj를 참조하므로 10과 obj를 출력합니다. 'use strict'모드에서 위의 코드는 아래와 같이 바꿀 수 있습니다.

``` javascript
var obj = {  
    i: 10  
};  
  
var b = () => console.log(this.i, this);  
var c = function() {  
    'use strict'  
  console.log(this.i, this);  
};  
  
b.apply(obj); // undefined {}
c.apply(obj); // 10 { i: 10 }
```

<br>


출처 : [ES6 In Depth: 화살표함수](http://hacks.mozilla.or.kr/2015/09/es6-in-depth-arrow-functions/),  [MDN-this](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/this)
