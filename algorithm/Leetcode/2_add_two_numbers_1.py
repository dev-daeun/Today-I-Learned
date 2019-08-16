# Definition for singly-linked list.
class ListNode:
    def __init__(self, x):
        self.val = x
        self.next = None


class LinkedList(object):
    def __init__(self):
        self.head = None

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

        carry = 0
        result = LinkedList()

        while l1 or l2:
            n1 = l1.val if l1 else 0
            n2 = l2.val if l2 else 0

            sum_ = n1 + n2 + carry
            carry = sum_ // 10
            share = int(sum_ % 10)
            result.insert_tail(share)

            l1 = l1.next if l1 else None
            l2 = l2.next if l2 else None

        if carry == 1:
            result.insert_tail(carry)

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
    l1.insert_tail(9)
    l1.insert_tail(8)

    l2 = LinkedList()
    l2.insert_tail(1)

    s = Solution()
    result = s.addTwoNumbers(l1.head, l2.head).ll_to_list()
    print(result)
    assert result == [0, 9]


def test4():
    l1 = LinkedList()
    l1.insert_tail(9)
    l1.insert_tail(9)

    l2 = LinkedList()
    l2.insert_tail(1)

    s = Solution()
    result = s.addTwoNumbers(l1.head, l2.head).ll_to_list()
    print(result)
    assert result == [0, 0, 1]

def test5():
    l1 = LinkedList()
    l1.insert_tail(0)

    l2 = LinkedList()
    l2.insert_tail(0)

    s = Solution()
    result = s.addTwoNumbers(l1.head, l2.head).ll_to_list()
    print(result)
    assert result == [0]

def test6():
    l1 = LinkedList()
    l1.insert_tail(1)
    for _ in range(29):
        l1.insert_tail(0)
    l1.insert_tail(1)

    l2 = LinkedList()
    l2.insert_tail(5)
    l2.insert_tail(6)
    l2.insert_tail(4)

    s = Solution()
    result = s.addTwoNumbers(l1.head, l2.head).ll_to_list()
    print(result)
    assert result == [6,6,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1]


if __name__ == '__main__':
    test1()
    test2()
    test3()
    test4()
    test5()
    test6()