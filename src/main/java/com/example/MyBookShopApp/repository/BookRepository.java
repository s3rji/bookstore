package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.model.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b FROM Book b WHERE b.pubDate >= :from AND b.pubDate <= :to")
    Page<Book> findByPubDateBetween(@Param("from") LocalDate from, @Param("to") LocalDate to, Pageable nextPage);

    @Query("SELECT b FROM Book b JOIN FETCH Book2TagEntity b2t ON b.id=b2t.bookId WHERE b2t.tagId=:tagId")
    Page<Book> findByTag(@Param("tagId") Integer tagId, Pageable nextPage);

    Page<Book> findAllByOrderByPopularityDesc(Pageable nextPage);
}
