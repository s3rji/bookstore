package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.dto.BookTo;
import com.example.MyBookShopApp.repository.BookRepository;
import com.example.MyBookShopApp.util.BookUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}
