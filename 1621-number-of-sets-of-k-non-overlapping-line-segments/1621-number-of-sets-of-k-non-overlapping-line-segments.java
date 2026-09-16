class Solution {
    public int numberOfSets(int n, int k) {
        final int MOD = 1_000_000_007;
        long[][] dp = new long[n][k + 1];

        // Base case: 0 segments = 1 way, regardless of how many points we consider
        for (int i = 0; i < n; i++) dp[i][0] = 1;

        for (int j = 1; j <= k; j++) {
            long prefix = 0; // running sum of dp[p][j-1] for p = 0 .. i-1
            for (int i = 0; i < n; i++) {
                long skip = (i > 0) ? dp[i - 1][j] : 0;
                dp[i][j] = (skip + prefix) % MOD;

                // update prefix to include p = i for the next iteration
                prefix = (prefix + dp[i][j - 1]) % MOD;
            }
        }

        return (int) dp[n - 1][k];
    }
}