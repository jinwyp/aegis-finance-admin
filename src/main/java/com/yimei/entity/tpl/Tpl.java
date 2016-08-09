package com.yimei.entity.tpl;

/**
 * Created by liuxinjie on 16/7/30.
 */
public class Tpl {
    private int id;
    private String name;

    public Tpl() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tpl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
