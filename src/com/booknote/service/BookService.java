package com.booknote.service;

import java.util.*;
import com.booknote.model.Book;
import com.booknote.model.Note;
import com.booknote.io.FileStore;

public class BookService {
    private List<Book> books;

    public BookService() {
        books = FileStore.load();
    }

    public void addBook(String id, String name, String author, String category, int totalPages) {
        if (findBookById(id) != null) throw new IllegalArgumentException("ID已存在");
        books.add(new Book(id, name, author, category, totalPages));
    }

    public Book findBookById(String id) {
        return books.stream().filter(b -> b.getId().equals(id)).findFirst().orElse(null);
    }

    public boolean deleteBook(String id) {
        return books.removeIf(b -> b.getId().equals(id));
    }

    public void updateBook(String id, String name, String author, String category) {
        Book b = findBookById(id);
        if (b == null) throw new NoSuchElementException("书籍不存在");
        if (!name.isEmpty()) b.setName(name);
        if (!author.isEmpty()) b.setAuthor(author);
        if (!category.isEmpty()) b.setCategory(category);
    }

    public List<Book> listAll() { return new ArrayList<>(books); }

    public List<Book> search(String keyword) {
        List<Book> result = new ArrayList<>();
        for (Book b : books) {
            if (b.getName().contains(keyword) || b.getAuthor().contains(keyword)) {
                result.add(b);
                continue;
            }
            for (Note n : b.getNotes()) {
                if (n.getContent().contains(keyword)) {
                    result.add(b);
                    break;
                }
            }
        }
        return result;
    }

    public List<Book> listByCategory(String category) {
        List<Book> result = new ArrayList<>();
        for (Book b : books) if (b.getCategory().equals(category)) result.add(b);
        return result;
    }

    public void save() throws Exception {
        FileStore.save(books);
    }
}