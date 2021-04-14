package common.bean;

public abstract class Data implements Val {
    private String content;

    public void setValue(Object value) {
        content = (String) value;
    }

    public String getValue() {
        return content;
    }
}
