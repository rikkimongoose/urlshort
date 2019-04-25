package com.urlshort.demo.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "url")
public class Url {
    @Id
    private String id;
    @Column
    private String fullUrl;
    @Column
    private LocalDateTime created;

    @PrePersist
    void createdAt() {
        this.created = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.created = LocalDateTime.now();
    }

    public Url() {}

    public Url(String shortUrl, String fullUrl){
        super();
        this.id = shortUrl;
        this.fullUrl = fullUrl;
    }

    public Url(String shortUrl, String fullUrl, LocalDateTime date){
        this(shortUrl, fullUrl);
        this.created = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
