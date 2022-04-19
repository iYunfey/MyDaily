/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName:    FinallyReturnDemo
 * Author:      mundo
 * Date:        2020-05-08 21:43
 * Description: finally和return执行
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

/**
 * 〈一句话功能简述〉<br>
 * 〈finally和return执行〉
 *
 * @author mundo
 * @create 2020-05-08
 * @since 1.0.0
 */
public class FinallyReturnDemo {

    public static void main(String[] args) {
        FinallyReturnDemo demo = new FinallyReturnDemo();
        int integer = demo.getInteger();
        System.out.println(integer);
    }

    public int getInteger() {
        try {
            int a = 1;
            System.out.println(a);
            return 0;
        } catch (Exception e) {

        } finally {
            System.out.println("finally");
            return 2;
        }
       // return 0;
    }
}
