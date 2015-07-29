package app.test.client.twitter.rgun.myapplication;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.TweetViewAdapter;

import java.util.List;


public class TweetsActivity extends ListActivity {

    private TweetViewAdapter tweetViewAdapter;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweets);
        tweetViewAdapter = new TweetViewAdapter(this);
        setListAdapter(tweetViewAdapter);
        loadTweets();
        editText = (EditText) findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.tweet);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTweet(editText.getText().toString());
            }
        });
    }

    public void loadTweets() {
        StatusesService service = Twitter.getInstance().getApiClient().getStatusesService();
        service.homeTimeline(null, null, null, null, null, null, null, new Callback<List<Tweet>>() {
                    @Override
                    public void success(Result<List<Tweet>> result) {
                        tweetViewAdapter.setTweets(result.data);
                    }

                    @Override
                    public void failure(TwitterException error) {
                        Toast.makeText(TweetsActivity.this, getString(R.string.tweets_receive_error), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    private void sendTweet(String text) {
        StatusesService statusesService = Twitter.getInstance().getApiClient().getStatusesService();
        statusesService.update(text, null, null, null, null, null, null, null, new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> tweetResult) {
                Toast.makeText(TweetsActivity.this, getString(R.string.new_tweet_success), Toast.LENGTH_LONG).show();
                editText.getText().clear();
                loadTweets();
            }

            @Override
            public void failure(TwitterException e) {
                Toast.makeText(TweetsActivity.this, getString(R.string.new_tweet_failure), Toast.LENGTH_LONG).show();
            }
        });
    }
}
