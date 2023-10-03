struct Solution {}

impl Solution {
    pub fn find_median(v: Vec<i32>) -> f64 {
        let middle = (v.len() / 2) - 1;
        if v.len() % 2 == 1 {
            return v[middle] as f64;
        }

        (v[middle] + v[middle + 1]) as f64 / 2.0
    }

    pub fn find_median_sorted_arrays(a: Vec<i32>, b: Vec<i32>) -> f64 {
        let mut i = 0;
        let mut j = 0;
        let mut current = 0;
        let mut previous = 0;
        let mut count = 0;
        let is_average = (a.len() + b.len()) % 2 == 0;
        let target = (a.len() + b.len()) / 2;

        if target == 0 {
            if a.len() == 0 && b.len() == 0 {
                return 0.0;
            } else if a.len() == 0 {
                return b[0] as f64;
            } else if b.len() == 0 {
                return a[0] as f64;
            }
        }

        if a.len() == 0 {
            return Solution::find_median(b);
        } else if b.len() == 0 {
            return Solution::find_median(a);
        }

        println!("current={current} prev={previous} i={i} j={j}");
        while count <= target {
            previous = current;

            if i < a.len() && a[i] <= b[j] {
                current = a[i];
                i += 1;
            } else {
                current = b[j];
                j += 1;
            }
            println!("current={current} prev={previous} i={i} j={j}");

            // Case in which array A or B is composed entirely of smaller numbers (i.e. [1, 2] and [3, 4]) - join the last and first elements.
            if i == target {
                if is_average {
                    previous = current;
                    current = b[0];
                }
                break;
            } else if j == target {
                if is_average {
                    previous = current;
                    current = a[0];
                }
                break;
            }

            count += 1;
        }

        if is_average {
            (current + previous) as f64 / 2.0
        } else {
            current as f64
        }
    }
}

fn main() {
    // assert_eq!(
    //     Solution::find_median_sorted_arrays(vec![1, 3], vec![2]),
    //     2.0
    // );

    // assert_eq!(
    //     Solution::find_median_sorted_arrays(vec![1, 2], vec![3, 4]),
    //     2.5
    // );

    // println!(
    //     "{}",
    //     Solution::find_median_sorted_arrays(vec![0, 0, 0, 0, 0], vec![-1, 0, 0, 0, 0, 0, 1])
    // );

    println!(
        "{}",
        Solution::find_median_sorted_arrays(vec![1, 3], vec![2])
    );

    // println!(
    //     "{}",
    //     Solution::find_median_sorted_arrays(vec![3], vec![-1, -2])
    // );
}
