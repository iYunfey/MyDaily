/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName:    CompareDemo
 * Author:      mundo
 * Date:        2020-03-17 17:44
 * Description: 比较器
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 〈一句话功能简述〉<br>
 * 〈比较器〉
 *
 * @author mundo
 * @create 2020-03-17
 * @since 1.0.0
 */
public class CompareDemo {

    static class Student {
        String name;
        Integer age;
        String address;

        public Student() {
        }

        public Student(String name, Integer age, String address) {
            this.name = name;
            this.age = age;
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Student student = (Student) o;
            return Objects.equals(name, student.name) &&
                    Objects.equals(age, student.age) &&
                    Objects.equals(address, student.address);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age, address);
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", address='" + address + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        List list = new ArrayList();
        Collections.addAll(list, 1, 2, 3, 4, 5, 9, 8, 7);
        System.out.println(list);
        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 > o2 ? -1 : 1;
            }
        });
        System.out.println(list);

        List students = new ArrayList<Student>();
        Student student1 = new Student("小王", 12, "北京");
        Student student2 = new Student("小王", 12, "上海");
        Student student3 = new Student("小王", 13, "上海");
        Student student4 = new Student("小李", 12, "上海");
        Student student5 = new Student("小赵", 12, "广州");
        Collections.addAll(students, student1, student2, student3, student5, student4);
        System.out.println(students);
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.name.compareTo(o2.name) == 0 ? (o1.age.compareTo(o2.age) == 0 ? o1.address.compareTo(o2.address) : 1) : -1;
            }
        });
        System.out.println(students);
    }
}
