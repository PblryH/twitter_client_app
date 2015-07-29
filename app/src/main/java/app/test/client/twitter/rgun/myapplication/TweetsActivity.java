package app.test.client.twitter.rgun.myapplication;

import android.app.ListActivity;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweets);
        tweetViewAdapter = new TweetViewAdapter(this);
        setListAdapter(tweetViewAdapter);
        loadTweets();
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
}
