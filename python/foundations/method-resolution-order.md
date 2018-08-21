## Method Resolution Order

파이썬이 함수를 채택하는 기준과 순서를 이해하는 것은 중요합니다. 코드가 언제, 어디서 호출되는지 알아야 하고, 
특히 다중상속 받는 클래스를 사용하게 될 경우 코드는 아주 복잡해집니다.
이 포스트에서는 Python3의 Method Resolution Order(함수 채택 순서)에 대해 다루겠습니다.
MRO는 'C3 Linearization`이라는 알고리즘에 기반합니다.


### 상속의 문제점
클래스의 상속을 특징으로 갖는 언어로 프로그래밍을 한다고 가정해보세요. 
상속을 처음 다루게 될 때 자식 '클래스는 부모 클래스의 모든 속성과 메서드를 가진다'고 이해할 겁니다.
이 가정은 대부분의 시나리오에서 먹히지만 두 개의 부모클래스들이 같은 클래스를 상속받아서 두 클래스가 같은 속성과 메서드를 갖게 된다면 어떻게 될까요?
자식 클래스는 어떤 부모클래스의 속성과 메서드를 먼저 상속받아야 할까요?
'다이아몬드 상속'으로도 알려진 이 문제를 해결하기 위해 Python3는 `C3 linearization algorithm`이라는 알고리즘을 이용합니다.

### 예시
MRO가 어떻게 동작하는지 예시로 알아보겠습니다. 아무것도 상속받지 않는 2개의 클래스와 이 클래스들을 상속받는 클래스 1개를 정의하겠습니다.
이 코드를 실행시키면 어떻게 될까요?

```python
class awesome():
  def __init__(self):
    print("My awesome class")
  
  def test_func(self):
    print("This is my awesome class")
    
class not_awesome():
  def __init__(self):
    print("My not awesome class")
   
  def test_func(self):
    print("This is my not awesome class")
   

class my_super(awesome, not_awesome):
  def __init__(self):
      print("My Super class")
      
my_class = my_super() # My Super Class 출력
my_class.test_func() # This is awesome class 출력
```
이 코드를 실행하면 `my_super`의 클래스의 `__init__`메소드(생성자)가 먼저 호출됩니다. 
그리고 `awesome`클래스로부터 상속받은 `test_func`메소드를 호출합니다.
`my_super`클래스가 상속 순서를 바꾼다면 `not_awesome`클래스의 `test_func`가 호출될 것입니다. 이 예제의 키포인트는 상속의 순서가 중요하다는 것입니다.

### C3 Linearization
`C3 Linearization`알고리즘이 무엇인지, 동작하는 원리는 무엇인지 알아보겠습니다. 
> 클래스의 `C3 Linearization`는 상속을 받는 클래스, 부모 클래스 linearization, 그리고 부모클래스 그 자체의 목록들이다.<br>

잘 와닿지 않는 설명입니다. 무슨 뜻인지 자세하게 뜯어보겠습니다. 

`C3`는
* 부모 클래스의 선언이 보존되게 합니다.
* 부모 클래스 전에 자식 클래스가 나타나게 합니다.
* 상속에 연관된 각 클래스들이 자신의 바로 위 클래스들에 달라붙도록 합니다.

![](https://tutorialedge.net/images/c3-linearization.png)<br>

먼저 `O`클래스는 아무런 부모클래스를 갖지 않고 클래스 `[A,B,C,D,E]`를 파생시킵니다. `[A,B,C]`클래스는 `K1`의 부모클래스이며, 
`[B,D,E]`는 `K2`, `[D,A]`는 `K3`의 부모클래스입니다. `Z`클래스는 `[K1, K2, K3]`를 상속받습니다.
이렇게 의존성이 복잡한 구조는 일반적인 시스템에서 잘 사용하지 않습니다만, 이 그림은 `C3`를 해부하는데 많은 예를 제시합니다.


| Class Linearization      | Comments |
| :--------- | :----- |
| [O]  | 상대적으로 우선순위가 낮고 부모클래스가 없기 때문에 [O]으로 남습니다. |
| [A] + merge([O],[O])     |  A의 `linearization`은 A + 부모클래스의 linearization + 부모클래스 자체로 구성됩니다. ([O],[O])에서 첫번째 [O]는 O의 linearization(부모클래스가 없으므로 O만 담기게 됩니다.), 두번째 [O]는 O 그 자체를 의미합니다. |
| [A,O]      |  그러므로 최종적인 `linearization`은 [A,O]입니다. |
| [B,O]      |  [B, C, D, E] 도 A와 동일합니다. |
| [K1] + merge(L(A), L(B), L(C), [A,B,C]) | 먼저 `K1`의 부모클래스들의 `linearization`을 찾습니다. 그 다음에 [A,B,C]의 `linearization`을 찾고 [A,B,C] 자체를 찾습니다.
| [K1] + merge([A,O], [B,O], [C,O], [A,B,C]]) | 부모리스트에 A가 가장 먼저 위치하므로 A를 먼저 꺼냅니다. |
| [K1, A] + merge([O], [B, O], [C, O], [B, C]) | O는 2번째 리스트의 끝에 위치하므로(K1의 바로 윗 클래스가 아니므로) 우선순위에서 제외됩니다. 그러므로 A 다음으로 우선하는 클래스는 B입니다.|
| K1, A, B] + merge([O], [O], [C,O], [C]) | 3번째 리스트의 끝에 역시 O가 존재하므로 B 다음으로 우선하는 클래스는 O가 아닌 C입니다. |
| 	[K1, A, B, C] + merge([O], [O], [O]) | 마침내 모든 옵션들이 이미 채택되었으므로 O를 꺼내옵니다. |
| 	[K1, A, B, C, O] | 최종적인 `__mro__`입니다. |

-------------------------------------------------------------
이 글은 ['Python MRO'](https://tutorialedge.net/python/python-method-resolution-order-tutorial/)를 번역, 의역하여 작성한 글입니다.
