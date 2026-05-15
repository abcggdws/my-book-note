package com.booknote;

import com.booknote.service.BookService;
import com.booknote.service.NoteService;
import com.booknote.service.StatisticsService;
import com.booknote.ui.ConsoleUI;
import com.booknote.util.ReminderThread;

public class Main {
    public static void main(String[] args) {
        BookService bookService = new BookService();
        NoteService noteService = new NoteService(bookService);
        StatisticsService statisticsService = new StatisticsService(bookService);

        // 启动后台提醒线程
        ReminderThread.startReminder();

        ConsoleUI ui = new ConsoleUI(bookService, noteService, statisticsService);
        ui.start();
    }
}