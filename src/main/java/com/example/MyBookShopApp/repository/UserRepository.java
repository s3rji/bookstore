package com.example.MyBookShopApp.repository;

import com.example.MyBookShopApp.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
