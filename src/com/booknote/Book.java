package com.booknote;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private String bookId;
    private String name;     // 书名
    private String author;   // 作者
    private String status;   // 未读 / 在读 / 已读
    private List<Note> noteList = new ArrayList<>();
    private int noteCount = 1;

    public Book(String bookId, String name, String author) {
        this.bookId = bookId;
        this.name = name;
        this.author = author;
        this.status = "未读";
    }

    // 添加笔记
    public void addNote(String content) {
        noteList.add(new Note(noteCount++, content));
    }

    // 删除笔记
    public boolean deleteNote(int noteId) {
        return noteList.removeIf(n -> n.getNoteId() == noteId);
    }

    // 修改笔记
    public boolean updateNote(int noteId, String newContent) {
        for (Note n : noteList) {
            if (n.getNoteId() == noteId) {
                n.setContent(newContent);
                return true;
            }
        }
        return false;
    }

    // 查看所有笔记
    public List<Note> getNoteList() {
        return noteList;
    }

    // getter setter
    public String getBookId() { return bookId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "【" + bookId + "】《" + name + "》 作者：" + author + " 状态：" + status + " | 笔记数：" + noteList.size();
    }
}