package com.qhh.myapplication.net;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author qinhaihang_vendor
 * @version $Rev$
 * @time 2019/3/27 10:56
 * @des
 * @packgename com.qhh.myapplication.net
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 */
public interface MovieService {

    @GET("/v2/movie/top250")
    Observable<ResponseBody> getMovies(@Query("start") int start, @Query("count") int count);
}
