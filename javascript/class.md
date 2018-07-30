# 클래스

## 1. 생성자 함수 & 프로토타입 객체
JS에서 클래스는 다른 객체지향 언어에서 정의하는 클래스와 전혀 다른 구조를 가지고 있습니다. 아래에는 JS의 클래스를 구성하는 것들입니다.

* 생성자 함수
* 프로토타입 객체

JS는 프로토타입 기반의 상속 메커니즘을 가지고 있습니다. 두 객체가 같은 프로토타입 객체로부터 프로퍼티를 상속받았다면, 두 객체는 같은 클래스의 인스턴스입니다. 그리고 두 객체가 같은 프로토타입 객체를 상속받았다면 대개 같은 생성자 함수를 사용하여 만들어지고, 초기화되었음을 뜻합니다.

```javascript
function inherit(prototype){
	if(prototype == null) throw TypeError();
	if(Object.create) return Object.create(prototype);
	var t = typeof prototype;
	if(t!== "object" && t!=="function") throw TypeError();

	function f() {}; //임시의 빈 생성자 함수 생성.
	f.prototype = prototype; //생성자 함수에게 프로토타입 객체 부여.
	return new f();  //생성자 함수로 prototype 객체를 상속받는 객체 생성 및 반환.
}
```
위 inherit() 함수는 지정된 프로토타입 객체를 상속하는 객체를 임의의 생성자 함수를 통해 생성하고, 반환합니다. 프로토타입 객체를 정의하고 이 객체를 상속받는 객체를 생성하기 위해 inherit()함수를 정의하고 사용한다면, JS에서는 클래스를 정의한 것과 같습니다. 한 가지 예시를 더 보겠습니다.

```javascript
function range(from, to){ //'range' 객체를 생성하는 함수.
	var r = inherit(range.methods);
	r.from = from;
	r.to = to;
	return r;	
}

range.methods = {  //'range' 객체라면 모두 상속받는 프로토타입 객체.
	includes: function(x){ 
		return this.from <= x && x <= this.to; 
	},
	foreach: function(f){
		for(let x  = Meth.ceil(this.from) x <= this.to; x++){}
		f(x);	
	},
	toString: function(){
		return '(' + this.from + '...' + this.to + ')';
	}
};
var r = range(1,3); // range 객체 생성.
r.includes(2) // true
r.foreach(console.log) // 1 2 3 출력
console.log(r); // (1...3) 출력
```
range.methods 프로토타입 객체는 range 함수의 프로퍼티로 저장됩니다. range 함수는 객체를 생성하기 위해 inherit 함수를 호출합니다. 이후 range 함수로 객체를 생성하면, 생성된 모든 객체들은 range.methods 객체를 상속받고 모든 프로퍼티들을 공유하게 됩니다. 하지만 from, to는 인스턴스 필드로, 각 인스턴스마다 고유한 값들을 가지게 됩니다.

**위의 range함수처럼 클래스를 위한 프로토타입 객체를 정의하고, 새 인스턴스를 생성하고 초기화하는 함수를 '팩토리(factory)함수'라고 합니다.** 

위의 range 함수는 inherit() 메소드를 호출할 뿐, 생성자 함수 자체는 아니기 때문에 자주 사용되는 방법은 아닙니다.
아래의 예시에서 생성자 함수를 정의해보겠습니다.

```javascript
function Range(from, to){ //생성자 함수. 내부에서 객체를 따로 생성하지 않습니다.
	this.from = from;
	this.to = to;
}

// 모든 Range 객체는 이 프로토타입 객체를 상속받습니다. 
Range.prototype = {
	includes: function(x){ 
		return this.from <= x && x <= this.to; 
	},
	foreach: function(f){
		for(let x  = Meth.ceil(this.from) x <= this.to; x++){}
		f(x);	
	},
	toString: function(){
		return '(' + this.from + '...' + this.to + ')';
	}
};

var r = new Range(1,3);
r.includes(2) // true
r.foreach(console.log) // 1 2 3 출력
console.log(r); // (1...3) 출력 
```
**모든 객체가 동일한 프로토타입 객체를 상속받기 위해서는 프로토타입 객체가 생성자 함수의 'prototype' 프로퍼티로 정의되어야 합니다.** 생성자 함수의 prototype 프로퍼티가 새 객체의 프로토타입 객체로 사용되는 것입니다. 위와 같은 방식으로 생성자 함수와 프로토타입 객체를 정의할 경우,

