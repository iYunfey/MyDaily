import java.util.HashSet;
import java.util.Set;

public class SetTest {

    public static void main(String[] args) {
        Set<String> set = new HashSet<>(16);
        boolean aaa = set.add("aaa");
        boolean aaa1 = set.add("aaa");
        boolean bbb = set.add("bbb");
        System.out.println(aaa);
        System.out.println(aaa1);
        System.out.println(bbb);
    }
}
