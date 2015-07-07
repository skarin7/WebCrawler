package com.imaginea.labs.webcrawler;

import com.imaginea.labs.webcrawler.com.imaginea.labs.threads.CrawlerThread;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilePermission;
import java.security.AccessController;
import java.util.List;
import java.util.function.Predicate;
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
    final   String baseFolder="/home/shankark/Crawler/Test/";
    final  String BASE_URI="http://mail-archives.apache.org/mod_mbox/maven-users/";
    final Connection conn;
    try
    {
         conn=Jsoup.connect("http://mail-archives.apache.org/mod_mbox/maven-users/").timeout(10 * 1000);
       List<Element> elements= conn.get().select("a[href]");
      //  String data= Jsoup.connect("http://mail-archives.apache.org/mod_mbox/maven-users/").timeout(10 * 1000).get().data();
        elements.stream().filter(n -> n.attributes().get("href").endsWith("thread")).collect(Collectors.toList()).stream().
                forEach(n -> new Thread(new CrawlerThread(BASE_URI, baseFolder, n.attributes().get("href"),conn)).start());
  /*      StartCrawling sc =new StartCrawling();
        sc.saveContent();*/
    }

    catch(Exception e)
    {
        e.printStackTrace();
    }

    finally {
    }
}

}