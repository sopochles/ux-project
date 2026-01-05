package com.example.dclassics_books;

import java.io.Serializable;

public class Book implements Serializable {
    private String title;
    private String author;
    private int cover;
    private String type;

    public Book(String title, String author, int cover, String type) {
        this.title = title;
        this.author = author;
        this.cover = cover;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getCover() {
        return cover;
    }
    public String getType() {
        return type;
    }
}