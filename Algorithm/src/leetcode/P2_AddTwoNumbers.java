package leetcode;

public class P2_AddTwoNumbers {

    public static void main(String[] args) {
        P2_AddTwoNumbers.Solution solution = new P2_AddTwoNumbers().new Solution();
        ListNode list1 = new ListNode(2, new ListNode(4, new ListNode(3, null)));
        ListNode list2 = new ListNode(5, new ListNode(6, new ListNode(4, null)));
        ListNode listNode = solution.addTwoNumbers(list1, list2);
        System.out.println(listNode.toString());
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
    class Solution {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode dumy = new ListNode(0);
            int carry = 0;
            ListNode temp = dumy;
            while (l1 != null || l2 != null) {
                int a = l1 != null ? l1.val : 0;
                int b = l2 != null ? l2.val : 0;
                int sum = a + b + carry;
                int d = sum % 10;
                carry = sum / 10;
                temp.next = new ListNode(d);
                temp = temp.next;
                if (l1 != null) {
                    l1 = l1.next;
                }
                if (l2 != null) {
                    l2 = l2.next;
                }
            }
            if (carry > 0) {
                temp.next = new ListNode(carry);
            }
            return dumy.next;

        }
    }
}
