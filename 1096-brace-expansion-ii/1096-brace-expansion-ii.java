import java.util.*;

class Solution {
    private String expr;
    private int pos;

    public List<String> braceExpansionII(String expression) {
        this.expr = expression;
        this.pos = 0;
        Set<String> result = parseUnion();
        List<String> ans = new ArrayList<>(result);
        Collections.sort(ans);
        return ans;
    }

    // Parses comma-separated concatenation terms, unions them
    private Set<String> parseUnion() {
        Set<String> result = new TreeSet<>();
        result.addAll(parseConcat());
        while (pos < expr.length() && expr.charAt(pos) == ',') {
            pos++; // skip comma
            result.addAll(parseConcat());
        }
        return result;
    }

    // Parses a sequence of adjacent factors, concatenating (cartesian product) them
    private Set<String> parseConcat() {
        List<Set<String>> factors = new ArrayList<>();
        while (pos < expr.length() && expr.charAt(pos) != ',' && expr.charAt(pos) != '}') {
            factors.add(parseFactor());
        }

        Set<String> result = new TreeSet<>();
        result.add("");
        for (Set<String> factor : factors) {
            Set<String> next = new TreeSet<>();
            for (String prefix : result) {
                for (String suffix : factor) {
                    next.add(prefix + suffix);
                }
            }
            result = next;
        }
        return result;
    }

    // Parses either a {..} group or a run of plain letters
    private Set<String> parseFactor() {
        if (expr.charAt(pos) == '{') {
            pos++; // skip '{'
            Set<String> result = parseUnion();
            pos++; // skip '}'
            return result;
        } else {
            // single letter (grammar guarantees letters aren't grouped beyond single chars here,
            // but handle runs safely just in case)
            int start = pos;
            while (pos < expr.length() && Character.isLowerCase(expr.charAt(pos))) {
                pos++;
            }
            Set<String> result = new TreeSet<>();
            result.add(expr.substring(start, pos));
            return result;
        }
    }
}