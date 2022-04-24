package leetcode;

import java.util.HashSet;
import java.util.Set;

public class P141_HasCycle {

    /**
     * Set不重复的特性，空间换时间
     */
    public class Solution1 {
        public boolean hasCycle(ListNode head) {
            Set<ListNode> set = new HashSet<ListNode>();
            while (head != null) {
                // 重复元素返回false
                if (!set.add(head)) {
                    return true;
                }
                head = head.next;
            }
            return false;
        }
    }

    /**
     * 快慢指针
     */
    public class Solution2 {
        public boolean hasCycle(ListNode head) {
            if (head == null || head.next == null) {
                return false;
            }
            ListNode slow = head;
            ListNode fast = head.next;
            // 循环条件，快慢指针对应节点不同
            while (slow != fast) {
                // 快指针已经为空或者快指针的下一节点为空说明没有循环节点
                if (fast == null || fast.next == null) {
                    // 到达链表尾部，直接返回
                    return false;
                }
                slow = slow.next;
                fast = fast.next.next;
            }
            // 跳出循环，节点相同
            return true;
        }
    }
}
