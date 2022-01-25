package com.example.MyBookShopApp.security;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtBlacklistRepository extends JpaRepository<JwtBlacklist, Integer> {
    JwtBlacklist findByToken(String token);
}
