package com.booknote;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookManager {
    private List<Book> bookList = new ArrayList<>();

    // 添加书
    public void addBook(String id, String name, String author) {
        bookList.add(new Book(id, name, author));
    }

    // 根据id查书
    public Optional<Book> findBookById(String id) {
        return bookList.stream().filter(b -> b.getBookId().equals(id)).findFirst();
    }

    // 删除书
    public boolean deleteBook(String id) {
        return bookList.removeIf(b -> b.getBookId().equals(id));
    }

    // 修改书信息
    public boolean updateBook(String id, String name, String author, String status) {
        Optional<Book> opt = findBookById(id);
        if (opt.isPresent()) {
            Book b = opt.get();
            b.setName(name);
            b.setAuthor(author);
            b.setStatus(status);
            return true;
        }
        return false;
    }

    // 展示所有书
    public List<Book> listAll() {
        return bookList;
    }
}