package com.booknote;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookManager {
    private List<Book> books = new ArrayList<>();
    private final Scanner sc = new Scanner(System.in);
    private final String FILE = "books.data";

    public BookManager() {
        File f = new File(FILE);
        try { f.createNewFile(); } catch (Exception e) {}
    }

    public void addBook() {
        System.out.print("编号："); String id = sc.nextLine();
        System.out.print("书名："); String name = sc.nextLine();
        System.out.print("作者："); String author = sc.nextLine();
        System.out.print("分类(文学/科普/历史/教辅)："); String c = sc.nextLine();
        System.out.print("总页数："); int page = sc.nextInt(); sc.nextLine();
        books.add(new Book(id, name, author, c, page));
        System.out.println("✅ 添加成功！");
    }

    public void showAllBooks() {
        if (books.isEmpty()) { System.out.println("无书籍"); return; }
        for (Book b : books) {
            System.out.printf("【%s】《%s》%s | %s | 进度：%d/%d | 评分：%.1f\n",
                    b.getId(), b.getName(), b.getAuthor(), b.getCategory(),
                    b.getReadPage(), b.getTotalPage(), b.getScore());
        }
    }

    public Book find(String id) {
        for (Book b : books) if (b.getId().equals(id)) return b;
        return null;
    }

    public void updateBook() {
        System.out.print("输入编号：");
        Book b = find(sc.nextLine());
        if (b == null) { System.out.println("不存在"); return; }
        System.out.print("新书名："); b.setName(sc.nextLine());
        System.out.print("新作者："); b.setAuthor(sc.nextLine());
        System.out.print("新分类："); b.setCategory(sc.nextLine());
        System.out.println("✅ 修改成功！");
    }

    public void deleteBook() {
        System.out.print("编号：");
        books.removeIf(b -> b.getId().equals(sc.nextLine()));
        System.out.println("✅ 删除成功！");
    }

    public void searchBook() {
        System.out.print("关键词："); String key = sc.nextLine();
        for (Book b : books) {
            if (b.getName().contains(key) || b.getAuthor().contains(key)) {
                System.out.println(b.getName() + " | " + b.getAuthor());
            }
        }
    }

    public void showByCategory() {
        System.out.print("分类："); String c = sc.nextLine();
        for (Book b : books) {
            if (b.getCategory().equals(c)) System.out.println(b.getName());
        }
    }

    public void addNote() {
        System.out.print("书籍编号："); Book b = find(sc.nextLine());
        if (b == null) return;
        System.out.print("笔记内容："); String s = sc.nextLine();
        b.addNote(s);
        System.out.println("✅ 笔记已保存");
    }

    public void showNotes() {
        System.out.print("编号："); Book b = find(sc.nextLine());
        if (b == null) return;
        for (Note n : b.getNotes()) {
            System.out.println(n.getId() + "｜" + n.getTime() + "｜" + n.getContent());
        }
    }

    public void updateNote() {
        System.out.print("编号："); Book b = find(sc.nextLine());
        if (b == null) return;
        System.out.print("笔记ID："); int id = sc.nextInt(); sc.nextLine();
        for (Note n : b.getNotes()) {
            if (n.getId() == id) {
                System.out.print("新内容："); n.setContent(sc.nextLine());
                System.out.println("✅ 已修改"); return;
            }
        }
    }

    public void deleteNote() {
        System.out.print("编号："); Book b = find(sc.nextLine());
        if (b == null) return;
        System.out.print("笔记ID："); int id = sc.nextInt(); sc.nextLine();
        b.getNotes().removeIf(n -> n.getId() == id);
        System.out.println("✅ 已删除");
    }

    public void setProgress() {
        System.out.print("编号："); Book b = find(sc.nextLine());
        if (b == null) return;
        System.out.print("已读页数："); int p = sc.nextInt(); sc.nextLine();
        b.setReadPage(p);
        System.out.println("✅ 进度已更新");
    }

    public void showStats() {
        int total = books.size();
        long read = books.stream().filter(b -> b.getStatus().equals("已读")).count();
        long ing = books.stream().filter(b -> b.getStatus().equals("在读")).count();
        int notes = books.stream().mapToInt(b -> b.getNotes().size()).sum();
        System.out.println("📊 统计");
        System.out.println("总书籍："+total);
        System.out.println("已读："+read+" 在读："+ing);
        System.out.println("总笔记："+notes);
    }

    public void plan() {
        System.out.println("📅 今日阅读计划：完成 20 页");
    }

    public void exportNotes() {
        try (PrintWriter pw = new PrintWriter("笔记导出.txt")) {
            for (Book b : books) {
                pw.println("《"+b.getName()+"》");
                for (Note n : b.getNotes()) pw.println(n.getContent());
                pw.println("----------------");
            }
            System.out.println("✅ 已导出到 笔记导出.txt");
        } catch (Exception e) {}
    }

    public void backup() {
        saveData();
        System.out.println("✅ 数据已备份");
    }

    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {
            oos.writeObject(books);
        } catch (Exception e) {}
    }

    public void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE))) {
            books = (List<Book>) ois.readObject();
        } catch (Exception e) {}
    }
}