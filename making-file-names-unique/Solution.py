# Accepted
# Runtime: 7932 ms
# Memory Usage: 26.5 MB
# Submitted: January 14th, 2021

class Solution:
    def getFolderNames(self, names: List[str]) -> List[str]:
        result = []
        names_used = set()
        for name in names:
            counter = 0
            test_name = name
            while test_name in names_used:
                counter += 1
                test_name = f'{name}({counter})'
            names_used.add(test_name)
            result.append(test_name)
        return result