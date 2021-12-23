package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.model.genre.Genre;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;
    private final BookService bookService;

    @Autowired
    public GenreController(GenreService genreService, BookService bookService) {
        this.genreService = genreService;
        this.bookService = bookService;
    }

    @GetMapping
    public String getGenresPage(Model model) {
        model.addAttribute("genres", genreService.getParents());
        return "/genres/index";
    }

    @GetMapping("/{slug}")
    public String getGenresSlug(@PathVariable("slug") String slug, Model model) {
        Genre genre = genreService.getBySlug(slug);
        model.addAttribute("genre", genre);
        model.addAttribute("booksByGenre", bookService.getPageByGenre(genre.getId(), 0, 20));
        return "/genres/slug";
    }
}
