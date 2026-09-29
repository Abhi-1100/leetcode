class Solution {
    public boolean hasValidPath(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        
        // Quick checks
        if ((m + n - 1) % 2 != 0) return false; // total length must be even
        if (grid[0][0] == ')' || grid[m-1][n-1] == '(') return false;
        
        // dp[i][j] = set of possible balance values (open - close count) reachable at (i,j)
        // Using boolean[] where index = balance value
        boolean[][][] dp = new boolean[m][n][];
        
        int val0 = grid[0][0] == '(' ? 1 : -1;
        dp[0][0] = new boolean[m + n]; // max possible balance
        if (val0 >= 0) {
            dp[0][0][val0] = true;
        }
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) continue;
                
                int val = grid[i][j] == '(' ? 1 : -1;
                boolean[] cur = new boolean[m + n];
                boolean any = false;
                
                // From top (i-1, j)
                if (i > 0 && dp[i-1][j] != null) {
                    boolean[] prev = dp[i-1][j];
                    for (int b = 0; b < prev.length; b++) {
                        if (prev[b]) {
                            int nb = b + val;
                            if (nb >= 0 && nb < cur.length) {
                                cur[nb] = true;
                                any = true;
                            }
                        }
                    }
                }
                
                // From left (i, j-1)
                if (j > 0 && dp[i][j-1] != null) {
                    boolean[] prev = dp[i][j-1];
                    for (int b = 0; b < prev.length; b++) {
                        if (prev[b]) {
                            int nb = b + val;
                            if (nb >= 0 && nb < cur.length) {
                                cur[nb] = true;
                                any = true;
                            }
                        }
                    }
                }
                
                dp[i][j] = any ? cur : null;
            }
        }
        
        return dp[m-1][n-1] != null && dp[m-1][n-1][0];
    }
}