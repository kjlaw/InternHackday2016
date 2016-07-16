package com.fantasticthree.funapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

public class LinkedInActivity extends AppCompatActivity {
    private final static String TAG = LinkedInActivity.class.getSimpleName();

    public static void launchActivity(final Activity activity) {
        Intent launchIntent = new Intent(activity, LinkedInActivity.class);
        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.startActivity(launchIntent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linked_in);
        AuthListener authListener = new AuthListener() {
            @Override
            public void onAuthSuccess() {
                Log.d(TAG, "Login Success");
                finish();
            }

            @Override
            public void onAuthError(LIAuthError error) {
                Log.d(TAG, "Authentication Error");
            }
        };
        Scope scope = Scope.build(Scope.R_FULLPROFILE);
        LISessionManager.getInstance(LinkedInActivity.this).init(LinkedInActivity.this, scope, authListener, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this,
                requestCode, resultCode, data);
    }
}
