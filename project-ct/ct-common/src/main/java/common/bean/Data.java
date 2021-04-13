package common.bean;

public class Data implements Val {
    private String content;

    public void setValue(String value){
        content = value;
    }

    public Object value() {
        return content;
    }
}
