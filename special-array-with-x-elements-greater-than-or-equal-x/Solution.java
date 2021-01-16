// Accepted
// Runtime: 1 ms
// Memory Usage: 39.3 MB
// Submitted: January 15th, 2021

class Solution {
    public int specialArray(int[] nums) {
        // Get the largest integer in the array
        int max = nums[0];
        for (int i = 1; i < nums.length; i++)
            if (nums[i] > max)
                max = nums[i];
        
        for (int i = 0; i <= max; i++) {
            int count = 0;
            
            // Count the number of integers greater than or equal
            for (int j = 0; j < nums.length; j++) {
                if (nums[j] >= i)
                    count++;
                
                // If we go over, stop counting
                if (count > i)
                    break;
            }
            
            // If it's equal, we found our answer
            if (count == i)
                return i;
        }
        
        return -1;
    }
}