# 一、Excel

https://poi.apache.org/components/spreadsheet/

版本对比

|               | POI版本         | Excel版本  | 拓展名 | 行数限制                                               |
| ------------- | --------------- | ---------- | ------ | ------------------------------------------------------ |
| HSSFWorkbook  |                 | 2003       | .xls   | 至多为65535行，超出会报错                              |
| XSSFWorkbook  | POI 3.8版本开始 | 2007及以后 | .xlsx  | 1048576行，16384列，容易OOM内存溢出                    |
| SXSSFWorkbook | POI 3.8版本开始 | 2007及以后 | .xlsx  | 无限制，硬盘空间换内存（就像hash map用空间换时间一样） |

对于不同版本的EXCEL文档要使用不同的工具类，如果使用错了，会提示如下错误信息。

```java
org.apache.poi.openxml4j.exceptions.InvalidOperationException
org.apache.poi.poifs.filesystem.OfficeXmlFileException
```

引入相关依赖

```xml
 <dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>4.1.2</version>
 </dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>4.1.2</version>
</dependency>
```

## 创建Excel

### 基本概念

　　一个**工作簿**可以有多个**工作表**，一个工作表可以有**多个行**，一个行可以有多个**单元格**

#### 工作簿XSSFWorkbook

创建一个工作簿

```java
XSSFWorkbook book = new XSSFWorkbook();
```



#### 工作表XSSFSheet

创建一个工作表

```java
Sheet sheet = book.createSheet("测试工作表");
```



#### 行XSSFRow

```java
// 行数与数据量有关
for (int i = 0; i < columnSize + 12; i++) {
	XSSFRow row = sheet1.createRow(i);
	for (int j = 0; j < 11; j++) {
		XSSFCell cell = row.createCell(j);
		cell.setCellStyle(cellStyle2);
		sheet1.autoSizeColumn(j);
	}
}
```



#### 单元格XSSFCell

```java
String[] string = new String[]{"省市", "县区", "日目标", "当日业绩", "日达成率", "当月目标", "当月达成", "月达成率", "排名", "进度缺口", "剩余日均目标"};
XSSFRow row3 = sheet1.getRow(2);
for (int i = 0; i < string.length; i++) {
	XSSFCell cell3 = row3.getCell(i);
	cell3.setCellValue(string[i]);
	cell3.setCellStyle(cellStyle2);
}
```



#### 设置单元格样式

```java

XSSFCellStyle cellStyle1 = workBook.createCellStyle();
XSSFFont font1 = workBook.createFont();
this.setCellFont(font1, "微软雅黑", new Color(240, 245, 0), true, 25);
this.setCellStyle(cellStyle1, font1);


// 内容一 白色 不加粗 居中
XSSFCellStyle cellStyle2 = workBook.createCellStyle();
XSSFFont font32= workBook.createFont();
this.setCellFont(font2, "微软雅黑", new Color(245, 245, 245), false, 10);
// 复制格式
cellStyle2.cloneStyleFrom(cellStyle1);
        
        
public XSSFFont setCellFont(XSSFFont font, String name, Color fontcolor, boolean bold, double fontSize) {
        font.setFontName(name);
        font.setBold(bold);
        font.setColor(new XSSFColor(fontcolor));
        font.setFontHeight(fontSize);
        return font;
}
```

#### 设置列宽

```java
sheet1.setColumnWidth(1, 14 * 256);
sheet1.setColumnWidth(2, 12 * 256);
sheet1.setColumnWidth(3, 12 * 256);
sheet1.setColumnWidth(10, 12 * 256);
```

#### 合并单元格

```java
CellRangeAddress region1 = new CellRangeAddress(0, 0, 0, 10);
sheet1.addMergedRegion(region1);
```

