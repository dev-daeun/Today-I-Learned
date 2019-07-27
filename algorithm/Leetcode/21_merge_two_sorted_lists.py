# Definition for singly-linked list.
class ListNode(object):
    def __init__(self, x):
        self.val = x
        self.next = None


class LinkedList(object):
    def __init__(self):
        self.head = None

    def insert_head(self, node):
        if self.head:
            node.next = self.head.next
        self.head = node

    def add_at_tail(self, node):
        if not self.head:
            self.insert_head(node)
            return
        p = self.head
        while p.next:
            p = p.next
        p.next = node


class Solution(object):
    def mergeTwoLists(self, l1, l2):
        """
        :type l1: ListNode
        :type l2: ListNode
        :rtype: ListNode
        """
        a = l1
        b = l2
        result = LinkedList()

        while a and b:
            if a.val < b.val:
                node = ListNode(a.val)
                a = a.next
            else:
                node = ListNode(b.val)
                b = b.next
            result.add_at_tail(node)

        while a:
            node = ListNode(a.val)
            result.add_at_tail(node)
            a = a.next

        while b:
            node = ListNode(b.val)
            result.add_at_tail(node)
            b = b.next

        return result.head
