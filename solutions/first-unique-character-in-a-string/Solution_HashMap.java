// Accepted
// Runtime: 23 ms
// Memory Usage: 39.5 MB
// Submitted: January 14th, 2021

import java.util.Map;
import java.util.HashMap;

class Solution {
    public int firstUniqChar(String s) {
        Map<Character, Integer> count = new HashMap<Character, Integer>();
        
        // Count the number of appearances of each character
        for (int i = 0; i < s.length(); i++) {
            char val = s.charAt(i);
            count.put(val, count.getOrDefault(val, 0) + 1);
        }
        
        // Iterate along the array again, and find the first character that only appears once
        for (int i = 0; i < s.length(); i++) {
            if (count.get(s.charAt(i)) == 1)
                return i;
        }
        
        return -1;
    }
}