import java.util.*;

class Solution {
    public List<String> maxNumOfSubstrings(String s) {
        int n = s.length();
        int[] first = new int[26];
        int[] last = new int[26];
        Arrays.fill(first, -1);
        Arrays.fill(last, -1);

        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';
            if (first[c] == -1) first[c] = i;
            last[c] = i;
        }

        List<int[]> intervals = new ArrayList<>(); // [start, end]

        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';
            if (first[c] != i) continue; // only start at first occurrence of a char

            int end = last[c];
            int j = i;
            boolean valid = true;

            while (j <= end) {
                int cj = s.charAt(j) - 'a';
                if (first[cj] < i) {
                    valid = false;
                    break;
                }
                end = Math.max(end, last[cj]);
                j++;
            }

            if (valid) {
                intervals.add(new int[]{i, end});
            }
        }

        // Sort by end ascending; for same end, sort by start descending (shorter interval first)
        intervals.sort((a, b) -> {
            if (a[1] != b[1]) return a[1] - b[1];
            return b[0] - a[0];
        });

        List<String> result = new ArrayList<>();
        int lastEnd = -1;

        for (int[] interval : intervals) {
            if (interval[0] > lastEnd) {
                result.add(s.substring(interval[0], interval[1] + 1));
                lastEnd = interval[1];
            }
        }

        return result;
    }
}