// Accepted
// Runtime: 4 ms
// Memory Usage: 39.8 MB
// Submitted: January 13th, 2021

class Solution {
    public String convert(String s, int numRows) {
        if (numRows == 1)
            return s;
        
        // Declare and instantiate with empty strings
        StringBuilder[] levels = new StringBuilder[numRows];
        for (int i = 0; i < levels.length; i++)
            levels[i] = new StringBuilder();
        
        int j = 1; // Start at 1 decrementing since it moves forward immediately before recording
        boolean direction = false; // true = increment, false = decrement
        for (int i = 0; i < s.length(); i++) {
            // If hitting the top or bottom, set direction accordingly
            if (j == 0)
                direction = true;
            else if (j == numRows - 1)
                direction = false;
        
            // Increment or decrement based on the direction
            j += direction ? 1 : -1;            
            levels[j].append(s.charAt(i));
        }
        
        StringBuilder compile = new StringBuilder();
        for (StringBuilder level : levels) {
            compile.append(level);
        }
        return compile.toString();
    }
}