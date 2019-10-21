package cn.piesat.sanitation.networkdriver.module;

import cn.piesat.sanitation.constant.IPConfig;
import cn.piesat.sanitation.networkdriver.upLoadFile.UploadListener;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 类名称：RetrofitUtils
 * 创建人：lq
 * 创建时间：2016-5-18 14:57:11
 * 类描述：封装一个retrofit集成0kHttp3的抽象基类
 */
public abstract class RetrofitUtils {


    private Retrofit mRetrofit;
    private Retrofit outSourceRetrofit;
    private OkHttpClient.Builder mOkHttpClient;

    /**
     * 获取当前Retrofit对象
     *
     * @return
     */
    protected Retrofit getRetrofit(boolean switchService, UploadListener uploadListener) {
        return switchService ? getMyRetrofit(uploadListener) : getOutSourceRetrofit(uploadListener);
    }


    /**
     * 获取外协服务器Retrofit对象
     *
     * @return
     */
    protected Retrofit getOutSourceRetrofit(UploadListener uploadListener) {

        if (null == outSourceRetrofit) {

            if (null == mOkHttpClient) {
                mOkHttpClient = OkHttp3Utils.getOkHttpClient(uploadListener);
            }

            //Retrofit2后使用build设计模式
            outSourceRetrofit = new Retrofit.Builder()
                    //设置服务器路径
                    .baseUrl(IPConfig.getOutSourceURLPreFix())
                    //添加转化库，默认是Gson
                    .addConverterFactory(GsonConverterFactory.create())
                    //添加回调库，采用RxJava
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    //设置使用okhttp网络请求
                    .client(mOkHttpClient.build())
                    .build();
        }

        return outSourceRetrofit;
    }


    /**
     * 获取自己服务器Retrofit对象
     *
     * @return
     */
    protected Retrofit getMyRetrofit(UploadListener uploadListener) {

        if (null == mRetrofit) {

            if (null == mOkHttpClient) {
                mOkHttpClient = OkHttp3Utils.getOkHttpClient(uploadListener);
            }

            //Retrofit2后使用build设计模式
            mRetrofit = new Retrofit.Builder()
                    //设置服务器路径
                    .baseUrl(IPConfig.getOutSourceURLPreFix())
                    //添加转化库，默认是Gson
                    .addConverterFactory(GsonConverterFactory.create())
                    //添加回调库，采用RxJava
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    //设置使用okhttp网络请求
                    .client(mOkHttpClient.build())
                    .build();
        }

        return mRetrofit;
    }


}
