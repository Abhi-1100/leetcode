class Solution {
    public int maxDepth(String s) {
        int depth =0 ;
        int max = 0;
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(c == '('){
                depth++;
                if(depth > max) max = depth;
            }else if(c == ')'){
                depth--;
            }
        }
        return max;
    }
}