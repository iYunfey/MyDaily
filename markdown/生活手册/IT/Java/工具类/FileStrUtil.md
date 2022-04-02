# FileStrUtil

```
import sun.misc.BASE64Decoder;

import java.io.*;

public class FileStrUtil {
    /**
     * summary:将字符串存储为文件 采用Base64解码
     *
     * @param is
     * @param outFileStr
     *
     */
    public static void streamSaveAsFile(InputStream is, String outFileStr) {
        FileOutputStream fos = null;
        try {
            File file = new File(outFileStr);
            BASE64Decoder decoder = new BASE64Decoder();
            fos = new FileOutputStream(file);
            byte[] buffer = decoder.decodeBuffer(is);
            fos.write(buffer, 0, buffer.length);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
                fos.close();
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new RuntimeException(e2);
            }
        }
    }

    /**
     *
     *
     * summary:将字符串存储为文件
     *
     * @param fileStr
     * @param outFilePath
     *
     */
    public static void stringSaveAsFile(String fileStr, String outFilePath) {
        InputStream out = new ByteArrayInputStream(fileStr.getBytes());
        FileStrUtil.streamSaveAsFile(out, outFilePath);
    }

    /**
     * 将流转换成字符串 使用Base64加密
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String streamToString(InputStream inputStream) throws IOException {
        byte[] bt = toByteArray(inputStream);
        inputStream.close();
        String out = new sun.misc.BASE64Encoder().encodeBuffer(bt);
        return out;
    }

    /**
     * 将流转换成字符串
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String fileToString(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream is = new FileInputStream(file);
        String fileStr = FileStrUtil.streamToString(is);
        return fileStr;
    }

    /**
     *
     * summary:将流转化为字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     *
     */
    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        byte[] result = null;
        try {
            int n = 0;
            while ((n = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
            result = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            out.close();
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        String fromPath = "F://fileupload//aaa.docx";
        String toPath = "C://Users//Desktop//aaaa.docx";
        String fileStr = FileStrUtil.fileToString(fromPath);
        FileStrUtil.stringSaveAsFile(fileStr, toPath);
    }
}
```

