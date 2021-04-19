package consumer.bean;

import common.bean.Consumer;
import common.constant.Names;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class CalllogConsumer implements Consumer {
    public void consume() {
        try{
            Properties properties = new Properties();
            // 获取当前线程下的上下文类加载器，然后将resources文件读取成流
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("consumer.properties")); // 加载配置文件来进行配置
            // 创建消费者
            KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
            // 订阅主题
            kafkaConsumer.subscribe(Arrays.asList(Names.TOPIC.getValue()));

            // hbase数据访问对象
            HBasedao hbasedao = new HBasedao();
            hbasedao.init(); // hbase初始化

            while(true){
                ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(100);// 每隔100ms拉取一次数据
                for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                    // System.out.println(consumerRecord.key() + "---" + consumerRecord.value());
                    System.out.println(consumerRecord.value());
                    hbasedao.insertDatas(); // 将数据插入hbase
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void close() throws IOException {

    }
}
