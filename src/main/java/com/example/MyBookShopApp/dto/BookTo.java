package com.example.MyBookShopApp.dto;

import com.example.MyBookShopApp.model.author.Author;
import com.example.MyBookShopApp.model.book.Book;

import java.time.LocalDate;

public class BookTo {

    private Integer id;

    private LocalDate pubDate;

    private Short isBestseller;

    private String title;

    private String image;

    private String description;

    private Integer priceOld;

    private Integer price;

    private Author author;

    public BookTo(Book book) {
        this.id = book.getId();
        this.pubDate = book.getPubDate();
        this.isBestseller = book.isBestseller();
        this.title = book.getTitle();
        this.image = book.getImage();
        this.description = book.getDescription();
        this.priceOld = book.getPrice();
        this.price = book.getPrice() - (book.getDiscount() * book.getPrice() / 100);
        this.author = book.getAuthor();
    }

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

    public Short getIsBestseller() {
        return isBestseller;
    }

    public void setIsBestseller(Short isBestseller) {
        this.isBestseller = isBestseller;
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

    public Integer getPriceOld() {
        return priceOld;
    }

    public void setPriceOld(Integer priceOld) {
        this.priceOld = priceOld;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
