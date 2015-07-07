package com.imaginealabs.webcrawler.threads;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by shankark on 7/7/15.
 */
public class CrawlerThread implements Runnable {

     org.slf4j.Logger logger= LoggerFactory.getLogger(this.getClass());
    private String baseUrl;
    private String baseDownloadPath;
    private String appendUrl;
    private Connection conn;

    public CrawlerThread(String baseUrl, String baseDownloadPath,String appendUrl,Connection conn) {
        this.baseUrl = baseUrl;
        this.baseDownloadPath=baseDownloadPath;
        this.appendUrl=appendUrl;
        this.conn=conn;
    }

    @Override
    public void run() {

        try {
        Connection.Response result = conn.url(baseUrl+appendUrl)
                .ignoreContentType(true).execute();
            File file = new File(baseDownloadPath);
                if(!file.exists() ) {
                    file.mkdirs();
                }
            FileOutputStream out = (new FileOutputStream(new File(file.getPath()+"/" + appendUrl.split("/")[0])));
            out.write(result.bodyAsBytes());  // result.body() is where the image's contents are.
            out.close();
        }
        catch(Exception e)
        {

            logger.error("Exception : " +e);
        }


    }
}
