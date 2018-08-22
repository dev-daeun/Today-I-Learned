## Quiz
이미 주어진 코드(class Node 와 class BinaryTree 에 의하여)의 구조를 따르는 이진 트리에 대하여, 
트리의 깊이(depth)를 구하는 연산의 구현을 완성하세요.

## My answer
* 어떤 노드에서의 `depth`는 왼쪽노드에서의 `depth`와 오른쪽노드의 `depth` 중 더 큰 값(서브트리의 키가 더 큰 것)에 1을 더한 것(현재 노드로 이어지는 간선 1개)을 의미합니다.
* 그러므로 어떤 트리의 depth를 구하는 것은 곧 루트노드에서 왼쪽노드와 오른쪽노드의 `depth`중 더 큰 것을 찾는 것입니다.
```python
class Node:

    def __init__(self, item):
        self.data = item
        self.left = None
        self.right = None


    def size(self):
        l = self.left.size() if self.left else 0
        r = self.right.size() if self.right else 0
        return l + r + 1


    def depth(self):
        left_d = self.left.depth()  if self.left  else 0  # 왼쪽노드의 depth를 재귀적으로 구합니다.
        right_d = self.right.depth()  if self.right  else 0 # 오른쪽노드의 depth를 재귀적으로 구합니다.
    
        return left_d +1 if left_d > right_d else right_d + 1  # 둘 중에 더 큰 것 + 1이 곧 현재노드의 depth를 나타냅니다.


class BinaryTree:

    def __init__(self, r):
        self.root = r

    def size(self):
        if self.root:
            return self.root.size()
        else:
            return 0


    def depth(self):
        if self.root:
            return self.root.depth()
        else:
            return 0


def solution(x):
    return 0
```
