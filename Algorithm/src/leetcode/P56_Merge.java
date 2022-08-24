package leetcode;

import java.io.IOException;
import java.util.Arrays;

public class P56_Merge {

    public static void main(String[] args) {
        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        int[][] intervals2 = {{1, 4}, {4, 5}};
        int[][] merge1 = merge(intervals);
        System.out.println(Arrays.toString(merge1));

    }

    public static int[][] merge(int[][] intervals) {
        int len = intervals.length;
        int[][] resultArray = new int[len][2];
        int resultIndex = 0;
        int left;
        int right;
        int prev = 0;
        for (int i = 0; i < len; i++) {
            int[] interval = intervals[i];
            left = interval[0];
            right = interval[1];
            if (i > 0 && left >= prev) {
                int curIndex = (resultIndex - 1) > 0 ? (resultIndex - 1) : 0;
                resultArray[curIndex][1] = right;
            } else {
                int[] temp = new int[2];
                temp[0] = left;
                temp[1] = right;
                resultArray[resultIndex] = temp;
                resultIndex++;
            }
            prev = right;
        }
        return resultArray;
    }


}
