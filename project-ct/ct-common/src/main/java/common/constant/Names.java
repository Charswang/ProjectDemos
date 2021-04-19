package common.constant;

import common.bean.Val;

/**
 * 名称常量枚举类
 */
public enum Names implements Val {
    NAMESPACE("ct"),
    TOPIC("ct"),
    TABLE("ct:calllog"),
    CF_CALLER("caller"),
    CF_INFO("info");

    private String name;

    private Names(String name){
        this.name = name;
    }

    public void setValue(Object value) {
        this.name = (String) value;
    }

    public String getValue() {
        return name;
    }
}
