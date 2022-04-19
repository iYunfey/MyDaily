/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName:    ArrayTest
 * Author:      mundo
 * Date:        2020-07-01 17:02
 * Description: 声明长度为0的数组会自动扩容吗
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

import java.util.Date;
import java.util.LinkedList;

/**
 * 〈一句话功能简述〉<br>
 * 〈声明长度为0的数组会自动扩容吗〉
 *
 * @author mundo
 * @create 2020-07-01
 * @since 1.0.0
 */
public class ArrayTest {
    public static void main(String[] args) {
        // int[] ints = new int[0];
        // System.out.println(ints.length);
        // ints[0] = 0;
        // System.out.println(ints.length);
        // ints[1] = 1;
        // System.out.println(ints.length);
        int[] arr = new int[3];
        int i = 0;
        arr[i] = 0;
        System.out.println(arr[1]);
        arr[i++] = 1;
        System.out.println("-------------");
        System.out.println(arr[1]);
    }
}
