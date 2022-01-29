package com.example.MyBookShopApp.dto;

public class ApiBookReviewLike {
    private Integer reviewId;
    private Short value;

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public Short getValue() {
        return value;
    }

    public void setValue(Short value) {
        this.value = value;
    }
}
