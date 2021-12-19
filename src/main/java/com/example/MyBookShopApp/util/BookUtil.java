package com.example.MyBookShopApp.util;

import com.example.MyBookShopApp.dto.BookTo;
import com.example.MyBookShopApp.model.book.Book;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public final class BookUtil {

    private BookUtil() {

    }

    public static List<BookTo> getTos(Collection<Book> books) {
        return books.stream().map(BookTo::new).toList();
    }

    public static List<BookTo> getTosPopularSorting(Collection<Book> books) {
        return books.stream()
                .sorted(Comparator.comparingDouble(BookUtil::getRatingPopularity).reversed())
                .map(BookTo::new)
                .toList();
    }

    public static double getRatingPopularity(Book book) {
        return book.getTotalBought() + 0.7 * book.getTotalCart() + 0.4 * book.getTotalPostponed();
    }
}
