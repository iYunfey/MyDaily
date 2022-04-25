package leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class P3_LengthOfLongestSubstring {

    public static void main(String[] args) {
        Solution solution = new P3_LengthOfLongestSubstring().new Solution();
        int i = solution.lengthOfLongestSubstring("abcabcbb");
        System.out.println(i);
    }

    class Solution {
        public int lengthOfLongestSubstring(String s) {
            Set<Character> set = new LinkedHashSet<>(s.length());
            int result = 0;
            int left = 0;
            for (int right = 0; right < s.length(); right++) {
                char c = s.charAt(right);
                while (set.contains(c)) {
                    set.remove(s.charAt(left));
                    left++;
                }
                set.add(c);
                result = Math.max(result, right - left + 1);
            }
            return result;
        }

        public int lengthOfLongestSubstring2(String s) {
            HashMap<Character, Integer> map = new HashMap<>();
            int max = 0, start = 0;
            for (int end = 0; end < s.length(); end++) {
                char ch = s.charAt(end);
                if (map.containsKey(ch)) {
                    start = Math.max(map.get(ch) + 1, start);
                }
                max = Math.max(max, end - start + 1);
                map.put(ch, end);
            }
            return max;
        }
    }
}
