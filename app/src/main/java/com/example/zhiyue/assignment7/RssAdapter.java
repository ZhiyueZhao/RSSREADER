package com.example.zhiyue.RSSpro;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ZhiYue on 3/8/2017.
 */

public class RssAdapter extends ArrayAdapter<RssItem> {
    private ArrayList<RssItem> rssItems;

    Context mContext;

    private int lastPosition = -1, desLength = 150;

    private SharedPreferences prefs;
    //View lookup cache
    private static class ViewHolder
    {
        TextView txtTitle, txtDescription, txtDate;
    }

    public RssAdapter(SharedPreferences preferences, Context context, ArrayList<RssItem> rssItems)
    {
        super(context, R.layout.rssadapter, rssItems);

        this.rssItems = rssItems;
        mContext = context;
        prefs = preferences;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Get the feed item for this position
        RssItem rssItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        // view lookup cache stored in tag
        ViewHolder viewHolder;

        final View result;

        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.rssadapter, parent, false);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.txtItemTitle);
            viewHolder.txtDescription = (TextView) convertView.findViewById(R.id.txtDescription);
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            result = convertView;

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;

        //check if shared prefs exist
        viewHolder.txtTitle.setText(rssItem.getTitle());
        viewHolder.txtDate.setText(rssItem.getPubDate());
        if(prefs.contains("DesLength")) {
            desLength = prefs.getInt("DesLength", 150);
            desLength = desLength>50 ? desLength : 50;
        }
        viewHolder.txtDescription.setText((Html.fromHtml(rssItem.getDescription(), null, null)).subSequence(4,desLength > (Html.fromHtml(rssItem.getDescription(), null, null)).length() ? (Html.fromHtml(rssItem.getDescription(), null, null)).length(): desLength));
        // Return the completed view to render on screen
        return result;
    }
}

