package common.constant;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 通过加载配置文件来配置常量
 */
public class ConfigConstant {
    private static Map<String,String> map = new HashMap<String, String>();
    // 静态代码块，程序启动后自动加载
    static {
        ResourceBundle bundle = ResourceBundle.getBundle("constantTest");
        Enumeration<String> keys = bundle.getKeys();
        while(keys.hasMoreElements()){
            String key = keys.nextElement();
            String value = bundle.getString(key);
            map.put(key,value);
        }
    }
    public static String getValue(String key){
        return map.get(key);
    }
    public static void main(String[] args) {
        System.out.println(getValue("ct.namespace"));
    }
}
