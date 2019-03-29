package com.qhh.myapplication.net;

import com.qhh.commombase.network.api.HttpHelper;
import com.qhh.commombase.network.exception.HttpHelperException;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * @author qinhaihang
 * @version $Rev$
 * @time 19-3-28 下午11:10
 * @des
 * @packgename com.qhh.myapplication.net
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 */
public class MovieLoader {

    private MovieService mMovieService;

    private static class SingletonHolder{
         private static final MovieLoader INSTANCE = new MovieLoader();
    }

    public static MovieLoader getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public MovieLoader() {
        try {
            mMovieService = HttpHelper.getInstance().create(MovieService.class);
        } catch (HttpHelperException e) {
            e.printStackTrace();
        }
    }

    public Observable<ResponseBody> getMovie(int start, int count){
        Observable<ResponseBody> moviesObservable = mMovieService.getMovies(start, count);
        return moviesObservable;
    }

}
