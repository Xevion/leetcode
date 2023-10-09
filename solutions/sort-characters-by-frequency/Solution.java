// Accepted
// Runtime: 10 ms
// Memory Usage: 40 MB
// Submitted: January 14th, 2021

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class Solution {
    public String frequencySort(String s) {
        // Get each character's frequency
        Map<Character, Integer> count = new HashMap<Character, Integer>();
        for (char c : s.toCharArray())
            count.put(c, count.getOrDefault(c, 0) + 1);
        
        // Paired with the character, sort each frequency
        PriorityQueue<Map.Entry<Character, Integer>> pq = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        pq.addAll(count.entrySet());
        
        // Building the final st
        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            Map.Entry<Character, Integer> entry = pq.poll();
            char v = (char) entry.getKey();
            int n = (int) entry.getValue();
            
            // Place n many v chars in the StringBuilder
            for (int i = 0; i < n; i++)
                sb.append(v);
        }
        
        return sb.toString();
    }
}