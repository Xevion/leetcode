// Accepted
// Runtime: 4 ms
// Memory Usage: 39.1 MB
// Submitted: January 15th, 2021

class Solution {
    public int[] frequencySort(int[] nums) {
        Map<Integer, Integer> count = new HashMap<Integer, Integer>();
        for (int n : nums)
            count.put(n, count.getOrDefault(n, 0) + 1);
        
        PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>((a, b) -> a.getValue() != b.getValue() ? a.getValue() - b.getValue() : b.getKey() - a.getKey());
        pq.addAll(count.entrySet());
        
        int[] sorted = new int[nums.length];
        int i = 0;
        while(!pq.isEmpty()) {
            Map.Entry<Integer, Integer> entry = pq.poll();
            int k = entry.getKey();
            int v = entry.getValue();
            while (v-- > 0)
                sorted[i++] = k;
        }
        
        return sorted;
    }
}