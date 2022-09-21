package com.example.editorial.entity;

import javax.persistence.*;

@Entity
@Table(name="editorial")
public class Editorial {

    private Integer id;
    private String name;
    private String web_site;

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeb_site() {
        return web_site;
    }

    public void setWeb_site(String web_site) {
        this.web_site = web_site;
    }
}
