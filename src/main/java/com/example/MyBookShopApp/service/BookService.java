package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.dto.ApiResponse;
import com.example.MyBookShopApp.dto.BookTo;
import com.example.MyBookShopApp.ex.BookstoreApiWrongParameterException;
import com.example.MyBookShopApp.model.book.Book;
import com.example.MyBookShopApp.repository.BookRepository;
import com.example.MyBookShopApp.repository.GenreRepository;
import com.example.MyBookShopApp.util.BookUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

    public List<BookTo> getPageOfSearchResult(String searchWord, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return BookUtil.getTos(bookRepository.findAllByTitleContaining(searchWord, nextPage).getContent());
    }

    public BookTo findBySlug(String slug) {
        return new BookTo(bookRepository.findBySlug(slug));
    }

    public void saveImage(String savePath, String slug) {
        Book book = bookRepository.findBySlug(slug);
        book.setImage(savePath);
        bookRepository.save(book);
    }

    public List<BookTo> getAllByTitle(String title) throws BookstoreApiWrongParameterException {
        if (title.length() <= 1) {
            throw new BookstoreApiWrongParameterException("Wrong values passed to one or more parameters");
        } else {
            List<BookTo> data = BookUtil.getTos(bookRepository.findAllByTitleContaining(title));
            if (data.size() > 0) {
                return data;
            } else {
                throw new BookstoreApiWrongParameterException("No data found with specified parameters...");
            }
        }
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<BookTo>> handleMissingServletRequestParameterException(Exception exception) {
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.BAD_REQUEST, "Missing required parameters", exception),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookstoreApiWrongParameterException.class)
    public ResponseEntity<ApiResponse<BookTo>> handleBookstoreApiWrongParameterException(Exception exception) {
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.BAD_REQUEST, "Bad parameter value...", exception),
                HttpStatus.BAD_REQUEST);
    }
}
