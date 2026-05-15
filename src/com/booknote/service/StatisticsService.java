package com.booknote.service;

import com.booknote.model.Book;
import com.booknote.model.ReadingRecord;

import java.util.*;

public class StatisticsService {
    private BookService bookService;

    public StatisticsService(BookService bookService) {
        this.bookService = bookService;
    }

    public String getOverallStats() {
        List<Book> books = bookService.listAll();
        long read = books.stream().filter(b -> b.getStatus().equals("已读")).count();
        long reading = books.stream().filter(b -> b.getStatus().equals("在读")).count();
        int totalNotes = books.stream().mapToInt(b -> b.getNotes().size()).sum();

        StringBuilder sb = new StringBuilder();
        sb.append("📊 统计信息\n");
        sb.append("总书籍数: ").append(books.size()).append("\n");
        sb.append("已读: ").append(read).append("  在读: ").append(reading).append("\n");
        sb.append("笔记总数: ").append(totalNotes).append("\n");

        // 分类统计
        Map<String, Integer> catCount = new HashMap<>();
        for (Book b : books) catCount.merge(b.getCategory(), 1, Integer::sum);
        sb.append("分类统计: ").append(catCount).append("\n");
        return sb.toString();
    }

    public List<String> getWeeklyReport() {
        // 简单返回最近7天的阅读记录摘要
        List<Book> books = bookService.listAll();
        List<String> report = new ArrayList<>();
        long now = System.currentTimeMillis();
        long weekAgo = now - 7 * 24 * 3600 * 1000L;

        for (Book b : books) {
            int weeklyPages = b.getReadingHistory().stream()
                    .filter(r -> r.getTimestamp() > weekAgo)
                    .mapToInt(ReadingRecord::getPagesRead)
                    .sum();
            if (weeklyPages > 0) {
                report.add(String.format("《%s》：本周阅读 %d 页", b.getName(), weeklyPages));
            }
        }
        return report;
    }
}