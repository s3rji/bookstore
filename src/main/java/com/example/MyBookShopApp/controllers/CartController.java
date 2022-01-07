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
@RequestMapping("/cart")
public class CartController {

    private final BookService bookService;

    @Autowired
    public CartController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute(name = "booksCart")
    public List<BookTo> booksCart() {
        return new ArrayList<>();
    }

    @ModelAttribute("searchWordTo")
    public SearchWordTo searchWordDto() {
        return new SearchWordTo();
    }

    @GetMapping
    public String getCartPage(@CookieValue(value = "cartContents", required = false) String cartContents, Model model) {
        if (!StringUtils.hasLength(cartContents)) {
            model.addAttribute("isCartEmpty", true);
        } else {
            model.addAttribute("isCartEmpty", false);
            cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
            cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() - 1) : cartContents;
            String[] cookieSlugs = cartContents.split("/");
            List<BookTo> booksFromCookieSlugs = bookService.findBySlugIn(cookieSlugs);
            model.addAttribute("booksCart", booksFromCookieSlugs);
        }
        return "cart";
    }

    @PostMapping("/{slug}")
    public String addBookCookie(@PathVariable("slug") String slug,
                                @CookieValue(name = "cartContents", required = false) String cartContents,
                                HttpServletResponse response,
                                Model model) {

        Cookie cookie = null;
        if (!StringUtils.hasLength(cartContents)) {
            cookie = new Cookie("cartContents", slug);
        } else if (!cartContents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(cartContents).add(slug);
            cookie = new Cookie("cartContents", stringJoiner.toString());
        }

        if (cookie != null) {
            cookie.setPath("/");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        }
        return "redirect:/books/" + slug;
    }

    @PostMapping("/remove/{slug}")
    public String removeBookCookie(@PathVariable("slug") String slug,
                                   @CookieValue(name = "cartContents", required = false) String cartContents,
                                   HttpServletResponse response,
                                   Model model) {

        if (StringUtils.hasLength(cartContents)) {
            List<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie("cartContents", String.join("/", cookieBooks));
            cookie.setPath("/");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        } else {
            model.addAttribute("isCartEmpty", true);
        }
        return "redirect:/cart";
    }
}
