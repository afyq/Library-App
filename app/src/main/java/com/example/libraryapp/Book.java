package com.example.libraryapp;

public class Book {
    private String bookId;
    private String bookTitle;
    private String bookWriter;
    private String bookPublisher;
    private String bookGenre;
    private int bookPage;
    private int bookAvailability;

    public Book(){
        //this constructor is required
    }

    public Book(String bookId, String bookTitle, String bookWriter, String bookPublisher, String bookGenre, int bookPage, int bookAvailability) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookWriter = bookWriter;
        this.bookPublisher = bookPublisher;
        this.bookGenre = bookGenre;
        this.bookPage = bookPage;
        this.bookAvailability = bookAvailability;
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

    public String getBookGenre() {
        return bookGenre;
    }

    public int getBookPage() {
        return bookPage;
    }

    public int getBookAvailability() {
        return bookAvailability;
    }
}
