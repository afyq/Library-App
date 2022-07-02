package com.example.libraryapp;

public class Nilam {
    private String bookId;
    private String bookTitle;
    private String bookWriter;
    private String bookPublisher;
    private String bookLanguage;
    private int bookYearPublish;
    private String bookToRM;

    public Nilam(){
        //this constructor is required
    }

    public Nilam(String bookId, String bookTitle, String bookWriter, String bookPublisher, String bookLanguage, int bookYearPublish, String bookToRM) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookWriter = bookWriter;
        this.bookPublisher = bookPublisher;
        this.bookLanguage = bookLanguage;
        this.bookYearPublish = bookYearPublish;
        this.bookToRM = bookToRM;
    }

    public String getBookId() {
        return bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookWriter() {
        return bookWriter;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public String getBookLanguage() {
        return bookLanguage;
    }

    public int getBookYearPublish() {
        return bookYearPublish;
    }

    public String getBookToRM() {
        return bookToRM;
    }
}
