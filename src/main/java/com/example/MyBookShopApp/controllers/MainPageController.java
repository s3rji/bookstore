package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.dto.BookTo;
import com.example.MyBookShopApp.model.book.Book;
import com.example.MyBookShopApp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class MainPageController {

    private final BookService bookService;

    @Autowired
    public MainPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("listBooks")
    public List<BookTo> recommendedBooks() {
        return bookService.getAll();
    }

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

}
