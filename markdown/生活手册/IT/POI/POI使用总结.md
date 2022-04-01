# 一、 POI简介

Apache POI是Apache软件基金会的开放源码函式库，POI提供API给Java程序对Microsoft Office格式档案读和写的功能。

官网：https://poi.apache.org/components/spreadsheet/

版本对比

|               | POI版本         | Excel版本  | 拓展名 | 行数限制                                              |
| ------------- | --------------- | ---------- | ------ | ----------------------------------------------------- |
| HSSFWorkbook  |                 | 2003       | .xls   | 至多为65535行，超出会报错                             |
| XSSFWorkbook  | POI 3.8版本开始 | 2007及以后 | .xlsx  | 1048576行，16384列，容易OOM内存溢出                   |
| SXSSFWorkbook | POI 3.8版本开始 | 2007及以后 | .xlsx  | 无限制，硬盘空间换内存（就像HashMap用空间换时间一样） |

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



# 二、 POI EXCEL文档结构类

            XSSFWorkbook excel文档对象
    
            XSSFSheet excel的sheet XSSFRow excel的行
    
            XSSFCell excel的单元格 XSSFFont excel字体
    
            XSSFName 名称 XSSFDataFormat 日期格式
    
            XSSFHeader sheet头
    
            XSSFFooter sheet尾
    
            XSSFCellStyle cell样式
    
            XSSFDateUtil 日期
    
            XSSFPrintSetup 打印
    
            XSSFErrorConstants 错误信息表
# 三、 EXCEL常用操作方法

##   1、 得到Excel常用对象           

```java
POIFSFileSystem fs= newPOIFSFileSystem(new FileInputStream("d:/test.xlsx"));
// 得到Excel工作簿对象
XSSFWorkbook wb = new XSSFWorkbook(fs);
// 得到Excel工作表对象
XSSFSheet sheet = wb.getSheetAt(0);
// 得到Excel工作表的行
XSSFRow row = sheet.getRow(i);
// 得到Excel工作表指定行的单元格
XSSFCell cell = row.getCell((short) j);
// 得到单元格样式  
cellStyle = cell.getCellStyle();
```



##  2、建立Excel常用对象

```java
// 创建Excel工作簿对象
XSSFWorkbook wb = new XSSFWorkbook();
// 创建Excel工作表对象
XSSFSheet sheet = wb.createSheet("new sheet");
// 创建Excel工作表的行
XSSFRow row = sheet.createRow((short)0);
// 创建单元格样式
cellStyle = wb.createCellStyle();
// 创建Excel工作表指定行的单元格
row.createCell((short)0).setCellStyle(cellStyle);
// 设置Excel工作表的值
row.createCell((short)0).setCellValue(1);
```



## 3、设置sheet名称和单元格内容

```java
wb.setSheetName(1, "第一张工作表",XSSFCell.ENCODING_UTF_16);
cell.setEncoding((short) 1);
cell.setCellValue("单元格内容");
```



## 4、取得sheet的数目 

```java
wb.getNumberOfSheets();
```



## 5、  根据index取得sheet对象

```java
XSSFSheet sheet = wb.getSheetAt(0);
```



## 6、取得有效的行数

```java
int firstRowNum = sheet.getFirstRowNum();
int rowCount = sheet.getLastRowNum();
```



## 7、取得一行的有效单元格个数

```java
row.getLastCellNum();
```



## 8、单元格值类型读写

```java
// 设置单元格为STRING类型
cell.setCellType(XSSFCell.CELL_TYPE_STRING);

// 读取
// 获取单元格类型
int cellType = cell.getCellType();
switch (cellType) {
        // 数字
        case Cell.CELL_TYPE_NUMERIC:
            // 数值格式
            short dataFormat = cell.getCellStyle().getDataFormat();
            if (XSSFDateUtil.isCellDateFormatted(cell)) {
                // 处理日期格式、时间格式
                SimpleDateFormat sdf = null;
                // 验证short值
                if (dataFormat == 14) {
                    sdf = new SimpleDateFormat("yyyy/MM/dd");
                } else if (dataFormat == 21) {
                    sdf = new SimpleDateFormat("HH:mm:ss");
                } else if (dataFormat == 22) {
                    sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                } else {
                    throw new RuntimeException("日期格式错误!!!");
                }
                Date date = cell.getDateCellValue();
                cellValue = sdf.format(date);
            } else if (dataFormat == 0) {
                // 处理数值格式
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cellValue = String.valueOf(cell.getRichStringCellValue().getString());
            }
            break;
        // 字符串
        case Cell.CELL_TYPE_STRING:
            cellValue = String.valueOf(cell.getStringCellValue());
            break;
        // Boolean
        case Cell.CELL_TYPE_BOOLEAN:
            cellValue = String.valueOf(cell.getBooleanCellValue());
            break;
        // 公式
        case Cell.CELL_TYPE_FORMULA:
            cellValue = String.valueOf(cell.getCellFormula());
            break;
        // 空值
        case Cell.CELL_TYPE_BLANK:
            cellValue = null;
            break;
        // 故障 
        case Cell.CELL_TYPE_ERROR:
            cellValue = "非法字符";
            break;
        default:
            cellValue = "未知类型";
            break;
    }
// 读取为数值类型的单元格内容 
cell.getNumericCellValue(); 
```

 

