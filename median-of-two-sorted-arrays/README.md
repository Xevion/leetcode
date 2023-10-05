# median-of-two-sorted-arrays

## Two Pointers

When building a merge sort algorithm, the last step is to merge two sorted arrays - just like with this problem (at least for the brute force soluton).
One interesting thing about this is that we can keep track of how far into the final sorted array we are during this 'merge' step.

For example, merging `[1, 2, 3]` and `[2, 7, 9]`, we can determine which index of the final array we are at.

```
[1] => 1
[1, 2] => 2
[1, 2, 2] => 3
[1, 2, 2, 3] => 4
[1, 2, 2, 3, 7] => 5
[1, 2, 2, 3, 7, 9] => 6
```

Given this, we can also determine at what point we have 'reached' the middle of the final array.
Given this, we can extract the middle value(s) from the final array, iterating just like we do during a merge sort, but only until we reach the middle.

In the example above, we reeach the middle at index 3 - and given half the final array's sie is 3 - that's the index we want to stop at.

The actual implementation is with two pointers - the index we're currently iterating over in each array.
We keep track of the last two values we've seen, and when we reach the middle, we can determine if we need to return one or two values.

> For a real implementation, you could setup two different algorithms, one for even-size final arrays, and one for odd. This would remove a couple instructions fo the loop, but given that the Big O is the same - it's not worth it for Leetcode.

## Binary Search

TODO: Writeup