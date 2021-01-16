// Accepted
// Runtime: 7ms
// Memory Usage: 38.5 MB
// Submitted: January 15th, 2021

class Solution {
    public boolean isPalindrome(int x) {
        if (x < 0)
            return false;
        else if (x >= 0 && x <= 9)
            return true;

        byte[] digits = new byte[((int) Math.log10(x)) + 1];
        for (int i = 0; i < digits.length; i++) {
            digits[i] = (byte) (x % 10);
            x /= 10;
        }
        
        int n = digits.length / 2;
        for (int i = 0; i < n; i++) {
            if (digits[i] != digits[digits.length - i - 1])
                return false;
        }
        
        return true;
    }
}