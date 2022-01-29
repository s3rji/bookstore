package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.dto.SearchWordTo;
import com.example.MyBookShopApp.model.author.Author;
import com.example.MyBookShopApp.service.AuthorService;
import com.example.MyBookShopApp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @ModelAttribute("searchWordTo")
    public SearchWordTo searchWordDto() {
        return new SearchWordTo();
    }

    @GetMapping()
    public String getAuthorsPage(Model model) {
        model.addAttribute("authorsMap", authorService.getAll());
        return "/authors/index";
    }

    @GetMapping("/{slug}")
    public String getAuthorSlug(@PathVariable("slug") String slug, Model model) {
        Author author = authorService.getBySlug(slug);
        model.addAttribute("authorBooks", bookService.getPageByAuthor(author.getId(), 0, 5));
        model.addAttribute("author", author);
        return "/authors/slug";
    }
}