## 9、设置列宽、行高

```java
sheet.setColumnWidth((short)column,(short)width);
row.setHeight((short)height);
```



## 10、添加区域，合并单元格

```java
// 合并从第rowFrom行columnFrom列
Region region = new Region((short)rowFrom,(short)columnFrom,(short)rowTo,(short)columnTo);
// 到rowTo行columnTo的区域
sheet.addMergedRegion(region);
// 得到所有区域
sheet.getNumMergedRegions();
```

 

## 11、保存Excel文件

```java
FileOutputStream fileOut = new FileOutputStream(path);
wb.write(fileOut);
```

 

## 12、根据单元格不同属性返回字符串数值

```java
public String getCellStringValue(XSSFCell cell) {
        String cellValue = "";
        // 获取单元格类型
        int cellType = cell.getCellType();
        switch (cellType) {
            // 数字
            case Cell.CELL_TYPE_NUMERIC:
                // 数值格式
                short dataFormat = cell.getCellStyle().getDataFormat();
                if (XSSFDateUtil.isCellDateFormatted(cell)) {
                    // 处理日期格式、时间格式
                    SimpleDateFormat sdf = null;
                    // 验证short值
                    if (dataFormat == 14) {
                        sdf = new SimpleDateFormat("yyyy/MM/dd");
                    } else if (dataFormat == 21) {
                        sdf = new SimpleDateFormat("HH:mm:ss");
                    } else if (dataFormat == 22) {
                        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    } else {
                        throw new RuntimeException("日期格式错误!!!");
                    }
                    Date date = cell.getDateCellValue();
                    cellValue = sdf.format(date);
                } else if (dataFormat == 0) {
                    // 处理数值格式
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cellValue = String.valueOf(cell.getRichStringCellValue().getString());
                }
                break;
            // 字符串
            case Cell.CELL_TYPE_STRING:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            // Boolean
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            // 公式
            case Cell.CELL_TYPE_FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            // 空值
            case Cell.CELL_TYPE_BLANK:
                cellValue = null;
                break;
            // 故障 
            case Cell.CELL_TYPE_ERROR:
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;      
}


```

 

## 13、常用单元格边框格式

```java
XSSFCellStyle style = wb.createCellStyle();
// 下边框
style.setBorderBottom(XSSFCellStyle.BORDER_DOTTED);
// 左边框
style.setBorderLeft(XSSFCellStyle.BORDER_DOTTED);
// 右边框
style.setBorderRight(XSSFCellStyle.BORDER_THIN);
// 上边框
style.setBorderTop(XSSFCellStyle.BORDER_THIN);
```

 

## 14、设置字体和内容位置

```java
XSSFFont f  = wb.createFont();
// 字号
f.setFontHeightInPoints((short) 11);
// 加粗
f.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
style.setFont(f);
// 左右居中
style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
// 上下居中
style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
// 单元格内容的旋转的角度
style.setRotation(short rotation);
XSSFDataFormat df = wb.createDataFormat();
// 设置单元格数据格式
style1.setDataFormat(df.getFormat("0.00%"));
// 给单元格设公式
cell.setCellFormula(string);
// 单元格内容的旋转的角度
style.setRotation(short rotation);
```

 

## 15、插入图片

```java
// 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
BufferedImage bufferImg = ImageIO.read(new File("ok.jpg"));
ImageIO.write(bufferImg,"jpg",byteArrayOut);
// 读进一个excel模版
FileInputStream fos = new FileInputStream(filePathName+"/stencil.xlt");
fs = new POIFSFileSystem(fos);
// 创建一个工作薄
XSSFWorkbook wb = new XSSFWorkbook(fs);      
XSSFSheet sheet = wb.getSheetAt(0);
XSSFPatriarch patriarch = sheet.createDrawingPatriarch();
XSSFClientAnchor anchor = new XSSFClientAnchor(0,0,1023,255,(short) 0,0,(short)10,10);
patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),XSSFWorkbook.PICTURE_TYPE_JPEG));
```

 

