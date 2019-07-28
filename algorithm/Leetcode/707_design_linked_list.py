class Node(object):
    def __init__(self, val, next_=None):
        self.val = val
        self.next_ = next_


class MyLinkedList(object):

    def __init__(self):
        self.head = None

    @property
    def get_list(self):
        ll = []
        if not self.head:
            return ll

        p = self.head
        while p:
            ll.append(p.val)
            p = p.next_
        return ll
    
    @property
    def is_empty(self):
        return not self.head

    @property
    def length(self):
        p = self.head
        if not p:
            return 0
        cnt = 1
        while p.next_:
            cnt += 1
            p = p.next_
        return cnt

    def get(self, index):
        """
        Get the value of the index-th node in the linked list. If the index is invalid, return -1.
        :type index: int
        :rtype: int
        """
        if self.is_empty:
            return -1

        idx = 0
        p = self.head
        while p:
            if idx == index:
                return p.val
            p = p.next_
            idx += 1
        return -1

    def addAtHead(self, val):
        """
        Add a node of value val before the first element of the linked list.
        After the insertion, the new node will be the first node of the linked list.
        :type val: int
        :rtype: None
        """
        new_node = Node(val)
        if self.head:
            new_node.next_ = self.head
        self.head = new_node
        return

    def addAtTail(self, val):
        """
        Append a node of value val to the last element of the linked list.
        :type val: int
        :rtype: None
        """
        if self.is_empty:
            return self.addAtHead(val)

        new_node = Node(val)
        p = self.head
        while p.next_:
            p = p.next_
        p.next_ = new_node
        return

    def addAtIndex(self, index, val):
        """
        Add a node of value val before the index-th node in the linked list.
        If index equals to the length of linked list, the node will be appended to the end of linked list.
        If index is greater than the length, the node will not be inserted.
        If index is negative, the node will be inserted at the head of the list.
        :type index: int
        :type val: int
        :rtype: None
        """
        if index > self.length:
            return
        if index <= 0:
            return self.addAtHead(val)
        new_node = Node(val)
        p = self.head
        idx = 0
        while p:
            prev_p = p
            p = p.next_
            if idx == index - 1:
                new_node.next_ = p
                prev_p.next_ = new_node
                return
            idx += 1
        return

    def deleteAtIndex(self, index):
        """
        Delete the index-th node in the linked list, if the index is valid.
        :type index: int
        :rtype: None
        """
        if self.is_empty:
            return

        p = self.head
        if index == 0:
            self.head = p.next_
            del p
            return

        idx = 0
        while p:
            if not p.next_:
                del p
                return
            prev_p = p
            p = p.next_
            if idx == index - 1:
                prev_p.next_ = p.next_
                del p
                return
            idx += 1
        return


def small_test1():
    ll = MyLinkedList()

    ll.addAtHead(1)
    print(f'll after addAtHead(1): {ll.get_list}')

    ll.addAtTail(3)
    print(f'll after addAtTail(3): {ll.get_list}')

    ll.addAtIndex(1, 2)
    print(f'll after addAtIndex(1, 2): {ll.get_list}')

    print(ll.get(1))
    assert ll.get(1) == 2

    ll.deleteAtIndex(1)
    print(f'll after deleteAtIndex(1): {ll.get_list}')

    print(ll.get(1))
    assert ll.get(1) == 3


def small_test2():
    ll = MyLinkedList()
    assert ll.get(0) == -1

    ll.addAtIndex(1, 2)
    print(f'll after addAtIndex(1, 2): {ll.get_list}')

    assert ll.get(0) == -1
    assert ll.get(1) == -1

    ll.addAtIndex(0, 1)
    print(f'll after addAtIndex(0, 1): {ll.get_list}')

    assert ll.get(0) == 1
    assert ll.get(1) == -1


def small_test3():
    ll = MyLinkedList()
    ll.addAtIndex(-1, 0)
    print(f'll after addAtIndex(-1, 0): {ll.get_list}')

    assert ll.get(0) == 0

    ll.deleteAtIndex(-1)
    print(f'll after deleteAtIndex(-1): {ll.get_list}')


if __name__ == '__main__':
    small_test1()
    small_test2()
    small_test3()
