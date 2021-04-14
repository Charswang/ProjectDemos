package common.bean;

import java.io.Closeable;
import java.util.List;

/**
 * 数据来源
 */
public interface DataIn extends Closeable {
    /**
     * 设置数据来源路径
     * @param path
     */
    public void setPath(String path);
    public Object read() throws Exception;
    public <T extends Data> List<T> read(Class<T> clazz) throws Exception;
}
