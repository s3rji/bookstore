package com.example.MyBookShopApp;

import com.example.MyBookShopApp.model.book.Book;

import java.time.LocalDate;

public class BookTestData {
    public static final int BOOK_ID = 1;

    public static Book getTestBook() {
        Book book = new Book();
        book.setId(BOOK_ID);
        book.setPubDate(LocalDate.now());
        book.setIsBestseller((short) 1);
        book.setSlug("testSlug");
        book.setTitle("testTitle");
        book.setImage("testImage");
        book.setDescription("testDescription");
        book.setPrice(1000);
        book.setDiscount((short) 10);
        book.setTotalBought(100);
        book.setTotalCart(1000);
        book.setTotalPostponed(1000);
        book.setPopularity(1200d);
        return book;
    }
}
