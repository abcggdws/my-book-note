package com.booknote;

import java.util.Date;

public class Note {
    private int noteId;
    private String content;    // 笔记内容
    private Date createTime;   // 记录时间

    public Note(int noteId, String content) {
        this.noteId = noteId;
        this.content = content;
        this.createTime = new Date();
    }

    public int getNoteId() { return noteId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Date getCreateTime() { return createTime; }

    @Override
    public String toString() {
        return "笔记" + noteId + " | " + createTime + "\n内容：" + content;
    }
}