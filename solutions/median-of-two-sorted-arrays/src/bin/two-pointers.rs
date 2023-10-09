struct Solution {}

impl Solution {
    pub fn find_median_sorted_arrays(a: Vec<i32>, b: Vec<i32>) -> f64 {
        let (a_length, b_length) = (a.len(), b.len());
        let (mut a_pointer, mut b_pointer, mut current, mut previous) = (0, 0, 0, 0);

        // Iterate until middle + 1  [1, 2, 3, 4] => arr[2]
        // On even length arrays, we need the previous value to calculate the median
        for _ in 0..=((a_length+b_length)/2) {
            previous = current;

            if a_pointer != a_length && b_pointer != b_length {
                // Main merge point (as long as both arrays have space to iterate left)
                if a[a_pointer] > b[b_pointer] {
                    current = b[b_pointer];
                    b_pointer += 1;
                } else {
                    current = a[a_pointer];
                    a_pointer += 1;
                }
            } else if a_pointer < a_length {
                // Other array has no more space to iterate
                current = a[a_pointer];
                a_pointer += 1;
            } else {
                // Other array has no more space to iterate
                current = b[b_pointer];
                b_pointer += 1;
            }
        }

        if (a_length + b_length) % 2 == 1 {
            current as f64
        } else {
            (current + previous) as f64 / 2.0
        }
    }
}

fn main() {
    println!(
        "{}",
        Solution::find_median_sorted_arrays(vec![1, 3], vec![2])
    );
}
