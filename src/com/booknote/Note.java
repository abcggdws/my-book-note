package com.booknote;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Note implements Serializable {
    private int id;
    private String content;
    private String time;

    public Note(int id, String content) {
        this.id = id;
        this.content = content;
        this.time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
    }

    public int getId() { return id; }
    public String getContent() { return content; }
    public String getTime() { return time; }
    public void setContent(String content) { this.content = content; }
}