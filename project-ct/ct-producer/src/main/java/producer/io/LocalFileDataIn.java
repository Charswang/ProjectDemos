package producer.io;

import common.bean.Data;
import common.bean.DataIn;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 本地文件数据输入
 */
public class LocalFileDataIn implements DataIn {
    private BufferedReader br = null;

    public LocalFileDataIn(String path) throws FileNotFoundException {
        setPath(path);
    }
    public void setPath(String path) {
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path),"UTF-8"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Object read() throws Exception {
        return null;
    }

    // 泛型更具有通用性
    public <T extends Data> List<T> read(Class<T> clazz) throws Exception {
        List<T> ts = new ArrayList<T>();
        try {
            String line = null;
            while ((line=br.readLine())!=null){
                T t = clazz.newInstance(); // 反射
                t.setValue(line);
                ts.add(t);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return ts;
        // 泛型的使用，T;
    }

    public void close() throws IOException {
        if (br==null){
            br.close();
        }
    }
}
