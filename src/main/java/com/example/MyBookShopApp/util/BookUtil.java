package com.example.MyBookShopApp.util;

import com.example.MyBookShopApp.dto.BookTo;
import com.example.MyBookShopApp.model.book.Book;

import java.util.Collection;
import java.util.List;

public class BookUtil {

    private BookUtil() {

    }

    public static List<BookTo> getTos(Collection<Book> books) {
        return books.stream().map(BookTo::new).toList();
    }
}
