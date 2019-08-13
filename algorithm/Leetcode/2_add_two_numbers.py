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
        n1 = n2 = 0
        i = j = 0

        while l1:
            n1 += l1.val * pow(10, i)
            i += 1
            l1 = l1.next
        
        while l2:
            n2 += l2.val * pow(10, j)
            j += 1
            l2 = l2.next
        
        result = LinkedList()
        sum_ = n1 + n2
        if sum_ == 0:
            result.insert_tail(0)
            return result
        while sum_ != 0:
            result.insert_tail(int(sum_ % 10))
            sum_ = sum_ // 10
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