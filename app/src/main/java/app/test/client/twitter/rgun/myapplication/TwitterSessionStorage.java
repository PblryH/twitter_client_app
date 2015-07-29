package app.test.client.twitter.rgun.myapplication;

import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterSession;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by rgun on 29.07.15.
 */
public class TwitterSessionStorage {

    private static TwitterSessionStorage ourInstance;
    @Setter @Getter private Result<TwitterSession> result;

    private TwitterSessionStorage() {}

    public synchronized static TwitterSessionStorage getInstance() {
        if (ourInstance == null) {
            ourInstance = new TwitterSessionStorage();
        }
        return ourInstance;
    }
}
