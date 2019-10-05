package leetcode0_50;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class T23 {
	 public static class ListNode {
		    int val;
		    ListNode next;
		    ListNode(int x) { this.val = x; }
		}
    public ListNode mergeKListNode(List<ListNode> list){
        PriorityQueue<ListNode> queue = new PriorityQueue<>(Comparator.comparing(node -> node.val));
        for (ListNode node :list){
            queue.add(node);
        }
        ListNode head = new ListNode(0);
        ListNode tail = head;
        while(!queue.isEmpty()){
            tail.next= queue.poll();
            tail = tail.next;
            if(tail.next != null){
                queue.add(tail.next);
            }
        }
        return head.next;
    }
    public static void main(String[] args) {
    	ListNode h1 = new ListNode(3);
    	ListNode h2 = new ListNode(2);
    	List<ListNode> list  = new ArrayList<ListNode>();
    	list.add(h1);
    	list.add(h2);
    	ListNode node = new ListNode(0);
    	node = new T23().mergeKListNode(list);
    	//node = mergeKListNode(list);直接用方法得加static静态才能全局调用
    	while(node!=null) {
    	System.out.println(node.val);
    	node = node.next;
    	}
    }
}
