## modules
* 루비에서는 `module`으로 `class`와 다른 구조를 정의할 수 있습니다.
* `module`로 인스턴스를 만들 수는 없으나, 함수와 상수를 정의하여 외부에서 참조할 수 있습니다.
```ruby
module Circle

  PI = 3.141592653589793
  
  def Circle.area(radius)
    PI * radius**2
  end
  
  def Circle.circumference(radius)
    2 * PI * radius
  end
end
```
* 모듈의 함수와 상수에 접근할 때는 `Circle::PI`, `Circle::circumference`와 같이 접근합니다.
* 스크립트에서 `require`를 통해 `require Circle`을 명시하면 `Circle::`을 덧붙이지 않아도 됩니다.

### include & extend
* 모듈을 클래스의 내부에서 불러와 참조할 수 있습니다.
* 인스턴스 영역에서 모듈을 사용하고자 할 때는 `include`를, 클래스 영역에서 사용하고자 할 때는 `extend`를 선언합니다.
* 하나의 클래스에서 여러 개의 모듈을 `include` 또는 `extend`하여 다중상속처럼 활용할 수 있습니다.


### include 예제
```ruby
module Action
  def jump
    @distance = rand(4) + 2
    puts "I jumped forward #{@distance} feet!"
  end
end

class Rabbit
  include Action      # include 키워드로 클래스 내부에서 Action모듈을 참조합니다.
  attr_reader :name
  def initialize(name)
    @name = name
  end
end

class Cricket
  include Action      # include 키워드로 클래스 내부에서 Action모듈을 참조합니다.
  attr_reader :name
  def initialize(name)
    @name = name
  end
end

peter = Rabbit.new("Peter")
jiminy = Cricket.new("Jiminy")

peter.jump         # Action모듈의 jump메소드를 호출했습니다.
jiminy.jump        # (Rabbit, Cricket 클래스가 Action모듈을 상속받은 것처럼 보입니다.)
```

### extend 예제
```ruby
module ThePresent
  def now
    puts "It's #{Time.new.hour > 12 ? Time.new.hour - 12 : Time.new.hour}:#{Time.new.min} #{Time.new.hour > 12 ? 'PM' : 'AM'} (GMT)."
  end
end

class TheHereAnd
  extend ThePresent
end

TheHereAnd.now # TheHereAnd클래스로 Action모듈의 now함수를 호출합니다.(이것 또한 상속받은 메소드를 호출한 것처럼 보입니다.)
```

