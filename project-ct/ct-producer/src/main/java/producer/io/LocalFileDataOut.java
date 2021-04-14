package producer.io;

import common.bean.DataOut;

import java.io.*;

/**
 * 本地文件输出
 */
public class LocalFileDataOut implements DataOut {
    private PrintWriter writer = null;
    public LocalFileDataOut(String path){
        setPath(path);
    }
    public void setPath(String path) {
        try {
            writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(path),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void write(Object object) throws Exception {
        write(object.toString());
    }

    public void write(String data) throws Exception {
        writer.println(data);
        writer.flush(); // 每写一条就flush一条
    }

    public void close() throws IOException {
        if (writer != null){
            writer.close();
        }
    }
}
