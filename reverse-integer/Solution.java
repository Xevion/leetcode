// Accepted
// Runtime: 1 ms
// Memory Usage: 35.8 MB
// Submitted: January 14th, 2021

class Solution {
    public int reverse(int x) {
        // Remember whether it was negative or not
        boolean negative = x < 0;
        x = Math.abs(x);
        int reversed = 0;
        
        int degree = (int) Math.floor(Math.log10(x));
        
        while (x > 0) {
            // digit x 10^degree
            reversed += Math.pow(10, degree--) * (x % 10);
            x /= 10;
        }
        
        if (reversed == Integer.MAX_VALUE || reversed == Integer.MIN_VALUE)
            return 0;
        return negative ? -reversed : reversed;
    }
}