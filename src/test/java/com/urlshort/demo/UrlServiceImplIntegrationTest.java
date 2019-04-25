package com.urlshort.demo;

import com.urlshort.demo.jpa.UrlRepository;
import com.urlshort.demo.model.Url;
import com.urlshort.demo.service.UrlService;
import com.urlshort.demo.service.UrlServiceImpl;
import com.urlshort.demo.util.UrlDateUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
public class UrlServiceImplIntegrationTest {

    @TestConfiguration
    static class UrlServiceImplIntegrationTestContextConfiguration {

        @Bean
        public UrlService urlService() {
            return new UrlServiceImpl();
        }
    }


    @Autowired
    private UrlService urlService;

    @MockBean
    private UrlRepository urlRepository;

    @Before
    public void setUp() {
        Url url = new Url("cOS4fLl", "http://oracle.com", LocalDateTime.now());
        Url urlOld = new Url("vcegv4vw", "http://java.com", LocalDateTime.now().minusMinutes(15));

        Mockito.when(urlRepository.getOne(url.getId()))
                .thenReturn(url);
        Mockito.when(urlRepository.getByFullUrl(url.getFullUrl()))
                .thenReturn(url);

        Mockito.when(urlRepository.getOne(urlOld.getId()))
                .thenReturn(urlOld);
        Mockito.when(urlRepository.getByFullUrl(urlOld.getFullUrl()))
                .thenReturn(urlOld);
    }

    @Test
    public void whenValidName_thenUrlShouldBeFound() {
        String name = "cOS4fLl";
        Url found = urlService.getByIndex(name);

        Assert.assertNotNull(found);
        Assert.assertEquals(found.getFullUrl(), "http://oracle.com");
    }

    @Test
    public void whenValidName_thenUrlShouldNotBeFound() {
        String name = "nonono";
        Url found = urlService.getByIndex(name);

        Assert.assertNull(found);
    }


    @Test
    public void whenValidName_thenOutdatedUrlShouldNotBeFound() {
        String name = "vcegv4vw";
        Url found = urlService.getByIndex(name);

        Assert.assertNotNull(found);
        Assert.assertEquals(found.getFullUrl(), "http://java.com");
        Assert.assertTrue(UrlDateUtil.isExpired(found.getCreated()));
    }
}
