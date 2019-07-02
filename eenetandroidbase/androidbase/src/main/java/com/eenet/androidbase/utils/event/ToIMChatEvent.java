package com.eenet.androidbase.utils.event;

/**
 * Created by yao23 on 16/11/16.
 */

public class ToIMChatEvent {

    private String id;
    private String name;

    public ToIMChatEvent(String id, String name){
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
