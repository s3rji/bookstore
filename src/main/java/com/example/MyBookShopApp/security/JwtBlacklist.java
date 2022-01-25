package com.example.MyBookShopApp.security;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "jwt_blacklist")
public class JwtBlacklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT NOT NULL", unique = true)
    private String token;

    @Column(columnDefinition = "TIMESTAMP NOT NULL")
    private Date expiringDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiringDate() {
        return expiringDate;
    }

    public void setExpiringDate(Date expiringDate) {
        this.expiringDate = expiringDate;
    }
}
