package common.constant;

import common.bean.Val;

/**
 * 名称常量枚举类
 */
public enum Names implements Val {
    NAMESPACE("ct");

    private String name;

    private Names(String name){
        this.name = name;
    }

    public String value() {
        return null;
    }
}
