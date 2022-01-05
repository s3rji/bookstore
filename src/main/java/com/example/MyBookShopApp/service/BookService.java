package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.dto.BookTo;
import com.example.MyBookShopApp.model.book.Book;
import com.example.MyBookShopApp.repository.BookRepository;
import com.example.MyBookShopApp.repository.GenreRepository;
import com.example.MyBookShopApp.util.BookUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public BookService(BookRepository bookRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
    }

    public List<BookTo> getAll() {
        return BookUtil.getTos(bookRepository.findAll());
    }

    public List<BookTo> getPageOfRecommended(Integer offset, Integer limit) {
        return BookUtil.getTos(bookRepository.findAll(PageRequest.of(offset, limit)).getContent());
    }

    public List<BookTo> getPageOfRecent(Integer offset, Integer limit, LocalDate from, LocalDate to) {
        Pageable nextPage = PageRequest.of(offset, limit, Sort.by("pubDate").descending());
        return BookUtil.getTos(bookRepository.findAllByPubDateBetween(from, to, nextPage).getContent());
    }

    public List<BookTo> getPageOfPopular(Integer offset, Integer limit) {
        return BookUtil.getTos(bookRepository.findAllByOrderByPopularityDesc(PageRequest.of(offset, limit)).getContent());
    }

    public List<BookTo> getPageByTag(Integer tagId, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit, Sort.by("pubDate").descending());
        return BookUtil.getTos(bookRepository.findAllByTag(tagId, nextPage).getContent());
    }

    public List<BookTo> getPageByGenre(Integer genreId, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit, Sort.by("pubDate").descending());
        return BookUtil.getTos(bookRepository.findAllByGenre(genreId, nextPage).getContent());
    }

    public List<BookTo> getPageByAuthor(Integer authorId, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit, Sort.by("pubDate").descending());
        return BookUtil.getTos(bookRepository.findAllByAuthor(authorId, nextPage).getContent());
    }

    public BookTo findBookBySlug(String slug) {
        return new BookTo(bookRepository.findBySlug(slug));
    }
}
