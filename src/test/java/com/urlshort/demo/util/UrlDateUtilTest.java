package com.urlshort.demo.util;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class UrlDateUtilTest {

    @Test
    public void isExpiredTrue() {
        LocalDateTime nowMinus15 = LocalDateTime.now().minusMinutes(15);

        Assert.assertTrue(UrlDateUtil.isExpired(nowMinus15));
    }


    @Test
    public void isExpiredFalse() {
        LocalDateTime nowMinus5 = LocalDateTime.now().minusMinutes(5);

        Assert.assertFalse(UrlDateUtil.isExpired(nowMinus5));
    }

    @Test
    public void isUrlValid() {
        String validUrl = "http://microsoft.com";
        String invalidUrl = "oeufhwurvnvivn8djc ajvdf";

        Assert.assertTrue(UrlDateUtil.isUrlValid(validUrl));
        Assert.assertFalse(UrlDateUtil.isUrlValid(invalidUrl));
    }
}