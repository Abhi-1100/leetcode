import java.util.*;

class Solution {
    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) order[i] = i;
        Arrays.sort(order, (a, b) -> Integer.compare(intervals.get(a).get(1), intervals.get(b).get(1)));

        int[] l = new int[n];
        long[] r = new long[n];
        int[] w = new int[n];
        int[] origIdx = new int[n];
        for (int i = 0; i < n; i++) {
            int oi = order[i];
            List<Integer> cur = intervals.get(oi);
            l[i] = cur.get(0);
            r[i] = cur.get(1);
            w[i] = cur.get(2);
            origIdx[i] = oi;
        }

        long[][] dpScore = new long[n + 1][5];
        int[][][] dpList = new int[n + 1][5][];

        for (int k = 0; k <= 4; k++) dpList[0][k] = new int[0];
        for (int i = 0; i <= n; i++) dpList[i][0] = new int[0];

        for (int i = 1; i <= n; i++) {
            int curL = l[i - 1];
            int curW = w[i - 1];
            int curIdx = origIdx[i - 1];

            int left = 0, right = i - 1;
            while (left < right) {
                int mid = (left + right) / 2;
                if (r[mid] < curL) left = mid + 1; else right = mid;
            }
            int j = left;

            for (int k = 1; k <= 4; k++) {
                long scoreSkip = dpScore[i - 1][k];
                int[] listSkip = dpList[i - 1][k];

                long scoreTake = dpScore[j][k - 1] + curW;
                int[] listTake = insertSorted(dpList[j][k - 1], curIdx);

                if (compare(scoreTake, listTake, scoreSkip, listSkip) < 0) {
                    dpScore[i][k] = scoreTake;
                    dpList[i][k] = listTake;
                } else {
                    dpScore[i][k] = scoreSkip;
                    dpList[i][k] = listSkip;
                }
            }
        }

        return dpList[n][4];
    }

    private int[] insertSorted(int[] arr, int val) {
        int[] res = new int[arr.length + 1];
        int pos = 0;
        while (pos < arr.length && arr[pos] < val) {
            res[pos] = arr[pos];
            pos++;
        }
        res[pos] = val;
        for (int i = pos; i < arr.length; i++) res[i + 1] = arr[i];
        return res;
    }

    private int compare(long scoreA, int[] listA, long scoreB, int[] listB) {
        if (scoreA != scoreB) return scoreA > scoreB ? -1 : 1;
        int len = Math.min(listA.length, listB.length);
        for (int i = 0; i < len; i++) {
            if (listA[i] != listB[i]) return listA[i] < listB[i] ? -1 : 1;
        }
        return Integer.compare(listA.length, listB.length);
    }
}