package com.booknote;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Book implements Serializable {
    private String id;
    private String name;
    private String author;
    private String category;
    private String status;
    private int totalPage;
    private int readPage;
    private double score;
    private List<Note> notes = new ArrayList<>();
    private int noteId = 1;

    public Book(String id, String name, String author, String category, int totalPage) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.category = category;
        this.totalPage = totalPage;
        this.status = "未读";
        this.readPage = 0;
    }

    public void addNote(String content) {
        notes.add(new Note(noteId++, content));
    }

    public List<Note> getNotes() { return notes; }
    public String getId() { return id; }
    public String getName() { return name; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public String getStatus() { return status; }
    public int getTotalPage() { return totalPage; }
    public int getReadPage() { return readPage; }
    public double getScore() { return score; }

    public void setReadPage(int readPage) {
        this.readPage = readPage;
        if (readPage >= totalPage) status = "已读";
        else if (readPage > 0) status = "在读";
    }

    public void setScore(double score) { this.score = score; }
    public void setName(String name) { this.name = name; }
    public void setAuthor(String author) { this.author = author; }
    public void setCategory(String category) { this.category = category; }
}