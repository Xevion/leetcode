// Accepted
// Runtime: 1 ms
// Memory Usage: 39.3 MB
// Submitted: January 15th, 2021

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

class Solution {
    public ListNode addTwoNumbers(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        int carry = 0;
        
        while (a != null || b != null) {
            int x = (a != null) ? a.val : 0;
            int y = (b != null) ? b.val : 0;
            int sum = carry + x + y;
            
            carry = sum / 10;
            cur.next = new ListNode(sum % 10);
            cur = cur.next;
            
            if (a != null) a = a.next;
            if (b != null) b = b.next;
        }
        
        if (carry > 0)
            cur.next = new ListNode(carry);
        
        return dummy.next;
    }
}