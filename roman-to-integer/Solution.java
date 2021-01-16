// Accepted
// Runtime: 4ms
// Memory Usage: 39.3 MB
// Submitted: January 15th, 2021

class Solution {
    Map<Character, Integer> convert = new HashMap<Character, Integer>();
    
    public Solution() {
        char[] symbols = new char[]{'I', 'V', 'X', 'L', 'C', 'D', 'M'};
        int[] values = new int[]{1, 5, 10, 50, 100, 500, 1000};
        
        for (int i = 0; i < symbols.length; i++)
            convert.put(symbols[i], values[i]);
    }
    
    public int romanToInt(String s) {
        int n = s.length();
        int sum = 0;
        int prev = convert.get(s.charAt(0));
        int cur = 0;
        
        for (int i = 1; i < n; i++) {
            cur = convert.get(s.charAt(i));
            if (prev < cur)
                cur -= prev;
            else
                sum += prev;
            prev = cur;
        }
        sum += prev;
        
        return sum;
    }
}