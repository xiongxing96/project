package leetcode0_50;

/**
 * 给定的n是有效的
 * 删除链表的倒数第N个节点
 * 间隔n,一次扫描法
 * @author xx
 *
 */

public class T19 {
	class ListNode{
		public int value;
		public ListNode next;
	}
	public ListNode removeNthFromEnd(ListNode head, int n) {
		ListNode right = head;
		ListNode left = head;
		for(int i=0;i<n;i++) {
			right = right.next;
		}
		if(right == null) {
			head = head.next;
			return head;
		}
		while(right.next != null) {
			right = right.next;
			left = left.next;
		}
		left =left.next.next;
		return head;
	}
}
