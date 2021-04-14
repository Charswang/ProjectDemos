package producer.bean;

import common.bean.Contact;
import common.bean.DataIn;
import common.bean.DataOut;
import common.bean.Producer;

import java.io.IOException;
import java.util.List;

/**
 * 本地文件生产者
 */
public class LocalFileProducer implements Producer {
    private DataIn in;
    private DataOut out;
    private volatile boolean flag = true; // 其他线程更改flag的话，这里能够看到更改操作，可以得到更改后的flag值
    public void producer() {
        try {
            List<Contact> contacts = in.read(Contact.class);
            for (Contact contact : contacts) {
                System.out.println(contact.toString());
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
