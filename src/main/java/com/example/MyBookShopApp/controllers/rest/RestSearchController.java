package com.example.MyBookShopApp.controllers.rest;

import com.example.MyBookShopApp.dto.BookPageTo;
import com.example.MyBookShopApp.dto.SearchWordTo;
import com.example.MyBookShopApp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
public class RestSearchController {

    private final BookService bookService;

    @Autowired
    public RestSearchController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/page/{searchWord}")
    public BookPageTo getNextSearchPage(@RequestParam("offset") Integer offset,
                                        @RequestParam("limit") Integer limit,
                                        @PathVariable(value = "searchWord", required = false) SearchWordTo searchWordTo) {
        return new BookPageTo(bookService.getPageOfSearchResult(searchWordTo.getExample(), offset, limit));
    }
}
