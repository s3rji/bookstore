package com.example.MyBookShopApp.model.book.review;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_review_like")
public class BookReviewLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "review_id", referencedColumnName = "id", columnDefinition = "INT NOT NULL")
    private BookReview bookReview;

    @Column(columnDefinition = "INT NOT NULL")
    private int userId;

    @Column(name = "time", columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime dateTime;

    @Column(columnDefinition = "SMALLINT NOT NULL")
    private Short value;

    public BookReviewLike() {
    }

    public BookReviewLike(BookReview bookReview, Integer userId, Short value) {
        this.bookReview = bookReview;
        this.userId = userId;
        this.value = value;
        this.dateTime = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookReview getBookReview() {
        return bookReview;
    }

    public void setBookReview(BookReview bookReview) {
        this.bookReview = bookReview;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime time) {
        this.dateTime = time;
    }

    public Short getValue() {
        return value;
    }

    public void setValue(Short value) {
        this.value = value;
    }
}
