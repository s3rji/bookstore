package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.dto.BookTo;
import com.example.MyBookShopApp.repository.BookRepository;
import com.example.MyBookShopApp.util.BookUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;

    @Autowired
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<BookTo> getAll() {
        return BookUtil.getTos(repository.findAll());
    }

    public List<BookTo> getPageOfRecommended(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return BookUtil.getTos(repository.findAll(nextPage).getContent());
    }

    public List<BookTo> getPageOfRecent(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit, Sort.by("pubDate").descending());
        return BookUtil.getTos(repository.findByPubDateBetween(
                LocalDate.of(2021, 7, 1),
                LocalDate.now(),
                nextPage
        ).getContent());
    }

    public List<BookTo> getPageOfPopular(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return BookUtil.getTosPopularSorting(repository.findAll(nextPage).getContent());
    }
}
