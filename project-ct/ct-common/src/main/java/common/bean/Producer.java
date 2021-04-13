package common.bean;

/**
 * 生产者接口
 */
public interface Producer {
    /**
     * 生产数据
     */
    public void producer();

    public void setIn(DataIn in);
    public void setOut(DataOut out);
}
