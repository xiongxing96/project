package leetcode0_50;
/*
 * 链表实现两数相加
 */
public class T2 {
	 class ListNode {
	       int val;
	       ListNode next;
	       ListNode(int x) { val = x; }//构造函数
	   }
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
	    //声明头节点
        ListNode root=new ListNode(0);
        //使尾节点指向头节点
        ListNode rear=root;
        //定义一个初始进位
        int carry=0;
        if (l1==null)
            return l2;
        if(l2==null)
            return l1;
        while((l1!=null)&&(l2!=null))
        {
            int rem=(l1.val+l2.val+carry)%10;//加后剩余位
            ListNode node=new ListNode(rem);//以剩余位新声明节点
            rear.next=node;
            carry=(l1.val+l2.val+carry)/10;//计算进位
            //rear始终指向最后一个节点
            rear=node;
            l1=l1.next;
            l2=l2.next;     
        }
        // 当我这个加完后，l1或者l2有多余的则直接放到后面。
        if (l1==null)
            rear.next=l2;
        else
            rear.next=l1;
        //如果加完还有进位，如果没有进位，carry在上面就置位0，所以1就说明有进，然后进位就在后面的一个值+1，在判断是都有进位
        if (carry==1)
        {
            while(rear.next!=null)
            {
                int sum=(rear.next.val+carry);
                rear.next.val=sum%10;
                
                carry=sum/10;
                rear=rear.next;
            }
                
        //如果最后还有进位
            if (carry==1)
            {
                rear.next=new ListNode(1);
            }
        }
        return root.next;    
    }
}
