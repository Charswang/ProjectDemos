package consumer;

import consumer.bean.CalllogConsumer;

import java.io.IOException;

public class BootStrap {
    public static void main(String[] args) throws IOException {
        // 创建消费者
        CalllogConsumer consumer = new CalllogConsumer();
        // 消费数据
        consumer.consume();
        // 关闭资源
        consumer.close();
    }
}
