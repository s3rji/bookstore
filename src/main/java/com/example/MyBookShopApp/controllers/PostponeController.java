package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.dto.BookTo;
import com.example.MyBookShopApp.dto.SearchWordTo;
import com.example.MyBookShopApp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Controller
@RequestMapping("/postponed")
public class PostponeController {

    private final BookService bookService;

    @Autowired
    public PostponeController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute(name = "booksKept")
    public List<BookTo> booksKept() {
        return new ArrayList<>();
    }

    @ModelAttribute("searchWordTo")
    public SearchWordTo searchWordDto() {
        return new SearchWordTo();
    }

    @GetMapping
    public String getPostponedPage(@CookieValue(value = "keptContents", required = false) String keptContents, Model model) {
        if (!StringUtils.hasLength(keptContents)) {
            model.addAttribute("isKeptEmpty", true);
        } else {
            model.addAttribute("isKeptEmpty", false);
            String[] cookieSlugs = keptContents.split("/");
            List<BookTo> booksFromCookieSlugs = bookService.findBySlugIn(cookieSlugs);
            model.addAttribute("booksKept", booksFromCookieSlugs);
        }

        return "postponed";
    }

    @PostMapping("/{slug}")
    public String addBookCookie(@PathVariable("slug") String slug,
                                @CookieValue(name = "keptContents", required = false) String keptContents,
                                HttpServletResponse response,
                                Model model) {

        Cookie cookie = null;
        if (!StringUtils.hasLength(keptContents)) {
            cookie = new Cookie("keptContents", slug);
        } else if (!keptContents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(keptContents).add(slug);
            cookie = new Cookie("keptContents", stringJoiner.toString());
        }

        if (cookie != null) {
            cookie.setPath("/");
            response.addCookie(cookie);
            model.addAttribute("isKeptEmpty", false);
        }
        return "redirect:/books/" + slug;
    }

    @PostMapping("/remove/{slug}")
    public String removeBookCookie(@PathVariable("slug") String slug,
                                   @CookieValue(name = "keptContents", required = false) String keptContents,
                                   HttpServletResponse response,
                                   Model model) {

        if (StringUtils.hasLength(keptContents)) {
            List<String> cookieBooks = new ArrayList<>(Arrays.asList(keptContents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie("keptContents", String.join("/", cookieBooks));
            cookie.setPath("/");
            response.addCookie(cookie);
            model.addAttribute("isKeptEmpty", false);
        } else {
            model.addAttribute("isKeptEmpty", true);
        }
        return "redirect:/postponed";
    }
}
