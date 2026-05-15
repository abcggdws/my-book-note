package com.booknote.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Note implements Serializable {
    private int id;
    private String content;
    private long timestamp;

    public Note(int id, String content) {
        this.id = id;
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }

    public int getId() { return id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public long getTimestamp() { return timestamp; }
    public String getFormattedTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(timestamp));
    }
}