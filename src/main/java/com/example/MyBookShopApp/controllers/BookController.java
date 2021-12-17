package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.dto.BookTo;
import com.example.MyBookShopApp.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/popular")
    public String getPopularPage() {
        return "/books/popular";
    }
}
