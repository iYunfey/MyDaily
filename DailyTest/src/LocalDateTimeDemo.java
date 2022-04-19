/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName:    LocalDateTimeDemo
 * Author:      mundo
 * Date:        2020-04-26 17:01
 * Description: JDK1.8中LocalDateTime全面取代Date
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

import com.sun.media.jfxmedia.logging.Logger;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 〈一句话功能简述〉<br> 
 * 〈JDK1.8中LocalDateTime全面取代Date〉
 * @author mundo
 * @create 2020-04-26 
 * @since 1.0.0
 */
public class LocalDateTimeDemo {

    public static void main(String[] args) {
        LocalDateTime rightNow = LocalDateTime.now();
        System.out.println(rightNow);
        System.out.println(rightNow.getYear());
        System.out.println(rightNow.getDayOfMonth());
        System.out.println(rightNow.getDayOfYear());
        System.out.println(rightNow.getDayOfWeek());

//        Date rightNow = new Date();
//        System.out.println("当前时刻: "+rightNow);// 当前时刻：Mon Apr 27 21:55:10 CST 2020
//        // 这打印结果你第一眼能看明白？可读性忒差了
//        System.out.println("当前年份: "+rightNow.getYear());// 当前年份：120
//        // 今天是2020年，你给我返回个120，没法读
//        System.out.println("当前月份: "+rightNow.getMonth());// 当前月份：3
//        // 现在是4月份，你给我返个3，没法读
    }
}
