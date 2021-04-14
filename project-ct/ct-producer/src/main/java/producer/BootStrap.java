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
        if (args.length < 2){
            System.out.println("no argument -- java -jar producer.jar path1 path2");
            System.exit(1);
        }
        // 构建生产者对象
        Producer producer = new LocalFileProducer();
        // producer.setIn(new LocalFileDataIn("D:\\WangHao\\ProjectDemos\\project-ct\\ct-producer\\src\\main\\resources\\contact.log"));  // 绝对路径
        /*producer.setIn(new LocalFileDataIn("ct-producer\\src\\main\\resources\\contact.log")); // 相对路径
        producer.setOut(new LocalFileDataOut("ct-producer\\src\\main\\resources\\calllog.log"));*/

        producer.setIn(new LocalFileDataIn(args[0]));
        producer.setOut(new LocalFileDataOut(args[1]));

        // 生产数据
        producer.producer();
        // 关闭生产者对象
        producer.close();
    }
}
/*
# Name the components on this agent
a1.sources = r1
a1.sinks = k1
a1.channels = c1

# Describe/configure the source
a1.sources.r1.type = exec
a1.sources.r1.command = tail -F -c +0 /opt/module/data/calllog.log
a1.sources.r1.shell = /bin/bash -c

# Describe the sink
a1.sinks.k1.type = org.apache.flume.sink.kafka.KafkaSink
a1.sinks.k1.kafka.topic = ct
a1.sinks.k1.kafka.bootstrap.servers = master:9092,slave1:9092,slave2:9092
a1.sinks.k1.kafka.flumeBatchSize = 20
a1.sinks.k1.kafka.producer.acks = 1
a1.sinks.k1.kafka.producer.linger.ms = 1

# Use a channel which buffers events in memory
a1.channels.c1.type = memory
a1.channels.c1.capacity = 1000
a1.channels.c1.transactionCapacity = 100

# Bind the source and sink to the channel
a1.sources.r1.channels = c1
a1.sinks.k1.channel = c1
* */