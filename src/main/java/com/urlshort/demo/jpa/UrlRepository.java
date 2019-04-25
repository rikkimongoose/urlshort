package com.urlshort.demo.jpa;

import com.urlshort.demo.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface UrlRepository extends JpaRepository<Url, String> {
    @Query("select u from Url u where u.fullUrl = :fullUrl")
    Url getByFullUrl(@Param("fullUrl") String fullUrl);
}
