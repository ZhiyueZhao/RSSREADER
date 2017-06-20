package com.example.zhiyue.RSSpro;

import java.io.Serializable;

/**
 * Created by ZhiYue on 2/17/2017.
 */

public class RssItem implements Serializable {
    private String title;
    private String url;
    private String description;
    private String pubDate;

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public RssItem() {
        title = "";
        url = "";
        description = "";
    }

    public RssItem(String title, String link, String description) {
        title = title;
        url = link;
        description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return title;
    }
}
