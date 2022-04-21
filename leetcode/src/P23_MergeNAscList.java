import java.util.Objects;
import java.util.PriorityQueue;

public class P23_MergeNAscList {

    public static void main(String[] args) {
        P23_MergeNAscList.Solution solution = new P23_MergeNAscList().new Solution();
        P23_MergeNAscList.ListNode list1 = new P23_MergeNAscList.ListNode(1, new P23_MergeNAscList.ListNode(3, new P23_MergeNAscList.ListNode(4, null)));
        P23_MergeNAscList.ListNode list2 = new P23_MergeNAscList.ListNode(1, new P23_MergeNAscList.ListNode(2, new P23_MergeNAscList.ListNode(4, null)));
        P23_MergeNAscList.ListNode list3 = new P23_MergeNAscList.ListNode(4, new P23_MergeNAscList.ListNode(8, new P23_MergeNAscList.ListNode(9, null)));
        ListNode[] lists = {list1, list2, list3};
        P23_MergeNAscList.ListNode listNode = solution.mergeKLists(lists);
        printVal(listNode);
    }

    public static void printVal(P23_MergeNAscList.ListNode listNode) {
        if (listNode == null || listNode.val == 0) {
            return;
        }
        System.out.println(listNode.val);
        printVal(listNode.next);
    }

    /**
     * 优先级队列
     */
    class Solution {
        public ListNode mergeKLists(ListNode[] lists) {
            if (lists.length == 0) {
                return null;
            }
            // 虚拟头结点
            ListNode dummy = new ListNode(-1);
            ListNode p = dummy;
            // 优先级队列，最小堆
            PriorityQueue<ListNode> pq = new PriorityQueue<>(
                    lists.length, (a, b) -> (a.val - b.val));
            // 将 k 个链表的头结点加入最小堆
            for (ListNode head : lists) {
                if (head != null) {
                    pq.add(head);
                }
            }

            while (!pq.isEmpty()) {
                // 获取最小节点，接到结果链表中
                ListNode node = pq.poll();
                p.next = node;
                if (node.next != null) {
                    pq.add(node.next);
                }
                // p 指针不断前进
                p = p.next;
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
        P23_MergeNAscList.ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, P23_MergeNAscList.ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            P23_MergeNAscList.ListNode listNode = (P23_MergeNAscList.ListNode) o;
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
