package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/recent")
    public String getRecentPage(Model model) {
        model.addAttribute("recentBooks",
                bookService.getPageOfRecent(0, 20, LocalDate.now().minusMonths(1), LocalDate.now()));
        return "/books/recent";
    }

    @GetMapping("/popular")
    public String getPopularPage(Model model) {
        model.addAttribute("popularBooks", bookService.getPageOfPopular(0, 20));
        return "/books/popular";
    }
}
