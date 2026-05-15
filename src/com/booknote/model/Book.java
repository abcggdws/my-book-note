package com.booknote.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Book implements Serializable {
    private String id;
    private String name;
    private String author;
    private String category;
    private String status;
    private int totalPages;
    private int readPages;
    private double totalScore;
    private int scoreCount;
    private List<Note> notes;
    private List<ReadingRecord> readingHistory;

    public Book(String id, String name, String author, String category, int totalPages) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.category = category;
        this.totalPages = totalPages;
        this.status = "未读";
        this.readPages = 0;
        this.notes = new ArrayList<>();
        this.readingHistory = new ArrayList<>();
    }

    public void addNote(Note note) { notes.add(note); }
    public boolean removeNote(int noteId) { return notes.removeIf(n -> n.getId() == noteId); }
    public void addReadingRecord(ReadingRecord record) { readingHistory.add(record); }

    public void updateReadPages(int pages) {
        int added = pages - this.readPages;
        if (added > 0) {
            readingHistory.add(new ReadingRecord(added, System.currentTimeMillis()));
        }
        this.readPages = Math.min(pages, totalPages);
        if (this.readPages >= totalPages) this.status = "已读";
        else if (this.readPages > 0) this.status = "在读";
    }

    public void addScore(double score) {
        this.totalScore += score;
        this.scoreCount++;
    }

    public double getAverageScore() { return scoreCount == 0 ? 0 : totalScore / scoreCount; }

    // Getters and setters...
    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }
    public int getReadPages() { return readPages; }
    public void setReadPages(int readPages) { this.readPages = readPages; }
    public int getScoreCount() { return scoreCount; }
    public List<Note> getNotes() { return notes; }
    public List<ReadingRecord> getReadingHistory() { return readingHistory; }
}