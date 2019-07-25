package cn.piesat.retrofitframe.netWork.module;

import java.util.concurrent.TimeUnit;

import cn.piesat.retrofitframe.BuildConfig;
import okhttp3.OkHttpClient;

/**
 * 类名称：OkHttp3Utils
 * 创建人：wangliteng
 * 创建时间：2017-11-09 14:57:11
 * 类描述：封装一个OkHttp3的获取类
 *
 */
public class OkHttp3Utils {

    private static OkHttpClient.Builder okHttpClient;

    /**
     * 获取OkHttpClient对象
     *
     * @return
     */
    public static OkHttpClient.Builder getOkHttpClient() {

        if (null == okHttpClient) {

            okHttpClient = new OkHttpClient.Builder();
            okHttpClient.connectTimeout(60, TimeUnit.SECONDS); //设置连接超时
            okHttpClient.readTimeout(60, TimeUnit.SECONDS);//设置读超时
            okHttpClient.writeTimeout(60, TimeUnit.SECONDS);//设置写超时
            okHttpClient.retryOnConnectionFailure(true);//是否自动重连

            //debug模式下打印
            if (BuildConfig.DEBUG) {
                //添加拦截
                okHttpClient.addInterceptor(new LoggingInterceptor());
            }
        }

        return okHttpClient;
    }
}
