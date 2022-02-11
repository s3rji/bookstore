package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.dto.ApiResult;
import com.example.MyBookShopApp.dto.SearchWordTo;
import com.example.MyBookShopApp.ex.ErrorInfo;
import com.example.MyBookShopApp.ex.IllegalRequestDataException;
import com.example.MyBookShopApp.security.BookstoreUserRegister;
import com.example.MyBookShopApp.security.ContactConfirmationPayload;
import com.example.MyBookShopApp.security.ContactConfirmationResponse;
import com.example.MyBookShopApp.security.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthUserController {

    private final BookstoreUserRegister userRegister;

    @Autowired
    public AuthUserController(BookstoreUserRegister userRegister) {
        this.userRegister = userRegister;
    }

    @ModelAttribute("searchWordTo")
    public SearchWordTo searchWordDto() {
        return new SearchWordTo();
    }

    @GetMapping("/signin")
    public String getSigninPage() {
        return "signin";
    }

    @GetMapping("/signup")
    public String getSignupPage(Model model) {
        model.addAttribute("regForm", new RegistrationForm());
        return "signup";
    }

    @PostMapping("/requestContactConfirmation")
    @ResponseBody
    public ApiResult requestContactConfirmation(@RequestBody ContactConfirmationPayload payload) {
        return new ApiResult(true);
    }

    @PostMapping("/approveContact")
    @ResponseBody
    public ApiResult approveContact(@RequestBody ContactConfirmationPayload payload) {
        return new ApiResult(true);
    }

    @PostMapping("/reg")
    public String userRegistration(RegistrationForm registrationForm, RedirectAttributes attributes, HttpServletRequest httpServletRequest) {
        userRegister.registerNewUser(registrationForm);
        attributes.addFlashAttribute("regOk", true);
        return "redirect:/signin";
    }

    @PostMapping("/login")
    @ResponseBody
    public ContactConfirmationResponse handleLogin(@RequestBody ContactConfirmationPayload payload,
                                                   HttpServletResponse httpServletResponse) {
        ContactConfirmationResponse loginResponse = userRegister.jwtLogin(payload);
        Cookie cookie = new Cookie("token", loginResponse.getResult());
        httpServletResponse.addCookie(cookie);
        return loginResponse;
    }

    @GetMapping("/my")
    public String getMyPage() {
        return "my";
    }

    @GetMapping("/profile")
    public String getProfilePage(Model model) {
        model.addAttribute("currentUser", userRegister.getCurrentUser());
        return "profile";
    }

    @ExceptionHandler(IllegalRequestDataException.class)
    public String handleIllegalRequestDataException(Exception exception, RedirectAttributes attributes) {
        attributes.addFlashAttribute("errorInfo", new ErrorInfo(exception.getMessage()));
        return "redirect:/signup";
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseBody
    public ApiResult handleUsernameNotFoundException(Exception exception) {
        return new ApiResult(false, exception.getMessage());
    }
}
