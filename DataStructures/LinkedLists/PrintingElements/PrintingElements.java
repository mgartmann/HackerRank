import java.util.Scanner;

import static java.util.Objects.isNull;

public class PrintingElements {

    static class SinglyLinkedListNode {
        public int data;
        public SinglyLinkedListNode next;

        public SinglyLinkedListNode(int nodeData) {
            this.data = nodeData;
            this.next = null;
        }
    }

    static class SinglyLinkedList {
        public SinglyLinkedListNode head;
        public SinglyLinkedListNode tail;

        public SinglyLinkedList() {
            this.head = null;
            this.tail = null;
        }

        public void insertNode(int nodeData) {
            SinglyLinkedListNode node = new SinglyLinkedListNode(nodeData);

            if (this.head == null) {
                this.head = node;
            } else {
                this.tail.next = node;
            }

            this.tail = node;
        }
    }

    /**
     * Prints the linked list in an recursive way.
     **/
    static void printLinkedListRecursive(SinglyLinkedListNode head) {
        if (isNull(head)) return;

        System.out.println(head.data);
        printLinkedList(head.next);
    }

    /**
     * Prints the linked list in an iterative way.
     * Compared to the recursive function, this means less overhead in terms
     * of stack frames.
     **/
    static void printLinkedList(SinglyLinkedListNode head) {
        while (!isNull(head)) {
            System.out.println(head.data);
            head = head.next;
        }
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        SinglyLinkedList llist = new SinglyLinkedList();

        int llistCount = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < llistCount; i++) {
            int llistItem = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            llist.insertNode(llistItem);
        }

        printLinkedList(llist.head);

        scanner.close();
    }
}
