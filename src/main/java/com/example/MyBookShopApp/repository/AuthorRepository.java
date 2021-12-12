package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
