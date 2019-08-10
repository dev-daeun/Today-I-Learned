# Definition for a binary tree node.
class TreeNode:
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None


class Solution:
    def preorderTraversal(self, root: TreeNode) -> List[int]:
        cur_node = root
        stack = [cur_node]
        result = []

        while stack:
            cur_node = stack.pop()
            if cur_node:
                result.append(cur_node.val)
                if cur_node.right:
                    stack.append(cur_node.right)
                if cur_node.left:
                    stack.append(cur_node.left)

        return result
