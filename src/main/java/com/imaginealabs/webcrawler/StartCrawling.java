package com.imaginealabs.webcrawler;

import com.imaginealabs.webcrawler.threads.CrawlerThread;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class StartCrawling {

    final Logger logger= LoggerFactory.getLogger(StartCrawling.class);
    final static  String baseFolder="/home/shankark/Crawler/Test/";
    final static String BASE_URI="http://mail-archives.apache.org/mod_mbox/maven-users/";
    /*public  void saveContent()
    {

        // output here
        try {
                    //Open a URL Stream
        Connection.Response resultImageResponse = Jsoup.connect("http://mail-archives.apache.org/mod_mbox/maven-users/201507.mbox/thread?1")
                .ignoreContentType(true).execute();
            File file = new File(baseFolder);
//            AccessController.checkPermission(new FilePermission(baseFolder, "read,write"));

                if(!file.exists() ) {

                    file.mkdirs();
                }
         *//*   }
            else
            {
                logger.error(" Permission issue on the given system : Cannot read directory");
                throw new Exception("Unable to read Directory");

            }*//*
            FileOutputStream out = (new FileOutputStream(new File(file.getPath()+"/" + "testFile")));
            out.write(resultImageResponse.bodyAsBytes());  // resultImageResponse.body() is where the image's contents are.
            out.close();
        }
        catch(Exception e)
        {

            logger.error("Exception : " +e);
        }
    }*/


public  static void main(String[] args)

{
    final Connection conn;
    try
    {
        Resource resource = new ClassPathResource("/webcrawler.properties");
        Properties props = PropertiesLoaderUtils.loadProperties(resource);
        final       String baseFolder;
        final   String BASE_URI=props.getProperty("remote.uri");
        final   Integer timeOut=Integer.parseInt(props.getProperty("remote.connection.timeout"));

        String baseLocation=props.getProperty("base.download.location");
        if(StringUtils.isEmpty(baseLocation))
        {
            baseFolder="/tmp";
        }
        else
        {
            baseFolder=baseLocation;
        }
        Instant startTime=Instant.now();
         conn=Jsoup.connect(BASE_URI).timeout(timeOut);
       List<Element> elements= conn.get().select("a[href]");
        elements.stream().filter(n -> n.attributes().get("href").endsWith("thread")).collect(Collectors.toList()).stream().
                forEach(n -> new Thread(new CrawlerThread(BASE_URI, baseFolder, n.attributes().get("href"),conn)).start());
  /*      StartCrawling sc =new StartCrawling();
        sc.saveContent();*/
        Instant endTime=Instant.now();
        Long totalTimeTook= Duration.between(startTime,endTime).getSeconds();
        System.out.println("Crawler took " + totalTimeTook + " Second(s)");
    }

    catch(Exception e)
    {
        e.printStackTrace();
    }

    finally {
    }
}

}