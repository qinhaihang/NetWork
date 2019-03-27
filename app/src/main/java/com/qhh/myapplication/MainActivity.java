package com.qhh.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        NoConvertObserver noConvertObserver = new NoConvertObserver() {
//
//        };
//
//        try {
//            SubscribeHelper.getInstance().subscribeOb(
//                    HttpHelper.getInstance().create(MovieService.class),
//                    noConvertObserver
//            );
//        } catch (HttpHelperException e) {
//            e.printStackTrace();
//        }

    }
}
