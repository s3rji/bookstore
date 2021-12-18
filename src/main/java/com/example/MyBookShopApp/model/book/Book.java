package com.example.MyBookShopApp.model.book;

import com.example.MyBookShopApp.model.author.Author;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "DATE NOT NULL")
    private LocalDate pubDate;

    @Column(columnDefinition = "SMALLINT NOT NULL")
    private Short isBestseller;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String slug;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String title;

    @Column(columnDefinition = "VARCHAR(255)")
    private String image;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "INT NOT NULL")
    private Integer price;

    @Column(columnDefinition = "SMALLINT NOT NULL DEFAULT 0")
    private Short discount;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @JsonIgnore
    private Author author;

    @Column(columnDefinition = "INT NOT NULL DEFAULT 0")
    private Integer totalBought;

    @Column(columnDefinition = "INT NOT NULL DEFAULT 0")
    private Integer totalCart;

    @Column(columnDefinition = "INT NOT NULL DEFAULT 0")
    private Integer totalPostponed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getPubDate() {
        return pubDate;
    }

    public void setPubDate(LocalDate pubDate) {
        this.pubDate = pubDate;
    }

    public Short isBestseller() {
        return isBestseller;
    }

    public void setIsBestseller(Short isBestseller) {
        this.isBestseller = isBestseller;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Short getDiscount() {
        return discount;
    }

    public void setDiscount(Short discount) {
        this.discount = discount;
    }

    public Short getIsBestseller() {
        return isBestseller;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Integer getTotalBought() {
        return totalBought;
    }

    public void setTotalBought(Integer totalBought) {
        this.totalBought = totalBought;
    }

    public Integer getTotalCart() {
        return totalCart;
    }

    public void setTotalCart(Integer totalCart) {
        this.totalCart = totalCart;
    }

    public Integer getTotalPostponed() {
        return totalPostponed;
    }

    public void setTotalPostponed(Integer totalPostponed) {
        this.totalPostponed = totalPostponed;
    }
}
