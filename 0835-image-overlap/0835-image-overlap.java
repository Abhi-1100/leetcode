import java.util.*;

class Solution {
    public int largestOverlap(int[][] img1, int[][] img2) {
        int n = img1.length;
        List<int[]> ones1 = new ArrayList<>();
        List<int[]> ones2 = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (img1[i][j] == 1) ones1.add(new int[]{i, j});
                if (img2[i][j] == 1) ones2.add(new int[]{i, j});
            }
        }


        Map<String, Integer> count = new HashMap<>();
        int best = 0;

        for (int[] p1 : ones1) {
            for (int[] p2 : ones2) {
                int dx = p2[0] - p1[0];
                int dy = p2[1] - p1[1];
                String key = dx + "," + dy;
                int c = count.getOrDefault(key, 0) + 1;
                count.put(key, c);
                best = Math.max(best, c);
            }
        }

        return best;
    }
}