// Accepted
// Runtime: 24 ms
// Memory Usage: 39.3 MB
// Submitted: January 13th, 2021

class Solution {
    public String longestPalindrome(String s) {
        int left = 0;
        int right = 0;
        int curMax = 0;
        
        for (int i = 0; i < s.length(); i++) {
            // Check both single and double char palindrome
            int len = Math.max(expandAround(s, i, i), expandAround(s, i, i + 1));
            
            // If palindrome substring found is larger, switch out (calculate new value range)
            if (len > curMax) {
                left = i - (len - 1) / 2;
                right = i + len / 2;
                curMax = len;
            }
        }
        
        return s.substring(left, right + 1);
    }
    
    // Finds the largest palindrome at a specific area
    public int expandAround(String s, int left, int right) {
        // Ensure it doesn't expand outside boundaries
        // Check that left and right character equal eachother
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            // Expand!
            left--;
            right++;
        }
        
        return right - left - 1;
    }
}