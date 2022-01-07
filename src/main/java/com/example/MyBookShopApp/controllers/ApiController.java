package com.example.MyBookShopApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api")
public class ApiController {

    @PostMapping("/changeBookStatus")
    public ModelAndView changeBookStatus(@RequestParam("status") String status,
                                         @RequestParam("booksIds") String slug) {
        return switch (status) {
            case "UNLINK_CART" -> new ModelAndView("forward:/cart/remove/" + slug);
            case "UNLINK_KEPT" -> new ModelAndView("forward:/postponed/remove/" + slug);
            case "CART" -> new ModelAndView("forward:/cart/" + slug);
            case "KEPT" -> new ModelAndView("forward:/postponed/" + slug);
            default -> new ModelAndView("redirect:/books/" + slug);
        };
    }
}
