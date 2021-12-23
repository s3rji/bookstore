package com.example.MyBookShopApp.controllers.rest;

import com.example.MyBookShopApp.dto.BookPageTo;
import com.example.MyBookShopApp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
}
