import java.util.StringJoiner;

public class StringJoinerTest {
    public static void main(String[] args) {
        String[] names = {"A", "B", "C", "D"};
        // 只设置分隔符
        StringJoiner joiner = new StringJoiner(",");
        for (String name : names) {
            joiner.add(name);
        }
        System.out.println(joiner);

        // 设置分隔符和开头结尾
        StringJoiner joiner2 = new StringJoiner(",", "[", "]");
        for (String name : names) {
            joiner2.add(name);
        }
        System.out.println(joiner2);

        // 同样的还有StingUtils.join();
    }
}
