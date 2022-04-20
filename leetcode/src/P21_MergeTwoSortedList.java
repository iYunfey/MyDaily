import java.util.Objects;

public class P21_MergeTwoSortedList {

    public static void main(String[] args) {
        Solution solution = new P21_MergeTwoSortedList().new Solution();
        ListNode list1 = new ListNode(1, new ListNode(3, new ListNode(4, null)));
        ListNode list2 = new ListNode(1, new ListNode(2, new ListNode(4, null)));
        ListNode listNode = solution.mergeTwoLists(list1, list2);
        printVal(listNode);
    }

    public static void printVal(ListNode listNode) {
        if (listNode == null || listNode.val == 0) {
            return;
        }
        System.out.println(listNode.val);
        printVal(listNode.next);
    }


    class Solution {
        public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
            // 虚拟头结点
            ListNode dummy = new ListNode(-1), p = dummy;
            ListNode p1 = list1, p2 = list2;

            while (p1 != null && p2 != null) {
                // 比较 p1 和 p2 两个指针
                // 将值较小的的节点接到 p 指针
                if (p1.val > p2.val) {
                    p.next = p2;
                    p2 = p2.next;
                } else {
                    p.next = p1;
                    p1 = p1.next;
                }
                // p 指针不断前进
                p = p.next;
            }

            if (p1 != null) {
                p.next = p1;
            }

            if (p2 != null) {
                p.next = p2;
            }

            return dummy.next;
        }
    }

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode() {}
     * ListNode(int val) { this.val = val; }
     * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     * }
     */
    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ListNode listNode = (ListNode) o;
            return val == listNode.val && Objects.equals(next, listNode.next);
        }

        @Override
        public int hashCode() {
            return Objects.hash(val, next);
        }

        @Override
        public String toString() {
            return "ListNode{" + val +
                    ", next=" + next +
                    '}';
        }
    }
}
