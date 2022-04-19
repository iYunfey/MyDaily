/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName:    DiGuiDemo
 * Author:      mundo
 * Date:        2020-05-07 20:20
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

/**
 * 〈递归求和：1+2-3+4-5...〉<br>
 * 〈〉
 *
 * @author mundo
 * @create 2020-05-07
 * @since 1.0.0
 */
public class DiGuiDemo {

    public static void main(String[] args) {
        DiGuiDemo diGui = new DiGuiDemo();
        int sum = diGui.getSum(5);
        System.out.println(sum);
    }

    public int getSum(int var) {
        int sum = 0;
        if (var <= 0) {
            return sum;
        }
        if (var > 1) {
            if (var % 2 == 0) {
                sum += var;
            } else {
                sum -= var;
            }
        } else {
            sum = 1;
        }
        return sum += getSum(var - 1);
    }
}
