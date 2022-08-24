package leetcode;

import java.io.IOException;
import java.util.*;

public class P56_Merge {

    public static void main(String[] args) {
        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        int[][] merge1 = merge(intervals);
        System.out.println(Arrays.deepToString(merge1));
        int[][] intervals2 = {{1, 4}, {4, 5}};
        int[][] merge2 = merge(intervals2);
        System.out.println(Arrays.deepToString(merge2));
        int[][] intervals3 = {{1, 4}, {0, 4}};
        int[][] merge3 = merge(intervals3);
        System.out.println(Arrays.deepToString(merge3));
        int[][] intervals4 = {{1, 4}, {2, 3}};
        int[][] merge4 = merge(intervals4);
        System.out.println(Arrays.deepToString(merge4));
        int[][] intervals5 = {{2, 3}, {4, 5}, {8, 9}, {1, 10}};
        int[][] merge5 = merge(intervals5);
        System.out.println(Arrays.deepToString(merge5));

    }

    public static int[][] merge(int[][] intervals) {
        int len = intervals.length;
        /*Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });*/
        Arrays.sort(intervals, (o1, o2) -> o1[0] - o2[0]);
        int[][] resultArray = new int[len][2];
        int resultIndex = 0;
        int left;
        int right;
        int prev = 0;
        for (int i = 0; i < len; i++) {
            int[] interval = intervals[i];
            left = interval[0];
            right = interval[1];
            /*if (i > 0 && left <= prev) {
                *//*if (right > prev) {
                    int curIndex = Math.max((resultIndex - 1), 0);
                    resultArray[curIndex][1] = right;
                }*//*
                int curIndex = Math.max((resultIndex - 1), 0);
                resultArray[curIndex][1] = Math.max(prev, right);
            } else if (left > prev || left == 0) {
                resultArray[resultIndex] = interval;
                resultIndex++;
            }*/
            if (i == 0 || left > prev) {
                resultArray[resultIndex] = interval;
                resultIndex++;
            } else {
                int curIndex = Math.max((resultIndex - 1), 0);
                resultArray[curIndex][1] = Math.max(prev, right);
            }
            prev = Math.max(prev, right);
        }
        return Arrays.copyOfRange(resultArray, 0, resultIndex);
    }


}
