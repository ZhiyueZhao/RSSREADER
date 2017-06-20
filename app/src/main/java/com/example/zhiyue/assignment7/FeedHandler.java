package com.example.zhiyue.RSSpro;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.max;

/**
 * Created by ZhiYue on 2/17/2017.
 */

public class FeedHandler extends DefaultHandler {

    private boolean inTitle, inDesc, inPubDate, inLink, inItem;

    //to keep track of elements iterated through
    private int elementsDone = 0;


    List<RssItem> rssItems;
    RssItem currentItem;

    //for efficiently building strings
    protected StringBuilder stringBuilder;

    public FeedHandler(){
        rssItems = new ArrayList<RssItem>();
    }

    public List<RssItem> getItems(){
        return rssItems;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        super.startElement(uri, localName, qName, attributes);

        if(elementsDone == 0 && qName.equals("item"))
        {
            currentItem = new RssItem();

            inItem = true;
        }

        if(qName.equals("title") && inItem)
        {
            inTitle = true;
            stringBuilder = new StringBuilder();
            elementsDone++;
        }
        else if(qName.equals("description") && inItem)
        {
            inDesc = true;
            stringBuilder = new StringBuilder();
            elementsDone++;
        }
        else if(qName.equals("pubDate") && inItem)
        {
            inPubDate = true;
            stringBuilder = new StringBuilder();
            elementsDone++;
        }
        else if(qName.equals("link") && inItem)
        {
            inLink = true;
            stringBuilder = new StringBuilder();
            elementsDone++;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        super.endElement(uri, localName, qName);

        if(qName.equals("title") && inItem)
        {
            inTitle = false;
            currentItem.setTitle(stringBuilder.toString());
        }
        else if(qName.equals("description") && inItem)
        {
            inDesc = false;
            currentItem.setDescription(stringBuilder.toString());
        }
        else if(qName.equals("link") && inItem)
        {
            inLink = false;
            currentItem.setLink(stringBuilder.toString());
        }
        else if(qName.equals("pubDate") && inItem)
        {
            inPubDate = false;
            currentItem.setPubDate(stringBuilder.toString());
        }

        if(elementsDone == 4 && inItem)
        {
            rssItems.add(currentItem);

            elementsDone = 0;

            inItem = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        super.characters(ch, start, length);

        if(inTitle || inDesc || inPubDate || inLink)
        {
            if(length > max && !inLink)
            {
                stringBuilder.append(ch, start, max - 3);
                stringBuilder.append("...");
            }
            else
            {
                stringBuilder.append(ch, start, length);
            }
        }
    }
}
