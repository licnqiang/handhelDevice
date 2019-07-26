package cn.piesat.retrofitframe.netWork.module;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import cn.piesat.retrofitframe.util.Log;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 网络拦截器
 * 主要功能  打印网络请求的参数 url
 * 是否响应
 * 响应的json数据和url
 */

public class LoggingInterceptor implements Interceptor {
    private final String TAG = getClass().getName();
    private final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        RequestBody requestBody = request.body();
        String body = null;
        if (null != requestBody) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (null != contentType) {
                charset = contentType.charset(UTF8);
            }
            body = buffer.readString(charset);
        }


        long statNs = System.nanoTime(); //计时
        Response response = chain.proceed(request);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - statNs);  //响应时差
        ResponseBody responseBody = response.body();
        String rBady = null;
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);   //缓存body体
        Buffer buffer = source.buffer();

        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (null != contentType) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {
                e.printStackTrace();
            }
        }
        rBady = buffer.clone().readString(charset);

        Log.e("http", "Http--响应==="+ rBady);

        return response;
    }
}
