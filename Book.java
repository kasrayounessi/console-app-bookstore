package com.company;

public class Book {
    private int bookId;
    private double price;
    private String title;
    private String author;
    private String category;
    private String description;

    public Book(){}

    public Book(double price, String title, String author, String category) {
        this.price = price;
        this.title = title;
        this.author = author;
        this.category = category;
    }

    public int getBookId() {
        return bookId;
    }

    public double getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
