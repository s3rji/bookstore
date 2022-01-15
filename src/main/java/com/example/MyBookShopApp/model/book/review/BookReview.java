package com.example.MyBookShopApp.model.book.review;

import com.example.MyBookShopApp.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "book_review")
public class BookReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "INT NOT NULL")
    private int bookId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", columnDefinition = "INT NOT NULL")
    private User user;

    @Column(name = "time", columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime dateTime;

    @Column(columnDefinition = "TEXT NOT NULL")
    private String text;

    @OneToMany(mappedBy = "bookReview")
    private List<BookReviewLike> reviewLikes;

    public BookReview() {
    }

    public BookReview(Integer bookId, String text, User user) {
        this.bookId = bookId;
        this.text = text;
        this.user = user;
        dateTime = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime time) {
        this.dateTime = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<BookReviewLike> getReviewLikes() {
        return List.copyOf(reviewLikes);
    }

    public void setReviewLikes(Collection<BookReviewLike> reviewLikes) {
        this.reviewLikes = List.copyOf(reviewLikes);
    }

    public int getTotalLike() {
        return (int) reviewLikes.stream().filter(bookReviewLike -> bookReviewLike.getValue() == 1).count();
    }

    public int getTotalDislike() {
        return (int) reviewLikes.stream().filter(bookReviewLike -> bookReviewLike.getValue() == -1).count();
    }
}
