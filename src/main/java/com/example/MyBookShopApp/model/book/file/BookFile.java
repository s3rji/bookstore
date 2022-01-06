package com.example.MyBookShopApp.model.book.file;

import com.example.MyBookShopApp.model.book.Book;

import javax.persistence.*;

@Entity
@Table(name = "book_file")
public class BookFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String hash;

    @Column(columnDefinition = "INT NOT NULL")
    private Integer typeId;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String path;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    public String getBookFileExtensionString(){
        return BookFileType.getExtensionStringByTypeID(typeId);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
