# Definition for singly-linked list.
class ListNode(object):
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

    def insert_head(self, val):
        new_node = ListNode(val)
        if self.empty:
            self.tail = new_node
        else:
            new_node.next = self.head
        self.head = new_node
        return new_node


def get_ll_length(head):
    p = head
    cnt = 0
    while p:
        cnt += 1
        p = p.next
    return cnt


def add_to_numbers_when_same_size(first, second, result):
    if first.next and second.next:
        carry = add_to_numbers_when_same_size(first.next, second.next, result)
    else:
        carry = 0

    sum_ = first.val + second.val + carry
    result.insert_head(int(sum_ % 10))
    return sum_ // 10


def add_to_numbers_when_diff_size(longer, shorter, result, flag):
    if flag == get_ll_length(shorter):
        return add_to_numbers_when_same_size(longer, shorter, result)
    else:
        carry = add_to_numbers_when_diff_size(longer.next, shorter, result, flag - 1)
        sum_ = longer.val + carry
        result.insert_head(int(sum_ % 10))
        return sum_ // 10


def insert_last_carry(carry, result):
    if carry == 1:
        result.insert_head(carry)


class Solution(object):
    def addTwoNumbers(self, l1, l2):
        first_len = get_ll_length(l1)
        second_len = get_ll_length(l2)

        result = LinkedList()

        if first_len > second_len:
            last_carry = add_to_numbers_when_diff_size(l1, l2, result, first_len)
        elif first_len < second_len:
            last_carry = add_to_numbers_when_diff_size(l2, l1, result, second_len)
        else:
            last_carry = add_to_numbers_when_same_size(l1, l2, result)
        insert_last_carry(last_carry, result)
        return result.head

