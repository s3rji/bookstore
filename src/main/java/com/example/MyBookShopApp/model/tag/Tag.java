package com.example.MyBookShopApp.model.tag;

import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String description;

    @Formula("(SELECT COUNT(*) FROM book2tag b WHERE b.tag_id=id)")
    private Integer count;

    public String font() {
        if (count >= 0 && count < 5) return "Tag_xs";
        if (count >= 5 && count < 10) return "Tag_sm";
        if (count >= 10 && count < 15) return "Tag_md";
        return "Tag_lg";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