## 16、调整工作表位置

```java
XSSFWorkbook wb = new XSSFWorkbook();
XSSFSheet sheet = wb.createSheet("format sheet");
XSSFPrintSetup ps = sheet.getPrintSetup();
sheet.setAutobreaks(true);
ps.setFitHeight((short)1);
ps.setFitWidth((short)1);
```

  



## 17、设置打印区域

```java
XSSFSheet sheet = wb.createSheet("Sheet1");
wb.setPrintArea(0, "$A$1:$C$2");
```

 

## 18、标注脚注

```java
XSSFSheet sheet = wb.createSheet("format sheet");
XSSFFooter footer = sheet.getFooter();
footer.setRight( "Page " + XSSFFooter.page() + " of " + XSSFFooter.numPages() );
```

 

## 19、在工作单中清空行数据，调整行位置

```java
XSSFWorkbook wb = new XSSFWorkbook();
XSSFSheet sheet = wb.createSheet("row sheet");
// Create various cells and rows for spreadsheet.
// Shift rows 6 - 11 on the spreadsheet to the top (rows 0 - 5)
sheet.shiftRows(5, 10, -5);
```

 

## 20、选中指定的工作表

```java
XSSFSheet sheet = wb.createSheet("row sheet");
heet.setSelected(true);
```

 

## 21、工作表的放大缩小


```java
XSSFSheet sheet1 = wb.createSheet("new sheet");
// 50 percent magnification
sheet1.setZoom(1,2);
```

 

## 22、头注和脚注

```java
XSSFSheet sheet = wb.createSheet("new sheet");
XSSFHeader header = sheet.getHeader();
header.setCenter("Center Header");
header.setLeft("Left Header");
header.setRight(XSSFHeader.font("Stencil-Normal", "Italic") +
XSSFHeader.fontSize((short) 16) + "Right w/ Stencil-Normal Italic font and size 16");
```

 

## 23、自定义颜色

```java
XSSFCellStyle style = wb.createCellStyle();
style.setFillForegroundColor(XSSFColor.LIME.index);
style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
XSSFFont font = wb.createFont();
font.setColor(XSSFColor.RED.index);
style.setFont(font);
cell.setCellStyle(style);
```



## 24、填充和颜色设置

```java
XSSFCellStyle style = wb.createCellStyle();
style.setFillBackgroundColor(XSSFColor.AQUA.index);
style.setFillPattern(XSSFCellStyle.BIG_SPOTS);
XSSFCell cell = row.createCell((short) 1);
cell.setCellValue("X");
style = wb.createCellStyle();
style.setFillForegroundColor(XSSFColor.ORANGE.index);
style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
cell.setCellStyle(style);

// 复制格式
XSSFCellStyle cellStyle2 = workBook.createCellStyle();
cellStyle2.cloneStyleFrom(style);
```



## 25、强行刷新单元格公式

```java
XSSFFormulaEvaluator eval=new XSSFFormulaEvaluator((XSSFWorkbook) wb);
private static void updateFormula(Workbook wb,Sheet s,int row){
        Row r=s.getRow(row);
        Cell c=null;
        FormulaEcaluator eval=null;
        if(wb instanceof XSSFWorkbook)
            eval=new XSSFFormulaEvaluator((XSSFWorkbook) wb);
        else if(wb instanceof XSSFWorkbook)
            eval=new XSSFFormulaEvaluator((XSSFWorkbook) wb);
        for(int i=r.getFirstCellNum();i<r.getLastCellNum();i++){
            c=r.getCell(i);
            if(c.getCellType()==Cell.CELL_TYPE_FORMULA)
                eval.evaluateFormulaCell(c);
        }
    }
```

说明：FormulaEvaluator提供了evaluateFormulaCell(Cell cell)方法，计算公式保存结果，但不改变公式。而evaluateInCell(Cell cell) 方法是计算公式，并将原公式替换为计算结果，也就是说该单元格的类型不在是Cell.CELL_TYPE_FORMULA而是Cell.CELL_TYPE_NUMBERIC。XSSFFormulaEvaluator提供了静态方法evaluateAllFormulaCells(XSSFWorkbook wb) ，计算一个Excel文件的所有公式，用起来很方便。
