package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.model.book.file.BookFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookFileRepository extends JpaRepository<BookFile, Integer> {

    public BookFile findByHash(String hash);
}
