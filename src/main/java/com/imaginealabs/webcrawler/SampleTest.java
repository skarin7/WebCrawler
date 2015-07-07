package com.imaginealabs.webcrawler;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.Properties;

/**
 * Created by shankark on 7/7/15.
 */
public class SampleTest {

    public static void main(String[] args) throws Exception
    {
        Resource resource = new ClassPathResource("/webcrawler.properties");
        Properties props = PropertiesLoaderUtils.loadProperties(resource);

    }

}
