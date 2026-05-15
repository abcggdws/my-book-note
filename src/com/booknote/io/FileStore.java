package com.booknote.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.booknote.model.Book;

public class FileStore {
    private static final String MAIN_FILE = "books.data";
    private static final String BACKUP_DIR = "backup";

    public static void save(List<Book> books) throws IOException {
        backupOldFiles();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(MAIN_FILE))) {
            oos.writeObject(books);
        }
    }

    public static List<Book> load() {
        File file = new File(MAIN_FILE);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Book>) ois.readObject();
        } catch (Exception e) {
            // Try to restore from backup
            return restoreFromBackup();
        }
    }

    private static void backupOldFiles() {
        File dir = new File(BACKUP_DIR);
        dir.mkdir();
        File main = new File(MAIN_FILE);
        if (main.exists()) {
            String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
            main.renameTo(new File(dir, "books_" + timestamp + ".data"));
            // Keep only last 5 backups
            File[] backups = dir.listFiles(f -> f.getName().startsWith("books_"));
            if (backups != null && backups.length > 5) {
                java.util.Arrays.sort(backups, (a, b) -> Long.compare(b.lastModified(), a.lastModified()));
                for (int i = 5; i < backups.length; i++) backups[i].delete();
            }
        }
    }

    private static List<Book> restoreFromBackup() {
        File dir = new File(BACKUP_DIR);
        File[] backups = dir.listFiles(f -> f.getName().endsWith(".data"));
        if (backups == null || backups.length == 0) return new ArrayList<>();
        java.util.Arrays.sort(backups, (a, b) -> Long.compare(b.lastModified(), a.lastModified()));
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(backups[0]))) {
            return (List<Book>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}