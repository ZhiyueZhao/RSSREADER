package com.example.zhiyue.RSSpro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

public class Main2Activity extends MenuActivity {

    private TextView txtTitle, txtDescription, txtDate;
    private RssItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        item = (RssItem)getIntent().getSerializableExtra("RssItem");

        txtTitle = (TextView)findViewById(R.id.txtItemTitle);
        txtDescription = (TextView)findViewById(R.id.txtItemContent);
        txtDate = (TextView)findViewById(R.id.txtItemDate);

        //check if shared prefs exist
        if(prefs.contains("FontSize"))
        {
            int size = prefs.getInt("FontSize", 20);

            size = size < 1 ? 20 : size;

            txtDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        }

        if(prefs.contains("ShowTitle") &&
                prefs.contains("ShowDate") &&
                prefs.contains("ShowDesc"))
        {
            if(prefs.getBoolean("ShowTitle", true))
            {
                txtTitle.setText(item.getTitle());
            }

            if(prefs.getBoolean("ShowDesc", true))
            {
                txtDescription.setText((Html.fromHtml(item.getDescription(), null, null)).subSequence(4,(Html.fromHtml(item.getDescription(), null, null)).length()));
            }

            if(prefs.getBoolean("ShowDate", true))
            {
                txtDate.setText(item.getPubDate());
            }
            else
            {
                txtDate.setText("");
            }
        }

        txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLink().trim()));
                startActivity(browserIntent);
            }
        });
    }
}
