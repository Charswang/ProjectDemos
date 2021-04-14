package producer.bean;

import common.bean.*;
import common.util.DateUtil;
import common.util.NumberUtil;
import jdk.nashorn.internal.codegen.CompilerConstants;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 本地文件生产者
 */
public class LocalFileProducer implements Producer {
    private DataIn in;
    private DataOut out;
    private volatile boolean flag = true; // 其他线程更改flag的话，这里能够看到更改操作，可以得到更改后的flag值
    public void producer() {
        try {
            // 获取通讯录
            List<Contact> contacts = in.read(Contact.class);
            /*for (Contact contact : contacts) {
                System.out.println(contact.toString());
            }*/

            // 随机获取两个电话号码
            while (flag){
                int call1index = new Random().nextInt(contacts.size()); // 返回的是int，并且可能会发现规律，而Math.random()返回的是double。
                int call2index;
                while (true){
                    call2index = new Random().nextInt(contacts.size());
                    if (call1index!=call2index){
                        break;
                    }
                }
                Contact call1 = contacts.get(call1index);
                Contact call2 = contacts.get(call2index);

                // 生成随机的通话时间
                String startDate = "20200101000000";
                String endDate = "20210101000000";

                long startTime = DateUtil.parse(startDate,"yyyyMMddHHmmss").getTime();
                long endTime = DateUtil.parse(endDate,"yyyyMMddHHmmss").getTime();
                long callTime = startTime + (long)((endTime - startTime) * Math.random());
                String callTimeString = DateUtil.format(new Date(callTime),"yyyyMMddHHmmss");

                // 随机生成通话时长
                String duration = NumberUtil.format(new Random().nextInt(3000),4);

                // 创建Calllog对象--生成通话记录
                Calllog calllog = new Calllog(call1.getTel(),call2.getTel(),callTimeString,duration);

                // 将通话记录写入数据文件中
                out.write(calllog); // 注意这里调动的是writer(Object obkect)这个方法，里面使用的是onject.toString()方法，所以为了让写入数据中的文件更规范，所以要重写Calllog中的toString()方法
                //System.out.println(calllog); // 控制台查看
                Thread.sleep(500); // 要进行睡眠才能符合情况，不然一下子都弄完了
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setIn(DataIn in) {
        this.in = in;
    }

    public void setOut(DataOut out) {
        this.out = out;
    }

    public void close() throws IOException {
        if (in == null){
            in.close();
        }
        if (out == null){
            out.close();
        }
    }
}
