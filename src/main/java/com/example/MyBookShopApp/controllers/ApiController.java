package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.dto.*;
import com.example.MyBookShopApp.model.user.User;
import com.example.MyBookShopApp.security.BookstoreUserRegister;
import com.example.MyBookShopApp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api")
public class ApiController {

    private final BookService bookService;
    private final BookstoreUserRegister userRegister;

    @Autowired
    public ApiController(BookService bookService, BookstoreUserRegister userRegister) {
        this.bookService = bookService;
        this.userRegister = userRegister;
    }

    @PostMapping("/changeBookStatus")
    public ModelAndView changeBookStatus(@RequestBody BookStatusTo bookStatus) {
        return switch (bookStatus.getStatus()) {
            case "UNLINK_CART" -> new ModelAndView("forward:/cart/remove/" + bookStatus.getBooksIds());
            case "UNLINK_KEPT" -> new ModelAndView("forward:/postponed/remove/" + bookStatus.getBooksIds());
            case "CART" -> new ModelAndView("forward:/cart/" + bookStatus.getBooksIds());
            case "KEPT" -> new ModelAndView("forward:/postponed/" + bookStatus.getBooksIds());
            default -> new ModelAndView("redirect:/books/" + bookStatus.getBooksIds());
        };
    }

    @PostMapping("/rateBook")
    @ResponseBody
    public ApiResult saveRateBook(@RequestBody ApiBookRate apiBookRate) {
        User currentUser = userRegister.getCurrentUser();
        if (currentUser.getName().equals("anonymousUser")) {
            return new ApiResult(false);
        }
        return new ApiResult(bookService.saveBookRating(apiBookRate.getBookId(), apiBookRate.getValue(), currentUser.getId()));
    }

    @PostMapping("/bookReview")
    @ResponseBody
    public ApiResult saveBookReview(@RequestBody ApiBookReview apiBookReview) {
        User currentUser = userRegister.getCurrentUser();
        if (currentUser.getName().equals("anonymousUser")) {
            return new ApiResult(false, "Оставлять отзывы могут только зарегистрированные пользователи");
        }

        if (apiBookReview.getText().trim().length() < 20) {
            return new ApiResult(false, "Отзыв слишком короткий. Напишите, пожалуйста, более развёрнутый отзыв");
        }
        return new ApiResult(bookService.saveBookReview(apiBookReview.getBookId(), apiBookReview.getText(), currentUser.getId()));
    }

    @PostMapping("/rateBookReview")
    @ResponseBody
    public ApiResult saveRateBookReview(@RequestBody ApiBookReviewLike apiBookReviewLike) {
        User currentUser = userRegister.getCurrentUser();
        if (currentUser.getName().equals("anonymousUser")) {
            return new ApiResult(false);
        }
        return new ApiResult(bookService.saveBookReviewLike(apiBookReviewLike.getReviewId(), apiBookReviewLike.getValue(),
                currentUser.getId()));
    }
}
