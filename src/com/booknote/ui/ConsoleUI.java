package com.booknote.ui;

import com.booknote.model.Book;
import com.booknote.model.Note;
import com.booknote.service.BookService;
import com.booknote.service.NoteService;
import com.booknote.service.StatisticsService;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final Scanner sc = new Scanner(System.in);
    private final BookService bookService;
    private final NoteService noteService;
    private final StatisticsService statisticsService;

    public ConsoleUI(BookService bookService, NoteService noteService, StatisticsService statisticsService) {
        this.bookService = bookService;
        this.noteService = noteService;
        this.statisticsService = statisticsService;
    }

    public void start() {
        while (true) {
            showMainMenu();
            int op = readInt();
            switch (op) {
                case 1 -> addBook();
                case 2 -> listBooks();
                case 3 -> updateBook();
                case 4 -> deleteBook();
                case 5 -> searchBook();
                case 6 -> listByCategory();
                case 7 -> addNote();
                case 8 -> showNotes();
                case 9 -> updateNote();
                case 10 -> deleteNote();
                case 11 -> setProgress();
                case 12 -> showStats();
                case 13 -> showPlan();
                case 0 -> {
                    saveAndExit();
                    return;
                }
                default -> System.out.println("无效选项");
            }
        }
    }

    private void showMainMenu() {
        System.out.println("""
                
                ========================================
                      个人读书管理系统 V3.0
                ========================================
                 1. 添加书籍        2. 查看所有书籍
                 3. 修改书籍        4. 删除书籍
                 5. 搜索书籍/笔记   6. 分类查看
                 7. 添加笔记        8. 查看笔记
                 9. 修改笔记        10. 删除笔记
                 11. 阅读进度        12. 阅读统计
                 13. 阅读计划        0. 退出
                ========================================
                请输入选择：""");
    }

    private void addBook() {
        System.out.print("编号: "); String id = sc.nextLine();
        System.out.print("书名: "); String name = sc.nextLine();
        System.out.print("作者: "); String author = sc.nextLine();
        System.out.print("分类: "); String cat = sc.nextLine();
        System.out.print("总页数: "); int pages = Integer.parseInt(sc.nextLine());
        try {
            bookService.addBook(id, name, author, cat, pages);
            System.out.println("✅ 添加成功");
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void listBooks() {
        List<Book> books = bookService.listAll();
        if (books.isEmpty()) System.out.println("暂无书籍");
        else books.forEach(this::printBookInfo);
    }

    private void printBookInfo(Book b) {
        System.out.printf("【%s】《%s》 %s | %s | 进度: %d/%d | 评分: %.1f (%d次)\n",
                b.getId(), b.getName(), b.getAuthor(), b.getCategory(),
                b.getReadPages(), b.getTotalPages(), b.getAverageScore(), b.getScoreCount());
    }

    private void updateBook() {
        System.out.print("编号: "); String id = sc.nextLine();
        System.out.print("新书名(回车跳过): "); String name = sc.nextLine();
        System.out.print("新作者(回车跳过): "); String author = sc.nextLine();
        System.out.print("新分类(回车跳过): "); String cat = sc.nextLine();
        try {
            bookService.updateBook(id, name, author, cat);
            System.out.println("✅ 修改成功");
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void deleteBook() {
        System.out.print("编号: "); String id = sc.nextLine();
        if (bookService.deleteBook(id)) System.out.println("✅ 已删除");
        else System.out.println("❌ 未找到");
    }

    private void searchBook() {
        System.out.print("关键词: "); String kw = sc.nextLine();
        List<Book> results = bookService.search(kw);
        if (results.isEmpty()) System.out.println("无结果");
        else results.forEach(this::printBookInfo);
    }

    private void listByCategory() {
        System.out.print("分类: "); String cat = sc.nextLine();
        List<Book> books = bookService.listByCategory(cat);
        if (books.isEmpty()) System.out.println("无结果");
        else books.forEach(this::printBookInfo);
    }

    private void addNote() {
        System.out.print("书籍编号: "); String id = sc.nextLine();
        System.out.print("笔记内容: "); String content = sc.nextLine();
        try {
            noteService.addNote(id, content);
            System.out.println("✅ 笔记已添加");
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void showNotes() {
        System.out.print("书籍编号: "); String id = sc.nextLine();
        Book b = bookService.findBookById(id);
        if (b == null) { System.out.println("❌ 书籍不存在"); return; }
        List<Note> notes = b.getNotes();
        if (notes.isEmpty()) System.out.println("暂无笔记");
        else notes.forEach(n -> System.out.println(n.getId() + " | " + n.getFormattedTime() + " | " + n.getContent()));
    }

    private void updateNote() {
        System.out.print("书籍编号: "); String id = sc.nextLine();
        System.out.print("笔记ID: "); int nid = Integer.parseInt(sc.nextLine());
        System.out.print("新内容: "); String content = sc.nextLine();
        try {
            noteService.updateNote(id, nid, content);
            System.out.println("✅ 已更新");
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void deleteNote() {
        System.out.print("书籍编号: "); String id = sc.nextLine();
        System.out.print("笔记ID: "); int nid = Integer.parseInt(sc.nextLine());
        try {
            noteService.deleteNote(id, nid);
            System.out.println("✅ 已删除");
        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void setProgress() {
        System.out.print("书籍编号: "); String id = sc.nextLine();
        Book b = bookService.findBookById(id);
        if (b == null) { System.out.println("❌ 书籍不存在"); return; }
        System.out.print("已读页数: "); int pages = Integer.parseInt(sc.nextLine());
        b.updateReadPages(pages);
        System.out.println("✅ 进度已更新");
    }

    private void showStats() {
        System.out.println(statisticsService.getOverallStats());
        System.out.print("查看本周报告？(y/n) ");
        if (sc.nextLine().equalsIgnoreCase("y")) {
            statisticsService.getWeeklyReport().forEach(System.out::println);
        }
    }

    private void showPlan() {
        System.out.println("📅 阅读计划（基于当前进度估算）");
        List<Book> books = bookService.listAll();
        for (Book b : books) {
            if (b.getReadPages() < b.getTotalPages()) {
                int remaining = b.getTotalPages() - b.getReadPages();
                int dailyGoal = 20; // 默认每日目标
                int days = (int) Math.ceil(remaining / (double) dailyGoal);
                System.out.printf("《%s》剩余 %d 页，目标每天 %d 页，预计 %d 天完成\n",
                        b.getName(), remaining, dailyGoal, days);
            }
        }
    }

    private void saveAndExit() {
        try {
            bookService.save();
            System.out.println("✅ 数据已保存，下次见！");
        } catch (Exception e) {
            System.out.println("❌ 保存失败: " + e.getMessage());
        }
    }

    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("请输入数字: ");
            }
        }
    }
}