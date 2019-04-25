package com.urlshort.demo;

import com.urlshort.demo.error.ApiError;
import com.urlshort.demo.error.ApiInvalidUrlError;
import com.urlshort.demo.error.ApiOutdatedError;
import com.urlshort.demo.error.ApiUnableCreateShortUrlError;
import com.urlshort.demo.model.Url;
import com.urlshort.demo.service.UrlService;
import com.urlshort.demo.util.UrlDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class UrlController {
    @Autowired
    private UrlService urlService;

    @RequestMapping(value="/data/shorten", produces=MediaType.APPLICATION_JSON_VALUE)
    @POST
    @ResponseBody
    public ResponseEntity addUrl(@RequestParam("fullUrl") final String fullUrl) {
        if(!UrlDateUtil.isUrlValid(fullUrl)){
            return ResponseEntity.badRequest().body(new ApiError(HttpStatus.BAD_REQUEST, new ApiInvalidUrlError(fullUrl)));
        }
        Url url = urlService.add(fullUrl);
        if(url == null) {
            return ResponseEntity.badRequest().body(new ApiError(HttpStatus.BAD_REQUEST, new ApiUnableCreateShortUrlError(fullUrl)));
        }
        return ResponseEntity.accepted().body(url);
    }

    @RequestMapping(value="/data/expand/{shorturl:\\w+}", produces=MediaType.APPLICATION_JSON_VALUE)
    @GET
    @ResponseBody
    public ResponseEntity expandUrl(@PathVariable String shorturl){
        Url url = urlService.getByIndex(shorturl);
        if(url == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.accepted().body(url);
    }

    @RequestMapping(value="/{shorturl:\\w+}", produces=MediaType.APPLICATION_JSON_VALUE)
    @GET
    @ResponseBody
    public ResponseEntity getUrl(@PathVariable String shorturl){
        Url url = urlService.getByIndex(shorturl);
        if(url == null) {
            return ResponseEntity.notFound().build();
        }
        if(UrlDateUtil.isExpired(url.getCreated())){
            return ResponseEntity.badRequest().body(new ApiError(HttpStatus.BAD_REQUEST, new ApiOutdatedError(shorturl)));
        }

        //Validate URL
        URI uri = null;
        try {
            uri = new URI(url.getFullUrl());
        } catch (URISyntaxException e) {
            return ResponseEntity.badRequest().body(new ApiError(HttpStatus.BAD_REQUEST, new ApiInvalidUrlError(shorturl)));
        }

        // Redirect
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

}