package com.example.libraryapp;

public class Issue {
    private String issueId;
    private String issueName;
    private String bookId;
    private String bookTitle;
    private String issueDate;
    private String returnDate;
    private String issueStatus;

    public Issue(){
        //this constructor is required
    }

    public Issue(String issueId, String issueName, String bookId, String bookTitle, String issueDate, String returnDate, String issueStatus) {
        this.issueId = issueId;
        this.issueName = issueName;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.issueStatus = issueStatus;
    }

    public String getIssueId() {
        return issueId;
    }

    public String getIssueName() {
        return issueName;
    }

    public String getBookId() {
        return bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public String getIssueStatus() {
        return issueStatus;
    }
}