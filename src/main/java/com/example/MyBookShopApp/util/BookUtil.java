package com.example.MyBookShopApp.util;

import com.example.MyBookShopApp.dto.BookRatingTo;
import com.example.MyBookShopApp.dto.BookReviewTo;
import com.example.MyBookShopApp.dto.BookTo;
import com.example.MyBookShopApp.model.book.Book;
import com.example.MyBookShopApp.model.book.BookRating;
import com.example.MyBookShopApp.model.book.review.BookReview;

import java.time.LocalDateTime;
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

    public static BookRatingTo getBookRatingTos(List<BookRating> bookRatings) {
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

    static double getRatingPopularity(Book book) {
        return book.getTotalBought() + 0.7 * book.getTotalCart() + 0.4 * book.getTotalPostponed();
    }

    public static List<BookReviewTo> getBookReviewTos(List<BookReview> bookReviews) {
        return bookReviews.stream()
                .map(bookReview -> {
                    int id = bookReview.getId();
                    String userName = bookReview.getUser().getName();
                    LocalDateTime dateTime = bookReview.getDateTime();
                    String text = bookReview.getText();
                    int totalLike = bookReview.getTotalLike();
                    int totalDislike = bookReview.getTotalDislike();
                    int rating = getBookReviewRating(totalLike, totalDislike);
                    return new BookReviewTo(id, userName, dateTime, text, rating, totalLike, totalDislike);
                })
                .sorted(Comparator.comparingInt(BookReviewTo::getRating).reversed().thenComparing(BookReviewTo::getId))
                .collect(Collectors.toList());
    }

    static int getBookReviewRating(int totalLike, int totalDislike) {
        if (totalLike == 0 && totalDislike == 0) return 0;
        return (int) Math.round(3 + 1.00 * (totalLike - totalDislike) / (totalLike + totalDislike) * 2);
    }
}
