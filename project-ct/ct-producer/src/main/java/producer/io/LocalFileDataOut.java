package producer.io;

import common.bean.DataOut;

import java.io.IOException;

/**
 * 本地文件输出
 */
public class LocalFileDataOut implements DataOut {
    public LocalFileDataOut(String path){
        setPath(path);
    }
    public void setPath(String path) {

    }

    public void close() throws IOException {

    }
}
