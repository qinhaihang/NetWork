package com.qhh.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qhh.commombase.network.HttpHelper;
import com.qhh.commombase.network.exception.HttpHelperException;
import com.qhh.myapplication.net.MovieService;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            MovieService movieService = HttpHelper.getInstance().create(MovieService.class);
            Observable<ResponseBody> moviesObservable = movieService.getMovies(0, 20);
        } catch (HttpHelperException e) {
            e.printStackTrace();
        }
    }
}
