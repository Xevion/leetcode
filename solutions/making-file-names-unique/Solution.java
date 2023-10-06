// Accepted
// Runtime: 41 ms
// Memory Usage: 54.7 MB
// Submitted: January 14th, 2021

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Solution {
    HashMap<String, Integer> count = new HashMap<String, Integer>();
    
    public String[] getFolderNames(String[] names) {
        String[] results = new String[names.length];
        
        for (int i = 0; i < names.length; i++) {
            if (count.containsKey(names[i])) {
                int k = count.get(names[i]) + 1;
                String test;
                
                do {
                    test = names[i] + "(" + k++ + ")";
                } while (count.containsKey(test));
                
                count.put(test, 0);
                count.put(names[i], k - 1);
                results[i] = test;
            } else {
                count.put(names[i], 0);
                results[i] = names[i];
            }
        }
        
        return results;
    }
}