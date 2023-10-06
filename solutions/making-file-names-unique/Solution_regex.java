// Accepted
// Runtime: 549 ms
// Memory Usage: 52.8 MB
// Submitted: January 14th, 2021
// Notes: This solution was pretty bad. I didn't understand how the problem could be solved and made a needlessly more complex and specific solution
// that worked, but it also used RegEx, did operations it didn't need to, and overall was much too complex and aggressive to work well.
// The correct solution was much simpler and more concise. It did technically work, though!

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Solution {
    public String[] getFolderNames(String[] names) {
        Map<String, Set<Integer>> nameCount = new HashMap<String, Set<Integer>>();
        List<String> submit = new ArrayList<String>(names.length);
        Pattern p = Pattern.compile("(.+)\\((\\d+)\\)");
        
        for (int i = 0; i < names.length; i++) {
            String name = names[i];

            Matcher m = p.matcher(name);
            // The filename provided has a k-value suffix
            if (m.matches()) {
                String strippedName = m.group(1);
                int k = Integer.parseInt(m.group(2));
                
                // If the stripped name has already been seen
                if (nameCount.containsKey(strippedName)) {
                    // If the k-value has already been recorded
                    if (nameCount.get(strippedName).contains(k)) {
                        // Name has been seen before
                        if (nameCount.containsKey(name)) {
                            // Get best K value, put new K value in, submit and construct new name
                            k = GetKValue(nameCount.get(name));
                            if (k == -1) {
                                nameCount.get(name).add(-1);
                                k = GetKValue(nameCount.get(name));
                            }
                            nameCount.get(name).add(k);
                            submit.add(name + "(" + k + ")");
                        } else {
                            nameCount.put(name, new HashSet<Integer>());
                            // We add -1 and skip to 1 immediately as this is a sort of special entry
                            nameCount.get(name).add(-1);
                            nameCount.get(name).add(1);
                            submit.add(name + "(1)");
                        }
                    } else {
                        // It hasn't been recorded before
                        nameCount.get(strippedName).add(k);
                        submit.add(name);
                    }
                } else {
                    // Stripped name has never been seen before
                    nameCount.put(strippedName, new HashSet<Integer>());
                    nameCount.get(strippedName).add(k);
                    submit.add(name);
                }
            } else {
                // Name has been seen before
                if (nameCount.containsKey(name)) {
                    // Get best K value, put new K value in, submit and construct new name
                    int k = GetKValue(nameCount.get(name));
                    nameCount.get(name).add(k);
                    if (k == -1) {
                        submit.add(name);
                    } else {
                        submit.add(name + "(" + k + ")");
                    }
                } else {
                    nameCount.put(name, new HashSet<Integer>());
                    nameCount.get(name).add(-1);
                    submit.add(name);
                }
            }
        }
        
        return submit.toArray(new String[0]);
    }
    
    public int GetKValue(Set<Integer> used) {
        if (!used.contains(-1))
            return -1;
        
        for (int i = 1;; i++) {
            if (!used.contains(i))
                return i;
        }
    }
}