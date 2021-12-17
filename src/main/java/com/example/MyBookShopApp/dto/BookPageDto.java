package com.example.MyBookShopApp.dto;

import java.util.List;

public class BookPageDto {

    private Integer count;
    private List<BookTo> books;

    public BookPageDto(List<BookTo> books) {
        this.count = books.size();
        this.books = books;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<BookTo> getBooks() {
        return books;
    }

    public void setBooks(List<BookTo> books) {
        this.books = books;
    }
}
