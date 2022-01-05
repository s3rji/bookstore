package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.dto.SearchWordTo;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.ResourceStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final ResourceStorage storage;

    @Autowired
    public BookController(BookService bookService, ResourceStorage storage) {
        this.bookService = bookService;
        this.storage = storage;
    }

    @ModelAttribute("searchWordTo")
    public SearchWordTo searchWordDto() {
        return new SearchWordTo();
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

    @GetMapping("/{slug}")
    public String getSlugPage(@PathVariable("slug") String slug, Model model) {
        model.addAttribute("slugBook", bookService.findBySlug(slug));
        return "/books/slug";
    }

    @PostMapping("/{slug}/img/save")
    public String saveNewImage(@RequestParam("file") MultipartFile file, @PathVariable("slug") String slug) throws IOException {
        String savePath = storage.saveNewBookImage(file, slug);
        bookService.saveImage(savePath, slug);
        return "redirect:/books/" + slug;
    }
}
