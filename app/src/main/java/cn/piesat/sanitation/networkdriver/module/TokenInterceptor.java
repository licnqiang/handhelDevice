package cn.piesat.sanitation.networkdriver.module;


import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.networkdriver.upLoadFile.UploadListener;
import cn.piesat.sanitation.util.SpHelper;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
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

public class TokenInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();


        Request.Builder requestBuilder = originalRequest.newBuilder()
                .addHeader("Accept-Encoding", "UTF-8")
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader("Token", BaseApplication.getIns().getUserToken())//添加请求头信息，服务器进行token有效性验证
                .method(originalRequest.method(), originalRequest.body());
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
