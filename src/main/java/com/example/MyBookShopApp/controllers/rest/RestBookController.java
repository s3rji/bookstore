package com.example.MyBookShopApp.controllers.rest;

import com.example.MyBookShopApp.dto.ApiResponse;
import com.example.MyBookShopApp.dto.BookPageTo;
import com.example.MyBookShopApp.dto.BookTo;
import com.example.MyBookShopApp.ex.BookstoreApiWrongParameterException;
import com.example.MyBookShopApp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.example.MyBookShopApp.util.DateUtil.atFromDateOrMin;
import static com.example.MyBookShopApp.util.DateUtil.atToDateOrMax;

@RestController
@RequestMapping("/api/books")
public class RestBookController {

    private final BookService bookService;

    @Autowired
    public RestBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/recent")
    public BookPageTo getRecentPage(@RequestParam("offset") Integer offset,
                                    @RequestParam("limit") Integer limit,
                                    @RequestParam("from") @DateTimeFormat(pattern = "dd.MM.yyyy") @Nullable LocalDate from,
                                    @RequestParam("to") @DateTimeFormat(pattern = "dd.MM.yyyy") @Nullable LocalDate to) {
        return new BookPageTo(bookService.getPageOfRecent(offset, limit, atFromDateOrMin(from), atToDateOrMax(to)));
    }

    @GetMapping("/popular")
    public BookPageTo getPopularPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BookPageTo(bookService.getPageOfPopular(offset, limit));
    }

    @GetMapping("/recommended")
    public BookPageTo getBooksPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BookPageTo(bookService.getPageOfRecommended(offset, limit));
    }

    @GetMapping("/tag/{id}")
    public BookPageTo getBooksByTagPage(@PathVariable("id") Integer id,
                                        @RequestParam("offset") Integer offset,
                                        @RequestParam("limit") Integer limit) {
        return new BookPageTo(bookService.getPageByTag(id, offset, limit));
    }

    @GetMapping("/genre/{id}")
    public BookPageTo getBooksByGenrePage(@PathVariable("id") Integer id,
                                          @RequestParam("offset") Integer offset,
                                          @RequestParam("limit") Integer limit) {
        return new BookPageTo(bookService.getPageByGenre(id, offset, limit));
    }

    @GetMapping("/by-title")
    public ResponseEntity<ApiResponse<BookTo>> booksByTitle(@RequestParam("title") String title) throws BookstoreApiWrongParameterException {
        ApiResponse<BookTo> response = new ApiResponse<>();
        List<BookTo> data = bookService.getAllByTitle(title);
        response.setDebugMessage("successful request");
        response.setMessage("data size: " + data.size() + " elements");
        response.setStatus(HttpStatus.OK);
        response.setData(data);
        return ResponseEntity.ok(response);
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
