package org.linphone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by macmini02 on 22/9/16.
 */

public class MainActivity extends Activity {

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymain);

        mHandler = new Handler();

        onServiceReady();

    }

    private void onServiceReady() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent().setClass(MainActivity.this, RegisterActivity.class).setData(getIntent().getData()));
                finish();
            }
        }, 2000);
    }
}
