package com.eeduspace.challenge.persist.po;
/**
 * Author: dingran
 * Date: 2016/7/13
 * 系统字典项
 **/
public class SystemDictionary {
    //主键
    private Long id;
    //字典项名称 唯一标识
    private String name;
    //字典项值
    private String value;
    //描述
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}