package com.booknote;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookManager manager = new BookManager();

        while (true) {
            System.out.println("\n===== 我的课外书&读书笔记系统（自用） =====");
            System.out.println("1. 添加课外书");
            System.out.println("2. 查看所有书籍");
            System.out.println("3. 修改书籍信息/阅读状态");
            System.out.println("4. 删除书籍");
            System.out.println("5. 给书籍写笔记");
            System.out.println("6. 查看某本书所有笔记");
            System.out.println("7. 修改/删除笔记");
            System.out.println("0. 退出");
            System.out.print("请选择：");
            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1:
                    System.out.print("书籍编号：");
                    String bid = sc.nextLine();
                    System.out.print("书名：");
                    String bname = sc.nextLine();
                    System.out.print("作者：");
                    String bauthor = sc.nextLine();
                    manager.addBook(bid, bname, bauthor);
                    System.out.println("添加成功");
                    break;
                case 2:
                    List<Book> books = manager.listAll();
                    if (books.isEmpty()) System.out.println("暂无书籍");
                    else books.forEach(System.out::println);
                    break;
                case 3:
                    System.out.print("要修改的书籍编号：");
                    String mid = sc.nextLine();
                    manager.findBookById(mid).ifPresentOrElse(b -> {
                        System.out.print("新书名：");
                        String nn = sc.nextLine();
                        System.out.print("新作者：");
                        String na = sc.nextLine();
                        System.out.print("状态(未读/在读/已读)：");
                        String s = sc.nextLine();
                        manager.updateBook(mid, nn, na, s);
                        System.out.println("修改成功");
                    }, () -> System.out.println("未找到该书"));
                    break;
                case 4:
                    System.out.print("删除书籍编号：");
                    String did = sc.nextLine();
                    if (manager.deleteBook(did)) System.out.println("已删除");
                    else System.out.println("不存在");
                    break;
                case 5:
                    System.out.print("书籍编号：");
                    String nbid = sc.nextLine();
                    manager.findBookById(nbid).ifPresentOrElse(b -> {
                        System.out.print("输入笔记内容：");
                        String content = sc.nextLine();
                        b.addNote(content);
                        System.out.println("笔记已保存");
                    }, () -> System.out.println("书籍不存在"));
                    break;
                case 6:
                    System.out.print("书籍编号：");
                    String vid = sc.nextLine();
                    manager.findBookById(vid).ifPresentOrElse(b -> {
                        List<Note> ns = b.getNoteList();
                        if (ns.isEmpty()) System.out.println("暂无笔记");
                        else ns.forEach(System.out::println);
                    }, () -> System.out.println("书籍不存在"));
                    break;
                case 7:
                    System.out.print("书籍编号：");
                    String eid = sc.nextLine();
                    manager.findBookById(eid).ifPresentOrElse(b -> {
                        System.out.print("笔记ID：");
                        int nid = sc.nextInt();
                        sc.nextLine();
                        System.out.println("1 修改  2 删除");
                        int o = sc.nextInt();
                        sc.nextLine();
                        if (o == 1) {
                            System.out.print("新内容：");
                            String c = sc.nextLine();
                            if (b.updateNote(nid, c)) System.out.println("修改成功");
                            else System.out.println("笔记不存在");
                        } else {
                            if (b.deleteNote(nid)) System.out.println("删除成功");
                            else System.out.println("笔记不存在");
                        }
                    }, () -> System.out.println("书籍不存在"));
                    break;
                case 0:
                    System.out.println("退出系统");
                    sc.close();
                    return;
                default:
                    System.out.println("输入错误");
            }
        }
    }
}