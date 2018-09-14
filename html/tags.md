## tags

### input 
* `radio`타입은 `name`속성이 동일한 태그끼리 상호배타 체크가 가능합니다.
```html
 <input id="rar1" type="radio" name="radio1">
 <input id="rar2" type="radio" name="radio1">
```

* 버튼의 속성이 `reset`일 경우 버튼이 포함된 `form`태그 내부의 입력 값들을 없애줍니다.
```html
  <button type="reset">내용 초기화</button>
```
### input 속성
```html  
  <input type="text" value="disabled" disabled>
  <input type="text" value="readonly" readonly>
  <input type="text" required> 
  <input type="text" placeholder="whitespace not valid">
  <input type="text" size="3">
  <input type="text" maxlength="10">
  <input type="radio" id="rd1" name="rar" value="radio1" checked>
  <input type="radio" id="rd2" name="rar" value="radio2">
  <input type="checkbox" value="this is checkbox2" checked>
```    

### label 
* 라벨 내부에 input태그를 넣어주면 해당 라벨은 input을 가리키게 됩니다.(브라우저에서 라벨을 클릭하면 input태그가 활성화됩니다.)
 ```html   
  <label>ID <input type="text"></label>
 ```
* 라벨에 `for`속성으로 `input`태그의 `id`를 지정하면 라벨이 `id`에 해당하는 `input`태그를 가리키게 됩니다.
 ```html
  <label for="secondBox">ID</label>
  <input type="text" id="secondBox">
 ```
### select
```html
    <select name="number" id="number-select" size="2">
      <option value="1">first</option>
      <option value="2">second</option>
      <option value="3">third</option>
      <option value="4">forth</option>
      <option value="5">fifth</option>
    </select>
    <input type="submit">


    <select name="fruit" id="fruit-select" multiple>
      <option value="banana">banana</option>
      <option value="apple">apple</option>
      <option value="peach">peach</option>
    </select>
    <input type="submit">
```
* 옵션에 그룹을 지어줄 때는 `optgroup`태그를 사용합니다.
```html
    <select name="select-opt-group" id="">
      <optgroup label="color">
        <option value="red">red</option>
        <option value="blue">blue</option>
        <option value="black">black</option>
        <option value="white">white</option>
      </optgroup>
      <optgroup label="animal">
        <option value="dog">dog</option>
        <option value="cat">cat</option>
        <option value="rat">rat</option>
        <option value="duck">duck</option>
      </optgroup>
    </select>
```

### button
* 버튼을 만드는 방법은 2가지입니다.(`input`과 `button`)
* `button`태그를 이용하는 게 좀 더 직관적입니다.
```html
    <input type="text" placeholder="text for button test">
    <input type="button" value="Input Button">
    
    <button type="submit">sumbit Button</button>
    <button type="reset">reset Button</button>
    <button type="button">button Button</button>
```

### fieldset
* `fieldset`은 `form`태그 내부에 서로 연관된 양식의 `input`태그들을 묶어서 브라우저에 박스로 그려줍니다.
```html
  <fieldset>
     <legend>Login</legend>
     <label for=""><input type="text" name="username"></label>
     <label for=""><input type="text" name="password"></label>
  </fieldset>
```
   
### css color
* css 색상은 빨강, 초록, 파랑을 각각 0부터 255까지의 16진수로 표현합니다.
* 예를 들어 보라색은 빨강 255, 파랑 255, 초록 0 으로 표현합니다.
* FF(16)은 255이므로 `RGB(FF, FF, FF) = #FFFFFF`입니다.

