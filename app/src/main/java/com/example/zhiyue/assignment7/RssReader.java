package com.example.zhiyue.RSSpro;

import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by ZhiYue on 2/17/2017.
 */

public class RssReader {
    private String rssUrl;

    public RssReader(String rssUrl)
    {
        this.rssUrl = rssUrl;
    }

    public List<RssItem> getItems() throws Exception{
        //create a SAX parser
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        //create instance of FreepHander
        FeedHandler feedHandler = new FeedHandler();
        //FeedHandler feedHandler = new FeedHandler(prefs);

        //parse the stream of data using our custom handler
        saxParser.parse(rssUrl, feedHandler);

        return feedHandler.getItems();
    }
}
