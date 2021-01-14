// Accepted
// Runtime: 4 ms
// Memory Usage: 39.1 MB
// Submitted: January 13th, 2021

class Solution {
    public int lengthOfLongestSubstring(String s) {
        int max = 0;
        // Map stores the character's (latest_index + 1)
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        
        int i = 0;
        for (int j = 0; j < s.length(); j++) {
            char c = s.charAt(j);
            
            // If we've already seen this character, move up i (if needed)
            if (map.containsKey(c))
                i = Math.max(map.get(c), i);
            
            max = Math.max(max, j - i + 1);
            map.put(c, j + 1);
        }
        
        return max;
    }
}