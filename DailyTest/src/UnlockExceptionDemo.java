/**
 * Copyright (C), 2020-2020, XXX有限公司
 * FileName: UnlockExceptionDemo
 * Author: mundo
 * Date: 2020/11/30 3:24 下午
 * Description: 未持有锁资源但执行unlock()方法会抛出什么异常
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

import java.util.concurrent.Callable;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 〈⼀句话功能简述〉<br>
 * 〈未持有锁资源的线程强制执行unlock()方法会抛出什么异常〉
 *
 * @author mundo
 * @create 2020/11/30
 * @since 1.0.0
 */
public class UnlockExceptionDemo{

    public static void main(String[] args) {
        ReentrantLock reentrantLock =new ReentrantLock();
        // java.lang.IllegalMonitorStateException
        reentrantLock.unlock();
    }
}