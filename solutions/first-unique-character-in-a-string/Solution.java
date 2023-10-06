// Accepted
// Runtime: 7 ms
// Memory Usage: 39.6 MB
// Submitted: January 14th, 2021

class Solution {
    public int firstUniqChar(String s) {
        int[] count = new int[26]; //
        int n = s.length();
        
        // Count the appearances of each character
        for (int i = 0; i < n; i++)
            count[s.charAt(i) - 'a']++;
        
        // Iterate only the array again, return the index of the character that only appeared once
        for (int i = 0; i < n; i++)
            if (count[s.charAt(i) - 'a'] == 1)
                return i;
                
        return -1;
    }
}