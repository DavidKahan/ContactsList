package com.sample.david.contactslist.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.sample.david.contactslist.R;
import com.sample.david.contactslist.presenter.SplashPresenter;


/**
 * Created by david on 08/07/2017.
 */

public class SplashActivity extends Activity {

    SplashPresenter mSplashPresenter = new SplashPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread tLoader = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //In here I prevent the call to the API if local DB already exists
                    if (!mSplashPresenter.doesDatabaseExist(SplashActivity.this)){
                        mSplashPresenter.getContactsToLocal();
                    }
                    Thread.sleep(2000);
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        tLoader.start();
    }
}
