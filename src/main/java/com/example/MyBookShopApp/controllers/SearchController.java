package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.dto.SearchWordTo;
import com.example.MyBookShopApp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SearchController {

    private final BookService bookService;

    @Autowired
    public SearchController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = {"/search", "/search/{searchWord}"})
    public String getSearchResult(@PathVariable(value = "searchWord", required = false) SearchWordTo searchWordTo, Model model) {
        model.addAttribute("searchWordTo", searchWordTo);
        model.addAttribute("searchResults", bookService.getPageOfSearchResult(searchWordTo.getExample(), 0, 20));
        return "/search/index";
    }
}
