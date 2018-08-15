# Learn Ruby
* [codecademy](https://www.codecademy.com/learn/learn-ruby)에서 ruby의 기초를 배웁니다.

## classes

### constructor
* 생성자는 `initialize`라는 이름의 함수로 정의합니다.

### class variables
* ruby에서 인스턴스 변수는 `@`, 클래스 변수는 `@@`, 전역변수는 `$`로 표현합니다.

### access limitation
* 인스턴스 변수의 R/W 권한을 구분하는 3가지 키워드가 있습니다. 
> attr_reader: 읽기 <br>
다 attr_writer: 쓰기 <br>
> attr_accessor: 읽기,쓰기 <br>
* `attr_reader :name`과 같이 인스턴스 변수의 심볼에 읽기/쓰기를 지정합니다.
* 루비에서 `public`으로 지정된 함수는 클래스 내/외부에서, `private`으로 지정된 함수는 클래스 내부에서만 호출가능합니다.
* 클래스 안에 `public`과 `private` 키워드를 선언하여 그 아래에 메소드를 정의합니다.

### inheritance
* 루비는 다중상속을 허용하지 않습니다. 
* 어떤 클래스를 상속받은 클래스를 만들 때는 `class 자식클래스 < 부모클래스`와 같이 정의합니다.
```ruby
class Person
  def initialize(name, age)
    @name = name
    @age = age
  end  
end

class Kde < Human
  def initialize(name, age)
    super(name, age)
  end
end
```


### 예제코드

```ruby
class Account
  attr_reader :name
  attr_reader :balance
  
  def initialize(name, balance=100) # 생성자
    @name = name
    @balance = balance
  end
  
  public # 접근제한자
  def display_balance(pin_number)
    if pin_number == @pin
      puts "Balance: $#{@balance}."
    else
      puts pin_error
    end
  end
  
  def withdraw(pin_number,amount)
    if pin_number == @pin
      @balance -= amount
      puts "Withdrew #{amount}."
    else
      puts pin_error
    end
  end

  private # 접근제한자
  def pin
    @pin = 1234
  end
  def pin_error
    return "Access denied: incorrect PIN."
  end 
end

checking_account = Account.new("Eric", 1_000_000) # 숫자를 나타낼때는 천단위마다 underscore로 구분합니다.
```

