package producer;

import common.bean.Producer;
import producer.bean.LocalFileProducer;
import producer.io.LocalFileDataIn;
import producer.io.LocalFileDataOut;

import java.io.IOException;

/**
 * 启动对象
 */
public class BootStrap {
    public static void main(String[] args) throws IOException {
        // 构建生产者对象
        Producer producer = new LocalFileProducer();
        // producer.setIn(new LocalFileDataIn("D:\\WangHao\\ProjectDemos\\project-ct\\ct-producer\\src\\main\\resources\\contact.log"));  // 绝对路径
        producer.setIn(new LocalFileDataIn("ct-producer\\src\\main\\resources\\contact.log")); // 相对路径
        producer.setOut(new LocalFileDataOut(""));
        // 生产数据
        producer.producer();
        // 关闭生产者对象
        producer.close();
    }
}
