package com.haozileung.infra.office;

/**
 * row中的一列数据
 *
 * Created by yamcha on 7/28/14.
 */
public class ExcelCell {

    /** 列值 */
    private Object value;

    /**
     * 获取value值的字符串形式
     * 
     * @return
     */
    public String getStringValue() {
        if (this.value != null) {
            return value.toString();
        }
        return "";
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        if (this.value != null) {
            return value.toString();
        }
        return "null";
    }
}