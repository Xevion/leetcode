// Accepted
// Runtime: 30 ms
// Memory Usage: 39.1 MB
// Submitted: January 14th, 2021

class Solution {
    public int longestStrChain(String[] words) {
        Map<String, Integer> chain = new HashMap<String, Integer>();
        
        int longest = 0;
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        
        // Iterate from shortest to longest words, as previously sorted
        for (String word : words) {
            chain.put(word, 1); // 1 length chain default
            
            // Skip immediately, no 0 length words will ever be in input
            if (word.length() == 1)
                continue;
            else {
                for (int i = 0; i < word.length(); i++) {
                    // Remove a char at once instance, and render the possible previous chain string
                    StringBuilder sb = new StringBuilder(word);
                    String test = sb.deleteCharAt(i).toString();
                    
                    // Check if the new chain we've found is larger than the last chain
                    if (chain.containsKey(test) && chain.get(test) + 1 > chain.get(word))
                        chain.put(word, chain.get(test) + 1);
                }
            }
            
            // Update answer if we've found a new longest chain
            longest = Math.max(longest, chain.get(word));
        }
        
        return longest;
    }
}