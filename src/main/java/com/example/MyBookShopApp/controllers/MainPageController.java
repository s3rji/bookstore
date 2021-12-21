package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.dto.BookTo;
import com.example.MyBookShopApp.model.tag.Tag;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

import static com.example.MyBookShopApp.util.DateUtil.atFromDateOrMin;
import static com.example.MyBookShopApp.util.DateUtil.atToDateOrMax;

@Controller
public class MainPageController {

    private final BookService bookService;
    private final TagService tagService;

    @Autowired
    public MainPageController(BookService bookService, TagService tagService) {
        this.bookService = bookService;
        this.tagService = tagService;
    }

    @ModelAttribute("recommendedBooks")
    public List<BookTo> recommendedBooks() {
        return bookService.getPageOfRecommended(0, 6);
    }

    @ModelAttribute("recentBooks")
    public List<BookTo> recentBooks() {
        return bookService.getPageOfRecent(0, 6, atFromDateOrMin(null), atToDateOrMax(null));
    }

    @ModelAttribute("popularBooks")
    public List<BookTo> popularBooks() {
        return bookService.getPageOfPopular(0, 6);
    }

    @ModelAttribute("tags")
    public List<Tag> allTags() {
        return tagService.getAll();
    }

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }
}
