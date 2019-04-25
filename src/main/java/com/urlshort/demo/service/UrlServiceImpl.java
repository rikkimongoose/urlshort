package com.urlshort.demo.service;

import com.urlshort.demo.jpa.UrlRepository;
import com.urlshort.demo.model.Url;
import com.urlshort.demo.util.UrlCodeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service("urlService")
public class UrlServiceImpl implements UrlService {
    private static final String PROP_INDEX_LENGTH = "shorturl.url.length";

    static Logger logger = Logger.getLogger(UrlServiceImpl.class);

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private Environment env;

    /**
     * @inheritDoc
     */
    @Override
    public Url getByIndex(String index) {
        Optional<Url> url = urlRepository.findById(index);
        return url.orElse(null);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Url add(String fullUrl) {
        //Если такой URL уже существует
        Url urlWithSameFullUrl = urlRepository.getByFullUrl(fullUrl);
        if(urlWithSameFullUrl != null) {
            urlWithSameFullUrl.setCreated(LocalDateTime.MAX);
            urlRepository.saveAndFlush(urlWithSameFullUrl);

            logger.debug(String.format("Short URL index '%s' for URL: %s is updated.", urlWithSameFullUrl.getId(), urlWithSameFullUrl.getFullUrl()));
            return urlWithSameFullUrl;
        }

        int indexLength = loadIndexLength();
        String index = UrlCodeUtil.generateCode(indexLength);
        //На случай коллизии
        Optional<Url> urlExisting = urlRepository.findById(index);
        while(urlExisting.isPresent()) {
            index = UrlCodeUtil.generateCode(indexLength);
            urlExisting = urlRepository.findById(index);
        }
        Url url = new Url(index, fullUrl);
        urlRepository.saveAndFlush(url);
        logger.debug(String.format("Short URL index '%s' for URL: %s is created.", url.getId(), url.getFullUrl()));
        return url;
    }

    private int loadIndexLength(){
        String indexLengthStr = env.getProperty(PROP_INDEX_LENGTH);
        int indexLength;
        try {
            indexLength = Integer.parseInt(indexLengthStr);
        } catch (NumberFormatException nfe) {
            indexLength = UrlCodeUtil.DEFAULT_SHORT_URL_LENGTH;
            logger.error(String.format("'%s' is not valid short url length.", indexLengthStr));
        }
        return indexLength;
    }
}
