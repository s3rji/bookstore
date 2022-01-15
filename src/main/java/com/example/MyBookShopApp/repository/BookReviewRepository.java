package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.model.book.review.BookReview;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookReviewRepository extends JpaRepository<BookReview, Integer> {
    @EntityGraph(attributePaths = {"reviewLikes", "user"})
    List<BookReview> findAllByBookId(Integer bookId);
}
