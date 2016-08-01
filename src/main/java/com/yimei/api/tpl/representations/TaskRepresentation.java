package com.yimei.api.tpl.representations;

import java.io.Serializable;

/**
 * Created by liuxinjie on 16/8/1.
 */
public class TaskRepresentation implements Serializable {
    private String id;
    private String name;

    public TaskRepresentation(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
