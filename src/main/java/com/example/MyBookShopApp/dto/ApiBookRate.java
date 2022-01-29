package com.example.MyBookShopApp.dto;

public class ApiBookRate {
    private Integer bookId;
    private Short value;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Short getValue() {
        return value;
    }

    public void setValue(Short value) {
        this.value = value;
    }
}
