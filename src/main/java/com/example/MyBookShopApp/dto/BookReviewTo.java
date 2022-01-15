package com.example.MyBookShopApp.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BookReviewTo {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private int id;

    private String userName;

    private LocalDateTime dateTime;

    private String text;

    private int rating;

    private int totalLike;

    private int totalDislike;

    public BookReviewTo(int id, String userName, LocalDateTime dateTime, String text, int rating, int totalLike, int totalDislike) {
        this.id = id;
        this.userName = userName;
        this.dateTime = dateTime;
        this.text = text;
        this.rating = rating;
        this.totalLike = totalLike;
        this.totalDislike = totalDislike;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStringDateTime() {
        return dateTime.format(formatter);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(int totalLike) {
        this.totalLike = totalLike;
    }

    public int getTotalDislike() {
        return totalDislike;
    }

    public void setTotalDislike(int totalDislike) {
        this.totalDislike = totalDislike;
    }
}
