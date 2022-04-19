/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName:    TwoArraySameEDemo
 * Author:      mundo
 * Date:        2020-05-06 16:41
 * Description: 有长数组A与短数组B，都存储的是正整数，不使用额外的数据结构，查询两个数组中重复的元素并列出。列出你认为时间与空间复杂度最低的最优解
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

import java.util.jar.JarEntry;

/**
 * 〈一句话功能简述〉<br>
 * 〈有长数组A与短数组B，都存储的是正整数，不使用额外的数据结构，查询两个数组中重复的元素并列出。列出你认为时间与空间复杂度最低的最优解〉
 *
 * @author mundo
 * @create 2020-05-06
 * @since 1.0.0
 */
public class TwoArraySameEDemo {

    public static void main(String[] args) {
        int[] arr1 = {1,2,3,4};
        int[] arr2 = {1,2,3,4,5};
        TwoArraySameEDemo demo = new TwoArraySameEDemo();
        int[] ints = demo.listSameElement(arr1, arr2);
        for (int i : ints) {
            System.out.println(i);
        }
    }

    
    public int[] listSameElement(int[] arr1, int[] arr2) {
        int length = arr1.length <= arr2.length ? arr1.length : arr2.length;
        int[] arr = new int[length];
        int num = 0;
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr2.length; j++) {
                if (arr1[i] == arr2[j]) {
                    arr[num] = arr1[i];
                    num++;
                    break;
                }
            }
        }
        return arr;
    }


}
