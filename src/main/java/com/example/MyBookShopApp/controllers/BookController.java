package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.dto.BookReviewTo;
import com.example.MyBookShopApp.dto.BookTo;
import com.example.MyBookShopApp.dto.SearchWordTo;
import com.example.MyBookShopApp.model.book.BookRating;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.ResourceStorage;
import com.example.MyBookShopApp.util.BookUtil;
import com.example.MyBookShopApp.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

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

    @ModelAttribute("currentUserRatingBook")
    public Short BookRating() {
        return 0;
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
        BookTo slugBook = bookService.getBySlug(slug);
        List<BookRating> bookRatings = bookService.getBookRatingsByBookId(slugBook.getId());
        List<BookReviewTo> bookReviews = bookService.getBookReviewsByBookId(slugBook.getId());
        BookRating currentUserRatingBook = bookService.getCurrentUserBookRating(slugBook.getId(), SecurityUtil.authUserId());
        if (currentUserRatingBook != null) {
            model.addAttribute("currentUserRatingBook", currentUserRatingBook.getRating());
        }
        model.addAttribute("slugBook", slugBook);
        model.addAttribute("bookRatings", BookUtil.getBookRatingTos(bookRatings));
        model.addAttribute("bookReviews", bookReviews);

        return "/books/slugmy";
    }

    @PostMapping("/{slug}/img/save")
    public String saveNewImage(@RequestParam("file") MultipartFile file, @PathVariable("slug") String slug) throws IOException {
        String savePath = storage.saveNewBookImage(file, slug);
        bookService.saveImage(savePath, slug);
        return "redirect:/books/" + slug;
    }

    @GetMapping("/download/{hash}")
    public ResponseEntity<ByteArrayResource> getBookFile(@PathVariable("hash") String hash) throws IOException {
        Path path = storage.getBookFilePath(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file path: " + path);

        MediaType mediaType = storage.getBookFileMime(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file mime type: " + mediaType);

        byte[] data = storage.getBookFileByteArray(hash);
        Logger.getLogger(this.getClass().getSimpleName()).info("book file data len: " + data.length);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                .contentType(mediaType)
                .contentLength(data.length)
                .body(new ByteArrayResource(data));
    }
}
