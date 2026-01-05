package com.example.dclassics_books;

public class Store {
    private String name;
    private String address;
    private int image;

    public Store(String name, String address, int image) {
        this.name = name;
        this.address = address;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int image() {
        return image;
    }

}