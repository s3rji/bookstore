package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.model.genre.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    @Query("SELECT g FROM Genre g WHERE g.parentId IS NULL ORDER BY g.countBooks DESC")
    List<Genre> findParentsSorting();

    Genre findBySlug(String slug);
}
