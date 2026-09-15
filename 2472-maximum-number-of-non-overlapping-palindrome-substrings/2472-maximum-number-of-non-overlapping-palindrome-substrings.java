class Solution {
    public int maxPalindromes(String s, int k) {
        int n = s.length();
        if (n < k) return 0;

        // pal[i][j] = true if s[i..j] (inclusive) is a palindrome
        boolean[][] pal = new boolean[n][n];
        for (int i = 0; i < n; i++) pal[i][i] = true;
        for (int i = 0; i + 1 < n; i++) pal[i][i + 1] = (s.charAt(i) == s.charAt(i + 1));

        for (int len = 3; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                pal[i][j] = (s.charAt(i) == s.charAt(j)) && pal[i + 1][j - 1];
            }
        }

        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i - 1]; // skip position i-1

            // try palindrome of length exactly k ending at i-1
            if (i >= k && pal[i - k][i - 1]) {
                dp[i] = Math.max(dp[i], dp[i - k] + 1);
            }

            // try palindrome of length exactly k+1 ending at i-1
            if (i >= k + 1 && pal[i - k - 1][i - 1]) {
                dp[i] = Math.max(dp[i], dp[i - k - 1] + 1);
            }
        }

        return dp[n];
    }
}