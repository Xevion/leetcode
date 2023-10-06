// Accepted
// Runtime: 1 ms
// Memory Usage: 38.9 MB
// Submitted: January 14th, 2021

class Solution {
    public int myAtoi(String s) {
        int sign = 1;
        int i = 0;
        int result = 0;
        if (s.length() == 0) return 0;
        
        // Skip through whitespace
        while (i < s.length() && s.charAt(i) == ' ')
            i++;
        
        // If there is a sign present, extract it
        if (i < s.length() && (s.charAt(i) == '-' || s.charAt(i) == '+'))
            if (s.charAt(i++) == '-')
                sign = -1;
        
        while (i < s.length() && Character.isDigit(s.charAt(i))) {
            if (result > Integer.MAX_VALUE / 10 || (result == Integer.MAX_VALUE / 10 && s.charAt(i) - '0' > Integer.MAX_VALUE % 10)) {
                return (sign == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            
            result = (result * 10) + (s.charAt(i++) - '0');
        }
        
        return result * sign;
    }        
}