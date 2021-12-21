package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.model.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