1. inherit() 을 정의하고 호출 할 필요가 없어집니다.
2. 생성자 함수는 그저 새 객체의 인스턴스 필드를 초기화하면 되고 새로운 객체를 만들어서 반환하지 않아도 됩니다.
3. 'Range.prototype'과 같이 생성자의 prototype 프로퍼티에 프로토타입 객체를 정의하므로 어떤 객체가 프로토타입 객체인지 구별이 확실해집니다. 
4. 생성자 함수는 일반적인 규칙을 따라 첫 알파벳은 대문자로 선언합니다.

**지금까지 내용을 곰곰히 생각해보면 클래스를 구별하는 핵심 요소는 생성자 함수가 아니라 프로토타입 객체입니다. 
다른 이름을 갖는 서로 다른 두 생성자 함수더라도, 같은 프로토타입 객체를 가진다면, 그 생성자 함수들에 의해 생성된 서로 다른 이름을 갖는 객체들은 같은 인스턴스인 것입니다.**

```javascript
r instanceof Range // r이 Range.prototype을 상속받았다면 true.
```
`instanceof` 연산자는 r이 Range 생성자 함수에 의해 초기화되었는지를 따지는 게 아니라 `Range.prototype`을 상속받았는지를 검사합니다. 
<br>

## 2. constructor 프로퍼티

모든 JS함수는 생성자 함수로 사용될 수 있는데, 대신에 `prototype` 프로퍼티를 가지고 있어야 합니다. 따라서 모든 JS 함수에는 자동으로 `prototype` 프로퍼티가 주어집니다. 

```javascript
var F = function() {};
var p = F.prototype;
var c = p.contructor;
console.log(c===F) // true. 
```
임의의 함수 F에 대해 F.prototype.constructor는 곧 자기 자신과 동일합니다. **모든 함수의 프로토타입 객체는 자신의 생성자 함수를 가리키는 constructor 프로퍼티를 가지고 있다는 것입니다.** 이는 생성자 함수를 통해 클래스를 구별할 수 있다는 뜻입니다.

![그림](https://s3.ap-northeast-2.amazonaws.com/kde6260/%ED%81%B4%EB%9E%98%EC%8A%A4.jpg)

위 그림은 생성자 함수와 프로토타입 객체, 생성자에 의해 만들어진 객체들 간의 관계를 나타내고 있습니다.
<br>

모든 함수가 기본적으로 prototype 프로퍼티를 갖고 있다면, `Range.protype = {...}` 의 경우 Range 함수의 프로퍼티를 별도의 객체로 덮어쓴 것이라고 볼 수 있습니다. 그런데 이 별도의 객체는 constructor 프로퍼티가 없습니다. 그러므로 별도의 객체로 prototype의 객체를 초기화 할 경우, 이 객체에 constructor 프로퍼티를 추가해줘야 합니다. 
```javascript
Range.prototype = {
    constructor: Range,
	includes: function(x){ 
		return this.from <= x && x <= this.to; 
	},
	foreach: function(f){
		for(let x  = Meth.ceil(this.from) x <= this.to; x++){}
		f(x);	
	},
	toString: function(){
		return '(' + this.from + '...' + this.to + ')';
	}
};
```
<br>
constructor 프로퍼티를 새로 추가하지 않고 프로토타입 객체를 초기화하는 방법이 있습니다. 미리 정의되어있는 prototype 프로퍼티에 객체의 프로퍼티를 추가하는 것입니다.

```javascript
Range.prototype.includes = function(x){ 
		return this.from <= x && x <= this.to; 
};
Range.prototype.foreach = function(f){
		for(let x  = Meth.ceil(this.from) x <= this.to; x++){}
		f(x);	
};
Range.prototype.toString = function(){
		return '(' + this.from + '...' + this.to + ')';
};
```
위의 경우 기존에 있던 Range.prototype을 확장하기 때문에 자동으로 생성된 Range.prototype.constructor 프로퍼티를 덮어쓰지 않으므로 따로 constructor 프로퍼티를 추가해야할 필요가 없습니다.
<br>
<br>
출처 : [자바스크립트 완벽 가이드](http://www.insightbook.co.kr/book/programming-insight/%EC%9E%90%EB%B0%94%EC%8A%A4%ED%81%AC%EB%A6%BD%ED%8A%B8-%EC%99%84%EB%B2%BD-%EA%B0%80%EC%9D%B4%EB%93%9C)
