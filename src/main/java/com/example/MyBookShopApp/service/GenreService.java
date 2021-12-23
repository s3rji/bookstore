package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.model.genre.Genre;
import com.example.MyBookShopApp.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepository repository;

    @Autowired
    public GenreService(GenreRepository repository) {
        this.repository = repository;
    }

    public List<Genre> getParents() {
        return repository.findParentsSorting();
    }

    public Genre getBySlug(String slug) {
        return repository.findBySlug(slug);
    }
}
