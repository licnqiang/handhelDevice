package cn.piesat.retrofitframe.netWork.module;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import cn.piesat.retrofitframe.netWork.upLoadFile.UploadListener;
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


    private UploadListener mUploadListener;

    public LoggingInterceptor(UploadListener uploadListener) {
        mUploadListener = uploadListener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        if(null == request.body()){
            return chain.proceed(request);
        }

        //监听请求进度
        if(null!=mUploadListener){
            Request build = request.newBuilder()
                    .method(request.method(),
                            new CountingRequestBody(request.body(),
                                    mUploadListener))
                    .build();
        }

        Response response = chain.proceed(request);
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

        return  response;
    }
}
