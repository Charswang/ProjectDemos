package common.bean;

import java.io.Closeable;

public interface DataOut extends Closeable {
    /**
     * 设置数据输出路径
     * @param path
     */
    public void setPath(String path);
    public void write(Object object) throws Exception;
    public void write(String data) throws Exception;
}
