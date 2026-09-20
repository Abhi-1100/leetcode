class Solution {
    public int reverseDegree(String s) {
        int sum=0;
        for(int i=0;i<s.length();i++){
            int wight = 123 - s.charAt(i);
            sum += wight *(i+1);
        }
        return sum;
    }
}