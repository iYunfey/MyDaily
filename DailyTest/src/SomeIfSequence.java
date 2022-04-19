/**
 * Copyright (C), 2020-2020, XXX有限公司
 * FileName: SomeIfSequence
 * Author: mundo
 * Date: 2020/7/25 12:09 上午
 * Description: 多个同级if执行顺序
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

/**
 * 〈⼀句话功能简述〉<br>
 * 〈多个同级if执行顺序〉
 *
 * @author mundo
 * @create 2020/7/25
 * @since 1.0.0
 */
public class SomeIfSequence {

    public static void main(String[] args) {
        int[] arr1 = {2, 4, 6, 8};
        int[] arr2 = {3, 9, 15, 21};
        int[] arr3 = {5, 25, 35, 55};

        if (arr1.length > 0) {
            for (int i = 0; i < arr1.length; i++) {
                if (arr1[i] % 2 == 0) {
                    System.out.println("arr1[" + i + "] = " + arr1[i]);
                }
            }
        }
        if (arr2.length > 0) {
            for (int j = 0; j < arr2.length; j++) {
                if (arr2[j] % 3 == 0) {
                    System.out.println("arr2[" + j + "] = " + arr2[j]);
                }
            }
        }
        if (arr3.length > 0) {
            for (int k = 0; k < arr3.length; k++) {
                if (arr3[k] % 5 == 0) {
                    System.out.println("arr3[" + k + "] = " + arr3[k]);
                }
            }
        }
    }
}