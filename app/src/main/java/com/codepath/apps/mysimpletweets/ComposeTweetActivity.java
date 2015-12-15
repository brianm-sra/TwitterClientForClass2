package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import com.codepath.apps.mysimpletweets.R;

import static android.widget.Toast.LENGTH_LONG;

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

        final String tweetStr = "" + editText.getText();
        Log.d("DEBUG", "You entered: \"" + tweetStr + "\"");


        client.postNewTweet(
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Tweet Successful!", Toast.LENGTH_LONG).show();
                        Log.d("DEBUG", "response = " + response.toString());
                        JSONObject userJson = null;
                        Long tweetId  = null;
                        try {
                            userJson = response.getJSONObject("user");
                            tweetId = response.getLong("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Intent data = new Intent();
                        if (userJson != null) {
                            User user = User.fromJSON(userJson);
                            data.putExtra("userName", user.getName());
                            data.putExtra("userScreenName", user.getScreenName());
                            data.putExtra("userId", user.getUid());
                        }


                        data.putExtra("tweet", tweetStr);
                        if (tweetId != null) {
                            data.putExtra("tweetId", tweetId);

                        }

                        setResult(RESULT_OK, data);
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
