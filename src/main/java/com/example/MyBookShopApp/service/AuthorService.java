package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.model.author.Author;
import com.example.MyBookShopApp.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorRepository repository;

    @Autowired
    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public Map<String, List<Author>> getAll() {
        return repository.findAll()
                .stream()
                .collect(Collectors.groupingBy((Author a) -> {
                    return a.getName().substring(0, 1);
                }));
    }
}
