class Solution {
    public int longestValidParentheses(String s) {
        Deque<Integer> st = new ArrayDeque<>();
        st.push(-1);
        int maxlen=0;

        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);

            if(c == '('){
                st.push(i);
            }else{
                st.pop();
                if(st.isEmpty()){
                    st.push(i);
                }else{
                    maxlen = Math.max(maxlen , i-st.peek());
                }
            }
        }
        return maxlen;
    }
}