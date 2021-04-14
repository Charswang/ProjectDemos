package common.bean;

import java.io.Closeable;  // 继承这个类表示生产者对象可关闭

/**
 * 生产者接口
 */
public interface Producer extends Closeable {
    /**
     * 生产数据
     */
    public void producer();

    public void setIn(DataIn in);
    public void setOut(DataOut out);
}
