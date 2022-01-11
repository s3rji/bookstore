package com.example.MyBookShopApp.util;

import com.example.MyBookShopApp.dto.BookRatingTo;
import com.example.MyBookShopApp.dto.BookTo;
import com.example.MyBookShopApp.model.book.Book;
import com.example.MyBookShopApp.model.book.BookRating;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class BookUtil {

    private BookUtil() {
    }

    public static List<BookTo> getTos(Collection<Book> books) {
        return books.stream().map(BookTo::new).toList();
    }

    public static BookRatingTo getBookRatingTo(List<BookRating> bookRatings) {
        Map<Short, Long> votesByRating = bookRatings.stream()
                .collect(Collectors.groupingBy(BookRating::getRating, Collectors.counting()));
        short averageRating = (short) Math.round(bookRatings.stream()
                .mapToDouble(BookRating::getRating).sum() / bookRatings.size());

        return new BookRatingTo(bookRatings.size(), averageRating, votesByRating);
    }

    public static List<BookTo> getTosPopularSorting(Collection<Book> books) {
        return books.stream()
                .sorted(Comparator.comparingDouble(BookUtil::getRatingPopularity).reversed())
                .map(BookTo::new)
                .toList();
    }

    private static double getRatingPopularity(Book book) {
        return book.getTotalBought() + 0.7 * book.getTotalCart() + 0.4 * book.getTotalPostponed();
    }
}
