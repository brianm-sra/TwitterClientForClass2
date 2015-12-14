package com.codepath.apps.mysimpletweets;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import org.apache.http.Header;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;

import com.codepath.apps.mysimpletweets.R;

public class ComposeTweetActivity extends AppCompatActivity {

    TextView editText;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        client = new TwitterClient(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editText = (TextView) findViewById(R.id.editText);
    }

    public void sendNewTweet(View view) {
        String tweetStr = "" + editText.getText();
        Log.d("DEBUG", "You entered: \"" + tweetStr + "\"");

        client.postNewTweet(
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                    }
                },
                tweetStr
        );

        this.finish();
    }
}
