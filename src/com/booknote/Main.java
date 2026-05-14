package com.booknote;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookManager manager = new BookManager();
        manager.loadData();

        while (true) {
            System.out.println("\n=====================================================");
            System.out.println("               🔥 个人读书管理系统 V2.0 高级版 🔥");
            System.out.println("=====================================================");
            System.out.println(" 1. 添加书籍        2. 查看所有书籍        3. 修改书籍");
            System.out.println(" 4. 删除书籍        5. 搜索书籍            6. 分类查看");
            System.out.println(" 7. 添加笔记        8. 查看笔记            9. 修改笔记");
            System.out.println("10. 删除笔记       11. 阅读进度设置       12. 阅读统计");
            System.out.println("13. 阅读计划       14. 笔记导出           15. 数据备份");
            System.out.println(" 0. 退出系统");
            System.out.println("=====================================================");
            System.out.print("请输入操作：");

            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1: manager.addBook(); break;
                case 2: manager.showAllBooks(); break;
                case 3: manager.updateBook(); break;
                case 4: manager.deleteBook(); break;
                case 5: manager.searchBook(); break;
                case 6: manager.showByCategory(); break;
                case 7: manager.addNote(); break;
                case 8: manager.showNotes(); break;
                case 9: manager.updateNote(); break;
                case 10: manager.deleteNote(); break;
                case 11: manager.setProgress(); break;
                case 12: manager.showStats(); break;
                case 13: manager.plan(); break;
                case 14: manager.exportNotes(); break;
                case 15: manager.backup(); break;
                case 0:
                    manager.saveData();
                    System.out.println("✅ 数据已保存，欢迎下次使用！");
                    return;
                default: System.out.println("输入错误！");
            }
        }
    }
}