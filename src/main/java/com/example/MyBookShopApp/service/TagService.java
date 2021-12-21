package com.example.MyBookShopApp.service;

import com.example.MyBookShopApp.model.tag.Tag;
import com.example.MyBookShopApp.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository repository;

    @Autowired
    public TagService(TagRepository repository) {
        this.repository = repository;
    }

    public List<Tag> getAll() {
        return repository.findAll();
    }

    public Tag getById(Integer tagId) {
        return repository.findById(tagId).orElse(null);
    }
}
