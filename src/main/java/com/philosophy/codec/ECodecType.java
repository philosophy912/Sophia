package com.philosophy.codec;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 23:50
 **/
public enum ECodecType {
    MD5("MD5"),
    SHA("SHA"),
    AES("AES"),
    DES3("DESede"),
    DES("DES"),
    PUBLIC("public"),
    PRIVATE("private");

    private ECodecType(String value){
        this.value = value;
    }
    private String value;
    /**
     * 枚举获取枚举数据的值
     *
     * @return 返回枚举数据的值
     */
    public String getValue() {
        return value;
    }
    /**
     * 根据值返回字符类型
     * @param value 枚举值
     * @return 返回字符的枚举值
     */
    public static ECodecType fromValue(String value){
        for(ECodecType item: values()){
            if(item.value.equalsIgnoreCase(value)){
                return item;
            }
        }
        throw new RuntimeException("not support character type[" + value + "]");
    }
}
