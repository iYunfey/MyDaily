# 1、使用格式化Json输出日志

## 1.1、使用阿里的FastJson

### 项目的pom.xml依赖

```xml
<dependency>
  <groupId>com.alibaba</groupId>
  <artifactId>fastjson</artifactId>
  <version>1.2.58</version>
</dependency>
```

### Java示例代码

```java
com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class Test{

	public static void main(String[] args) {

    	String jsonString = "{\"_index\":\"book_shop\",\"_type\":\"it_book\",\"_id\":\"1\",\"_score\":1.0," +
            "\"_source\":{\"name\": \"Java编程思想（第4版）\",\"author\": \"[美] Bruce Eckel\",\"category\": \"编程语言\"," +
            "\"price\": 109.0,\"publisher\": \"机械工业出版社\",\"date\": \"2007-06-01\",\"tags\": [ \"Java\", \"编程语言\" ]}}";

    	JSONObject object = JSONObject.parseObject(jsonString);
   		String pretty = JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);

        // pretty = JSON.toJSONString(JSONObject.parseObject(jsonString), SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
    	System.out.println(pretty);
	}
}
```

格式化输出后的结果:

说明: FastJson通过Tab键进行换行后的格式化.

```json
{
	"_index":"book_shop",
	"_type":"it_book",
	"_source":{
		"date":"2007-06-01",
		"author":"[美] Bruce Eckel",
		"price":109.0,
		"name":"Java编程思想（第4版）",
		"publisher":"机械工业出版社",
		"category":"编程语言",
		"tags":[
			"Java",
			"编程语言"
		]
	},
	"_id":"1",
	"_score":1.0
}
```

## 1.2、使用谷歌的Gson

### 项目的pom.xml依赖

```xml
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.2.4</version>
</dependency>
```

### Java示例代码

```java
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Test{

	public static void main(String[] args) {
    
        String jsonString = "{\"_index\":\"book_shop\",\"_type\":\"it_book\",\"_id\":\"1\",\"_score\":1.0," +
                "\"_source\":{\"name\": \"Java编程思想（第4版）\",\"author\": \"[美] Bruce Eckel\",\"category\": \"编程语言\"," +
                "\"price\": 109.0,\"publisher\": \"机械工业出版社\",\"date\": \"2007-06-01\",\"tags\": [ \"Java\", \"编程语言\" ]}}";

        String pretty = toPrettyFormat(jsonString)

        System.out.println(pretty);
	}

    /**
     * 格式化输出JSON字符串
     * @return 格式化后的JSON字符串
     */
    private static String toPrettyFormat(String json) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }
}
```

格式化输出后的结果：

说明: Gson使用2个空格作为换行后的格式转换.

```json
{
  "_index": "book_shop",
  "_type": "it_book",
  "_id": "1",
  "_score": 1.0,
  "_source": {
    "name": "Java编程思想（第4版）",
    "author": "[美] Bruce Eckel",
    "category": "编程语言",
    "price": 109.0,
    "publisher": "机械工业出版社",
    "date": "2007-06-01",
    "tags": [
      "Java",
      "编程语言"
    ]
  }
}
```

