package com.example.zhiyue.RSSpro;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends MenuActivity {
    private String FEED_URL = "http://www.cbc.ca/cmlink/rss-sports-soccer";//http://www.cbc.ca/cmlink/rss-canada
    // A reference to this activity
    private ArrayList<RssItem> results;
    private ListView itcItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtError = (TextView)findViewById(R.id.txtError);
        itcItems = (ListView)findViewById(R.id.lView1);

        Intent intent = getIntent();
        String sUri = intent.getStringExtra("uri");

        FEED_URL = (TextUtils.isEmpty(sUri)) ? FEED_URL : sUri;

        txtError.setText("Internet connected!");
        itcItems.setAdapter(null);

        if(isNetworkAvailable())
        {
            //execute new thread to retrieve feeds
            AsyncFeeds aFeeds = new AsyncFeeds();
            aFeeds.execute(FEED_URL);
        }
        else
        {
            txtError.setText(NO_INTERNET_CONNECTION);
        }

    }

    private class AsyncFeeds extends AsyncTask<String, Void, List<RssItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("George", "onPreExecute");
        }

        //does not have access to the UI thread
        @Override
        protected List<RssItem> doInBackground(String... url) {
            Log.d("George", "doInBackground");

            try {
                //parse the stream of data using handler
                //SAXParserFactory.newInstance().newSAXParser().parse(url, new FreepHandler());
                RssReader rssReader = new RssReader(url[0]);

                return rssReader.getItems();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            catch (Exception e){
                Log.e("RssAct 1", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<RssItem> result) {

            //check if shared prefs exist
            if(prefs.contains("SortAscending"))
            {
                if(prefs.getBoolean("SortAscending", true))
                {
                    Collections.reverse(result);
                }
            }
            results = new ArrayList<RssItem>();
            if(prefs.contains("NumToShow"))
            {
                int show = prefs.getInt("NumToShow", 0);
                if(show <= 0)
                {
                    results = (ArrayList)result;
                }
                else
                {
                    show = show>result.size()? result.size() : show;

                    for (int i = 0; i<show; i++)
                    {
                        results.add(result.get(i));
                    }
                }
            }

            ArrayAdapter<RssItem> adapter = new RssAdapter(prefs,getApplicationContext(), results);
            itcItems.setAdapter(adapter);
            itcItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    //get item
                    RssItem item = results.get(position);

                    //create an intent to pass item
                    Intent itemIntent = new Intent(getApplicationContext(), Main2Activity.class);

                    //store item
                    itemIntent.putExtra("RssItem", item);

                    //start activity
                    startActivity(itemIntent);
                }
            });
        }
    }
}
