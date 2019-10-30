package cn.piesat.sanitation.networkdriver.module;

import java.util.concurrent.TimeUnit;

import cn.piesat.sanitation.networkdriver.upLoadFile.UploadListener;
import okhttp3.OkHttpClient;

/**
 * 类名称：OkHttp3Utils
 * 创建时间：2017-11-09 14:57:11
 * 类描述：封装一个OkHttp3的获取类
 */
public class OkHttp3Utils {

    private static OkHttpClient.Builder okHttpClient;

    /**
     * 获取OkHttpClient对象
     *
     * @return
     */
    public static OkHttpClient.Builder getOkHttpClient(UploadListener uploadListener) {

        if (null == okHttpClient) {
            okHttpClient = new OkHttpClient.Builder();
            okHttpClient.connectTimeout(60, TimeUnit.SECONDS); //设置连接超时
            okHttpClient.readTimeout(60, TimeUnit.SECONDS);//设置读超时
            okHttpClient.writeTimeout(60, TimeUnit.SECONDS);//设置写超时
            okHttpClient.retryOnConnectionFailure(true);//是否自动重连
            okHttpClient.addInterceptor(new TokenInterceptor());//添加token拦截
            okHttpClient.addInterceptor(new LoggingInterceptor(uploadListener));//获取请求进度拦截
        }

        return okHttpClient;
    }
}
