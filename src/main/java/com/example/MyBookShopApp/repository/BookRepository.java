package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.dto.BookTo;
import com.example.MyBookShopApp.model.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b FROM Book b WHERE b.pubDate >= :from AND b.pubDate <= :to")
    @EntityGraph(attributePaths = "author")
    Page<Book> findAllByPubDateBetween(@Param("from") LocalDate from, @Param("to") LocalDate to, Pageable nextPage);

    @Query("SELECT b FROM Book b JOIN FETCH Book2TagEntity b2t ON b.id=b2t.bookId WHERE b2t.tagId=:tagId")
    Page<Book> findAllByTag(@Param("tagId") Integer tagId, Pageable nextPage);

    @EntityGraph(attributePaths = "author")
    Page<Book> findAllByOrderByPopularityDesc(Pageable nextPage);

    @Query("SELECT b FROM Book b JOIN FETCH Book2GenreEntity b2g ON b.id=b2g.bookId WHERE b2g.genreId=:genreId")
    Page<Book> findAllByGenre(@Param("genreId") Integer genreId, Pageable nextPage);

    @Query("SELECT b FROM Book b WHERE b.author.id=:authorId")
    Page<Book> findAllByAuthor(@Param("authorId") Integer authorId, Pageable nextPage);

    @EntityGraph(attributePaths = "author")
    Book findBySlug(String slug);
}
