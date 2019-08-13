# Definition for singly-linked list.
class ListNode:
    def __init__(self, x):
        self.val = x
        self.next = None


class LinkedList(object):
    def __init__(self):
        self.head = None
        self.tail = None

    @property
    def empty(self):
        return not bool(self.head)
    
    def insert_tail(self, val):
        new_node = ListNode(val)
        if self.empty:
            self.head = new_node
        else:
            self.tail.next = new_node
        self.tail = new_node
        return new_node

    def ll_to_list(self):
        li = []
        p = self.head
        while p:
            li.append(p.val)
            p = p.next
        return li
    
    def print_list(self):
        p = self.head
        while p:
            print(p.val, end=' ')
            p = p.next

class Solution:
    def addTwoNumbers(self, l1: ListNode, l2: ListNode) -> ListNode:
        share = 0
        result = LinkedList()
        
        while l1 and l2:
            sum_ = l1.val + l2.val
            rest = int(sum_ % 10)
            result.insert_tail(rest + share)
            share = int(sum_ / 10)
            l1 = l1.next
            l2 = l2.next

        while l1:
            result.insert_tail(l1.val + share)
            l1 = l1.next
            share = 0
        
        while l2:
            result.insert_tail(l2.val + share)
            l2 = l2.next
            share = 0
        
        return result

def test1():
    l1 = LinkedList()
    l1.insert_tail(2)
    l1.insert_tail(4)
    l1.insert_tail(3)

    l2 = LinkedList()
    l2.insert_tail(5)
    l2.insert_tail(6)
    l2.insert_tail(4)

    s = Solution()
    result = s.addTwoNumbers(l1.head, l2.head).ll_to_list()
    print(result)
    assert result == [7, 0, 8]


def test2():
    l1 = LinkedList()
    l1.insert_tail(5)

    l2 = LinkedList()
    l2.insert_tail(5)

    s = Solution()
    result = s.addTwoNumbers(l1.head, l2.head).ll_to_list()
    print(result)
    assert result == [0, 1]


def test3():
    l1 = LinkedList()
    l1.insert_tail(8)
    l1.insert_tail(9)

    l2 = LinkedList()
    l2.insert_tail(1)

    s = Solution()
    result = s.addTwoNumbers(l1.head, l2.head).ll_to_list() 
    assert result == [0, 9]


if __name__ == '__main__':
    test1()
    test2()
    test3()