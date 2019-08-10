# Definition for a binary tree node.
class TreeNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None

class Solution:
    def inorderTraversal(self, root: TreeNode) -> List[int]:
        stack = []
        result = []
        cur_node = root

        while True:
            if cur_node:
                stack.append(cur_node)
                cur_node = cur_node.left
            elif stack:
                cur_node = stack.pop()
                result.append(cur_node.val)
                cur_node = cur_node.right
            else:
                break

        return result
