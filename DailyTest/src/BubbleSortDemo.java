/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName:    BubbleSortDemo
 * Author:      mundo
 * Date:        2020-04-29 19:52
 * Description: 冒泡排序
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

import java.util.Collections;

/**
 * 〈一句话功能简述〉<br>
 * 〈冒泡排序〉
 *
 * @author mundo
 * @create 2020-04-29
 * @since 1.0.0
 */
public class BubbleSortDemo {

    public static void main(String[] args) {
        int[] array = {1, 3, 2, 5, 6, 4};
        BubbleSortDemo bubbleSortDemo = new BubbleSortDemo();
        System.out.print("排序前： ");
        bubbleSortDemo.showArray(array);
        bubbleSortDemo.sortDesc(array);
        System.out.println();
        System.out.print("降序后： ");
        bubbleSortDemo.showArray(array);
       // bubbleSortDemo.sort(array);
       // System.out.println();
       // System.out.print("升序后： ");
       // bubbleSortDemo.showArray(array);
        System.out.println();
        bubbleSortDemo.sortPro(array);
        System.out.print("优化升序后： ");
        bubbleSortDemo.showArray(array);
    }

    public void sort(int[] array) {
        // 外循环控制循环次数
        for (int i = 0; i < array.length - 1; i++) {
            // 内循环后移最大数
            for (int j = 0; j < array.length - i - 1; j++) {
                int temp;
                if (array[j] > array[j + 1]) {
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    public void sortPro(int[] array) {
        // 装载临时变量
        int temp;
        // 是否发生置换，1 发生 ，0 未发生
        int isChanged;
        // 外循环控制循环次数
        for (int i = 0; i < array.length - 1; i++) {
            // 每次比较前初始化
            isChanged = 0;
            // 内循环后移最大数
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    isChanged = 1;
                }
            }
            // 若未发生置换，则已排好序，无须继续循环
            if (isChanged == 0) {
                break;
            }
        }
    }

    public void sortDesc(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 1; j < array.length - i; j++) {
                int temp;
                if (array[j] > array[j - 1]) {
                    temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                }
            }
        }
    }

    public void showArray(int[] array) {
        for (int i : array) {
            System.out.print(i);
        }
        System.out.print(" ");
    }
}
