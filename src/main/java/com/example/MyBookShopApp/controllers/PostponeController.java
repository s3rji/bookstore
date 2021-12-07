package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/postponed")
public class PostponeController {

    private final BookService bookService;

    public PostponeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String getPostponedPage() {
        return "postponed";
    }
}
