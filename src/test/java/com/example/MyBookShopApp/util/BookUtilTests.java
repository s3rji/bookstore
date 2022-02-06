package com.example.MyBookShopApp.util;

import com.example.MyBookShopApp.BookTestData;
import com.example.MyBookShopApp.model.book.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookUtilTests {

    @Test
    void getRatingPopularity() {
        Book book = BookTestData.getTestBook();
        double actualPopularity = BookUtil.getRatingPopularity(book);
        assertEquals(book.getPopularity(), actualPopularity);
    }

    @Test
    void getBookReviewRating() {
        int actualReviewRating = BookUtil.getBookReviewRating(100, 50);
        assertEquals(4, actualReviewRating);
        actualReviewRating = BookUtil.getBookReviewRating(50, 100);
        assertEquals(2, actualReviewRating);
        actualReviewRating = BookUtil.getBookReviewRating(0, 0);
        assertEquals(0, actualReviewRating);
    }
}