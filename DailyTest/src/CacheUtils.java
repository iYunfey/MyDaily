import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheUtils {
    public static void main(String[] args) throws InterruptedException {
        CacheUtils.set("name","zhangsan",2000);
        CacheUtils.set("name","lisi",2000);
        CacheUtils.set("name","ww",500);
        CacheUtils.set("name1","qweqwe",4000);
        Thread.sleep(1000);
        String name = CacheUtils.get("name");
        String name1 = CacheUtils.get("name1");
        System.out.println(name);
        System.out.println(name1);
        Thread.sleep(1000);
        System.out.println(CacheUtil.get("name"));
        System.out.println(CacheUtil.get("name1"));
        Thread.sleep(1000);
        System.out.println(CacheUtil.get("name"));
        System.out.println(CacheUtil.get("name1"));
        Thread.sleep(1000);
        System.out.println(CacheUtil.get("name"));
        System.out.println(CacheUtil.get("name1"));
        Thread.sleep(1000);
        System.out.println(CacheUtil.get("name"));
        System.out.println(CacheUtil.get("name1"));

    }

    private CacheUtils(){}

    private static final Map<String, CacheUtilBean> CACHE_MAP = new HashMap<String, CacheUtilBean>();

    public static void set(String key,String value,long exprTime){
        //将传入的毫秒数 转换为 将来的时间戳
        CACHE_MAP.put(key,new CacheUtilBean(value,System.currentTimeMillis()+exprTime));
    }

    private static final long DEFAULT_EXPR_TIME = 24*60*60*1000L;
    public static void set(String key,String value){
        set(key,value, DEFAULT_EXPR_TIME);
    }

    public static String get(String key){
        //获取之前先删除时间点之前的
        removeExp();
        CacheUtilBean cacheUtilBean = CACHE_MAP.get(key);
        if(cacheUtilBean==null){
            return "";
        }
        return cacheUtilBean.getValue();
    }

    private static void removeExp() {
        List<String> removeKey = new ArrayList<>();
        for (Map.Entry<String, CacheUtilBean> entry : CACHE_MAP.entrySet()) {
            Long exprTime = entry.getValue().getExprTime();
            if(System.currentTimeMillis()>exprTime){
                removeKey.add(entry.getKey());
            }
        }

        for (String s : removeKey) {
            CACHE_MAP.remove(s);
        }
    }
    static class CacheUtilBean {
        //存的值
        private String value;
        //过期时间戳 set的时候计算好
        private Long exprTime;

        public CacheUtilBean(String value, Long exprTime) {
            this.value = value;
            this.exprTime = exprTime;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Long getExprTime() {
            return exprTime;
        }

        public void setExprTime(Long exprTime) {
            this.exprTime = exprTime;
        }
    }
}
