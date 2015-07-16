package com.imaginealabs.webcrawler.threads;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by shankark on 7/7/15.
 */
public class CrawlerThread implements Runnable {
Logger logger= LoggerFactory.getLogger(this.getClass());
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
        FileOutputStream out=null;


        File file = new File(baseDownloadPath+appendUrl.split("/")[0].substring(0,5)+"/" +appendUrl.split("/")[0].substring(5,6));
            if(!file.exists() ) {
                file.mkdirs();
            }

        try {
        //Connection.Response result = conn.url(baseUrl+appendUrl).ignoreContentType(true).execute();
            List<Element> elements= conn.get().select("a[href]");
            for (Element element : elements) {

                        String msgSub=element.select("#msglist > tbody > tr > td:eq(1)").(".subject");




            }






             out = (new FileOutputStream(new File(file.getPath()+"/" + appendUrl.split("/")[0])));
            out.write(result.bodyAsBytes());

        }
        catch(Exception e)
        {

            logger.error("Exception : " +e);
        }

        finally {
            if(out!=null)
            {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
