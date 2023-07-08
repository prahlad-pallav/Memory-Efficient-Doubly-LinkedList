public class MemoryEfficientDoublyLinkedList<T> {
    private static class Node<T> {
        T data;
        int next;

        Node(T data, int next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node<T>[] elements;
    private int head;
    private int free;

    public MemoryEfficientDoublyLinkedList(int capacity) {
        elements = (Node<T>[]) new Node[capacity];
        head = -1;
        free = 0;

        for (int i = 0; i < capacity; i++) {
            elements[i] = new Node<>(null, i + 1);
        }

        elements[capacity - 1].next = -1;
    }

    public boolean isEmpty() {
        return head == -1;
    }

    public void addFirst(T element) {
        int newIndex = allocateNode();
        elements[newIndex].data = element;
        elements[newIndex].next = head;
        head = newIndex;
    }

    public void addLast(T element) {
        int newIndex = allocateNode();
        elements[newIndex].data = element;

        if (isEmpty()) {
            head = newIndex;
        } else {
            int current = head;
            while (elements[current].next != -1) {
                current = elements[current].next;
            }
            elements[current].next = newIndex;
        }
    }

    public void removeFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("List is empty");
        }

        int nodeToRemove = head;
        head = elements[head].next;
        freeNode(nodeToRemove);
    }

    public void removeLast() {
        if (isEmpty()) {
            throw new IllegalStateException("List is empty");
        }

        int current = head;
        int prev = -1;
        while (elements[current].next != -1) {
            prev = current;
            current = elements[current].next;
        }

        if (prev == -1) {
            head = -1;
        } else {
            elements[prev].next = -1;
        }

        freeNode(current);
    }

    public void printList() {
        int current = head;
        while (current != -1) {
            System.out.print(elements[current].data + " ");
            current = elements[current].next;
        }
        System.out.println();
    }

    private int allocateNode() {
        int newIndex = free;
        free = elements[free].next;
        return newIndex;
    }

    private void freeNode(int index) {
        elements[index].data = null;
        elements[index].next = free;
        free = index;
    }

    public static void main(String[] args) {
        MemoryEfficientDoublyLinkedList<Integer> list = new MemoryEfficientDoublyLinkedList<>(10);

        list.addLast(1);
        list.addLast(2);
        list.addLast(3);

        list.printList(); // Output: 1 2 3

        list.removeFirst();
        list.removeLast();

        list.printList(); // Output: 2
    }
}
