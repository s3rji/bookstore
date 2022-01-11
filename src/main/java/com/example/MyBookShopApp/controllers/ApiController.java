package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.dto.RateResult;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api")
public class ApiController {

    private final BookService bookService;

    @Autowired
    public ApiController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/changeBookStatus")
    public ModelAndView changeBookStatus(@RequestParam("status") String status,
                                         @RequestParam("slugBook") String slugBook) {
        return switch (status) {
            case "UNLINK_CART" -> new ModelAndView("forward:/cart/remove/" + slugBook);
            case "UNLINK_KEPT" -> new ModelAndView("forward:/postponed/remove/" + slugBook);
            case "CART" -> new ModelAndView("forward:/cart/" + slugBook);
            case "KEPT" -> new ModelAndView("forward:/postponed/" + slugBook);
            default -> new ModelAndView("redirect:/books/" + slugBook);
        };
    }

    @PostMapping("/rateBook")
    @ResponseBody
    public RateResult rateBook(@RequestParam("bookId") Integer bookId,
                               @RequestParam("value") Short value) {
        return new RateResult(bookService.saveBookRating(bookId, value, SecurityUtil.authUserId()));
    }
}
