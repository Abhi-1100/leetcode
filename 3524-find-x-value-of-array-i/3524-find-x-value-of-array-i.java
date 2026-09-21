class Solution {
    public long[] resultArray(int[] nums, int k) {
        int n = nums.length;
        long[] result = new long[k];
        long[] cur = new long[k]; // cur[r] = number of subarrays ending at previous index with product % k == r

        for (int j = 0; j < n; j++) {
            int m = nums[j] % k;
            long[] next = new long[k];

            // extend all subarrays that ended at j-1 by multiplying with nums[j]
            for (int r = 0; r < k; r++) {
                if (cur[r] == 0) continue;
                int newR = (int) ((long) r * m % k);
                next[newR] += cur[r];
            }

            // new subarray consisting of nums[j] alone
            next[m] += 1;

            for (int x = 0; x < k; x++) {
                result[x] += next[x];
            }

            cur = next;
        }

        return result;
    }
}