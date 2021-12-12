package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/genres")
public class GenreController {

    private final BookService bookService;

    public GenreController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String getGenresPage() {
        return "genres/index";
    }
}
