package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.dto.BookPageDto;
import com.example.MyBookShopApp.dto.BookTo;
import com.example.MyBookShopApp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        return bookService.getPageOfRecommended(0, 6);
    }

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/books/recommended")
    @ResponseBody
    public BookPageDto getBooksPage(@RequestParam("offset") Integer offset,
                                    @RequestParam("limit") Integer limit) {
        return new BookPageDto(bookService.getPageOfRecommended(offset, limit));
    }

}
