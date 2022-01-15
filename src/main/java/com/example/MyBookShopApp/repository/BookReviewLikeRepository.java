package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.model.book.review.BookReview;
import com.example.MyBookShopApp.model.book.review.BookReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewLikeRepository extends JpaRepository<BookReviewLike, Integer> {

    BookReviewLike findByBookReviewAndUserId(BookReview bookReview, Integer userId);
}
