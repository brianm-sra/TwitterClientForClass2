package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // getSupportActionBar().setDisplayShowHomeEnabled(true);
        // getSupportActionBar().setLogo(android.R.mipmap.sym_def_app_icon);  //.ic_launcher);
        // getSupportActionBar().setDisplayUseLogoEnabled(true);

        // Find the ListView
        lvTweets = (ListView) findViewById(R.id.lvTweets);

        // Create the ArrayList (data source)
        tweets = new ArrayList<Tweet>();

        // Construct the adapter from data source
        aTweets = new TweetsArrayAdapter(this, tweets);

        // Connect adapter to ListView
        lvTweets.setAdapter(aTweets);

        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    // Send an API request to get the timeline json
    // Fill the ListView by creating the Tweet objects from the JSON
    private void populateTimeline() {

        client.getHomeTimeline(new JsonHttpResponseHandler() {
            // SUCCESS

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG", json.toString());

                // deserialize JSON

                // create models

                // load the model data into ListView
                ArrayList<Tweet> tweets = Tweet.fromJSONArray(json);
                Log.d("DEBUG", "tweets.size() = " + tweets.size());
                aTweets.addAll(tweets);
                Log.d("DEBUG", aTweets.toString());
            }

            // FAILURE

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }

    public void onComposeAction(MenuItem item) {

        Intent composeTweetIntent = new Intent(this, ComposeTweetActivity.class);
        startActivity(composeTweetIntent);
    }
}
