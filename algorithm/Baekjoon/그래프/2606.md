## 백준 2606번 - 바이러스

* 컴퓨터 쌍이 들어온다. >>> 두 원소를 `union`한다.
* 1번 컴퓨터에 의해 감염된 컴퓨터의 수를 센다. >>> 루트가 1인 원소의 갯수를 센다. >>> 루트를 `find`한다.

이 문제는 DFS로 푼 적이 있지만 유니온-파인드로 풀 수도 있습니다.<br>
쌍이 들어올 때마다 두 원소를 union하여 루트를 1로 갖는 집합을 만듭니다.<br>
1과 같은 집합에 있는 원소들이 곧 1번 컴퓨터에 의해 감염된 컴퓨터를 나타내므로, 집합에 있는 모든 원소들의 갯수가 곧 답입니다.<br>
한 가지 주의할 점은, 쌍을 입력받는 데 1이 먼저 들어오지 않을 수 있다는 것입니다.<br>
루트가 1이 아닌 `x`, `y`를 `union`하면 `y`의 루트는 `x`가 됩니다.<br>
이후 1과 `x`를 `union`하고, 각 원소의 루트가 1인지 루프를 돌며 확인할 때, `x`에 의해 1과 연결되어있는 `y`는 루트가 `x`이므로,<br>
1과 같은 집합에 있다 하더라도 카운팅 대상이 아니게 되버립니다.<br>
따라서, `union`에서 `x`, `y`를 합칠 때에는, 루트가 1인 원소가 다른 원소의 부모가 되도록 해야합니다.<br>


## 배운 점


## My answer

```python
import sys


def find(x):
    if x == parent[x]:
        return x
    else:
        p_x = find(parent[x])
        parent[x] = p_x
        return p_x


def union(x, y):
    p_x = find(x)
    p_y = find(y)
    if p_y == 1: 
        parent[p_x] = p_y
    else:
        parent[p_y] = p_x



N = int(sys.stdin.readline())
M = int(sys.stdin.readline())

parent = [0]

for i in range(1, N+1):
    parent.append(i)

for x in range(0, M):
    line = sys.stdin.readline().split(' ')
    x = int(line[0])
    y = int(line[1].split('\n')[0])
    union(x, y)

cnt = 0
for e in range(2, N+1):
    if find(e) == 1: cnt += 1

print(cnt)     
```
