/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName:    OuterClass
 * Author:      mundo
 * Date:        2020-04-29 17:30
 * Description: 外部类内部类执行顺序
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

/**
 * 〈一句话功能简述〉<br>
 * 〈外部类内部类执行顺序〉
 *
 * @author mundo
 * @create 2020-04-29
 * @since 1.0.0
 */
public class OuterClass {

    private InnerClass innerClass = new InnerClass(this);

    private  class InnerClass {
        public InnerClass(OuterClass outerClass){
            outerClass.print();
            System.out.println("InnerClass is initializing");
        }
    }

    public void print(){
        System.out.println("OutClass is invoking");
    }

    public OuterClass(){
        System.out.println("OuterClass is initializing");
    }

    public static void main(String[] args) {
        OuterClass outerClass = new OuterClass();

    }
}
