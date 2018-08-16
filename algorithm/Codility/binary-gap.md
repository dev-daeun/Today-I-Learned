## Quiz
양의 정수 `N`의 `binary_gap`은 `N`을 이진수로 변환했을 때 1과 1사이에 채워진 0의 길이를 의미합니다.<br>
예를 들어, 숫자 9는 이진수로 바꿨을 때 `1001`이고, 1과 1사이에 채워진 0의 길이가 2이므로 숫자 9의 `binary_gap`은 2입니다.<br>
숫자 1041의 이진수는 `10000010001`이므로 1041의 `binary_gap`은 5와 4입니다.<br>
양의 정수 `N`이 주어질 때 `N`의 최대 `binary_gap`을 구하는 함수를 작성하세요.<br>
양의 정수 `N`은 1과 2,147,483,647 사이의 정수입니다.<br><br>

최악의 시간복잡도는 `O(log(N))`입니다.<br>
최악의 공간복잡도는 `O(log(1))`입니다.<br>

## 배운 점
* 시간복잡도가 `O(log(N))`이라서 처음에는 이진탐색으로 구현했지만 이진탐색의 `mid`로 선택되지 않는 0 또는 1인 숫자들을 고려하지 않기 때문에
  이진탐색은 틀린 해결법이다.
* 이 문제는 [Codility Lessons](https://app.codility.com/programmers/lessons/1-iterations/)의 첫 번째 문제인데 자고 일어나서 주제가 iteration(순회)인 걸 확인하고 재귀(recursion)와 다름을 깨달은 뒤 
문제를 다시 풀었다. **(문제의 종류와 조건이 주어지면 그게 뭘 의미하는지 생각을 한번 거치고 문제풀이에 뛰어들자.)**

## My answer
* 푸는 데 걸린 시간: 1시간 30분
* 이진수에서 i번째자리의 숫자를 구할 때는 `N`을 `2^(i-1)`로 나눠서 구할 수 있다. (학창시절 수학시간에 이진수를 어떻게 구했는지 생각해보자.)
* `N`이 1이 될 때까지 2로 계속 나눠서 떨어지는 0 또는 1로 `N`의 이진수를 순회한다. 
* `start`라는 변수를 둬서 숫자 1을 만나게 될 때 1이 `binary_gap`의 시작인지, 또는 끝인지 구분한다.
* 끝이라면 지금까지 카운팅된 0의 길이를 결과값이 저장될 `binary_gap`과 비교하여 `binary_gap`변수를 갱신한다.
* 0을 만나게 될 때도 'binary_gap'의 시작이 있는지(이전에 1을 만난적이 있는지)의 조건에 따라서 0을 카운팅한다. 

```python
import math
def solution(N):
    # write your code in Python 3.6
    start = False
    bin_gap = 0
    cnt = 0
    i = math.floor(math.log(N, 2)) + 1

    while i > 0:
        rest = N % 2
        if rest==1:
            if start==False:
                start = True
            else:
                bin_gap = bin_gap if bin_gap >= cnt else cnt
                cnt = 0               
        else:
            if start==True:
                cnt += 1


        i -= 1
        N //= 2
    return bin_gap

```

