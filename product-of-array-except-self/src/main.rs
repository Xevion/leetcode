struct Solution {}

impl Solution {
    pub fn product_except_self(nums: Vec<i32>) -> Vec<i32> {
        let mut result = vec![1; nums.len()];
        let mut left = 1;
        let mut right = 1;

        println!("{:?}", result);
        for i in 0..nums.len() {
            // Moving left to right: multiple each number by the preceding number
            result[i] *= left;
            left *= nums[i];
            
            // Moving right to left, multiple each number by the following number
            result[nums.len() - 1 - i] *= right;
            right *= nums[nums.len() - 1 - i];
        }

        result
    }
}

fn main() {
    assert_eq!(
        Solution::product_except_self(vec![1, 2, 3, 4]),
        vec![24, 12, 8, 6]
    );

    assert_eq!(
        Solution::product_except_self(vec![-1, 1, 0, -3, 3]),
        vec![0, 0, 9, 0, 0]
    )
}
