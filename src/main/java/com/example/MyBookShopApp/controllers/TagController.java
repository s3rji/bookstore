package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.dto.SearchWordTo;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("tags")
public class TagController {
    private final TagService tagService;
    private final BookService bookService;

    @Autowired
    public TagController(TagService tagService, BookService bookService) {
        this.tagService = tagService;
        this.bookService = bookService;
    }

    @ModelAttribute("searchWordTo")
    public SearchWordTo searchWordDto() {
        return new SearchWordTo();
    }

    @GetMapping("index")
    public String getTagPage(@RequestParam("tagId") Integer tagId, Model model) {
        model.addAttribute("tag", tagService.getById(tagId));
        model.addAttribute("booksByTag", bookService.getPageByTag(tagId, 0, 20));
        return "/tags/index";
    }
}
