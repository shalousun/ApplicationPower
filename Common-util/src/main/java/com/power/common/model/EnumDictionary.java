package com.power.common.model;

/**
 *
 * @author yu 2019/12/7.
 */
public class EnumDictionary {

    /**
     * dict value
     */
    private String value;

    /**
     * code type
     */
    private String type;
    /**
     * dict desc
     */
    private String desc;

    /**
     * enum ordinal
     */
    private int ordinal;

    /**
     * enum name
     */
    private String name;


    public String getValue() {
        return value;
    }

    public EnumDictionary setValue(String value) {
        this.value = value;
        return this;
    }

    public String getType() {
        return type;
    }

    public EnumDictionary setType(String type) {
        this.type = type;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public EnumDictionary setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public EnumDictionary setOrdinal(int ordinal) {
        this.ordinal = ordinal;
        return this;
    }

    public String getName() {
        return name;
    }

    public EnumDictionary setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "EnumDictionary{" +
                "value='" + value + '\'' +
                ", type='" + type + '\'' +
                ", desc='" + desc + '\'' +
                ", ordinal=" + ordinal +
                ", name='" + name + '\'' +
                '}';
    }
}
