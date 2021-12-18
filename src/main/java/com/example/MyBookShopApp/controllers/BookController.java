package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.dto.BookPageDto;
import com.example.MyBookShopApp.dto.BookTo;
import com.example.MyBookShopApp.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("books")
    public List<BookTo> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/recent")
    public String getRecentPage() {
        return "/books/recent";
    }

    @GetMapping(value = "/recent", params = {"offset", "limit"})
    @ResponseBody
    public BookPageDto getRecentPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BookPageDto(bookService.getPageOfRecent(offset, limit));
    }

    @GetMapping("/popular")
    public String getPopularPage() {
        return "/books/popular";
    }

    @GetMapping(value = "/popular", params = {"offset", "limit"})
    @ResponseBody
    public BookPageDto getPopularPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BookPageDto(bookService.getPageOfPopular(offset, limit));
    }

    @GetMapping(value = "/recommended", params = {"offset", "limit"})
    @ResponseBody
    public BookPageDto getBooksPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BookPageDto(bookService.getPageOfRecommended(offset, limit));
    }
}
