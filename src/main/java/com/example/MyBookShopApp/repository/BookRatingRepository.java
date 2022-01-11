package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.model.book.BookRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRatingRepository extends JpaRepository<BookRating, Integer> {
    List<BookRating> findAllByBookId(Integer bookId);

    BookRating findByBookIdAndUserId(Integer bookId, int authUserId);
}
