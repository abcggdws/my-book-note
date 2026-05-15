package com.booknote.service;

import com.booknote.model.Book;
import com.booknote.model.Note;
import java.util.NoSuchElementException; 
public class NoteService {
    private BookService bookService;

    public NoteService(BookService bookService) {
        this.bookService = bookService;
    }

    public void addNote(String bookId, String content) {
        Book book = bookService.findBookById(bookId);
        if (book == null) throw new NoSuchElementException("书籍不存在");
        int newId = book.getNotes().stream().mapToInt(Note::getId).max().orElse(0) + 1;
        book.addNote(new Note(newId, content));
    }

    public void updateNote(String bookId, int noteId, String newContent) {
        Book book = bookService.findBookById(bookId);
        if (book == null) throw new NoSuchElementException("书籍不存在");
        Note note = book.getNotes().stream().filter(n -> n.getId() == noteId).findFirst().orElse(null);
        if (note == null) throw new NoSuchElementException("笔记不存在");
        note.setContent(newContent);
    }

    public void deleteNote(String bookId, int noteId) {
        Book book = bookService.findBookById(bookId);
        if (book == null) throw new NoSuchElementException("书籍不存在");
        book.removeNote(noteId);
    }
}