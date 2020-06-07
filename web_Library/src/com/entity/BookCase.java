package com.entity;

import java.util.List;

/**
 * 图书类别的封装
 */
public class BookCase {
    private Integer id;
    private String name;
    //private List<Book> books;//在书类中是一对多，用列表体现，但是用不到

    public BookCase(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
