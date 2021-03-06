package cn.piesat.sanitation.networkdriver.common;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.common.netchange.event.TokenLoseEvent;
import cn.piesat.sanitation.constant.IPConfig;
import cn.piesat.sanitation.networkdriver.module.NetApi;
import cn.piesat.sanitation.networkdriver.module.RetrofitUtils;
import cn.piesat.sanitation.util.FileUtil;
import cn.piesat.sanitation.util.LogUtil;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lq
 * @fileName BasePresenter
 * @data on  2019/7/25 14:24
 * @describe 网络请求基类
 */
public abstract class BasePresenter extends RetrofitUtils {

    public static final int REQUEST_SUCCESS = 0;//请求成功
    public static final int REQUEST_TOKEN_LOSE = 401;//token失效
    public static final int REQUEST_FAILURE = 500;//请求失败
    protected NetApi service = null;


    public <T> void TransmitCommonApi(boolean switchService, boolean TestSwitch, boolean isPost, boolean isBody, String part, String methodName, Map<String, String> parameterMap, TypeToken<?> typeToken) {

        GsonBuilder gsonBuilder =new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson =gsonBuilder.create();

        LogUtil.e("http", "http--url==" + (switchService ? IPConfig.getURLPreFix() : IPConfig.getOutSourceURLPreFix()) + part + "/" + methodName);
        LogUtil.e("http", "http--requestMethod==" + (isPost ? "post" : "get"));
        LogUtil.e("http", "http--requestMethod==" + (isBody ? "body" : "params"));
        LogUtil.e("http", "http--parameter==" + gson.toJson(parameterMap));
        BaseReseponseInfo baseResponse = null;
        /**
         * 判断是否是测试模式
         */
        if (TestSwitch) {
            InputStream in = null;
            try {
                in = BaseApplication.ApplicationContext.getAssets().open(methodName + ".txt");
                String result = FileUtil.ToString(in);
                LogUtil.e("http", "Http--本地响应===" + result);
                baseResponse = gson.fromJson(result, BaseReseponseInfo.class);
                responseData(baseResponse, methodName, parameterMap, typeToken);
            } catch (IOException e) {
                e.printStackTrace();
                onResponse(methodName, null, REQUEST_FAILURE, parameterMap, null != baseResponse ? baseResponse.msg : "");
                LogUtil.e("http", "BaseParser--e==" + e);
            }

        } else {

            service = getRetrofit(switchService, null).create(NetApi.class);

            /**
             *网络请求
             */
            commonApi(isBody, isPost, part, methodName, parameterMap, typeToken);
        }
    }


    /**
     * 接口调用
     *
     * @param methodName   方法名
     * @param parameterMap 参数
     * @param typeToken    返回值类型
     */
    public void commonApi(boolean isBody, boolean isPost, final String part, final String methodName, final Map<String, String> parameterMap, final TypeToken<?> typeToken) {


        GsonBuilder gsonBuilder =new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson =gsonBuilder.create();

        Observable<BaseReseponseInfo> observable;
        if (isPost) {
            if (isBody) {
                observable = service.serviceAPIBady(part, methodName, parameterMap);
            } else {
                observable = service.serviceAPI(part, methodName, parameterMap);
            }
        } else {
            observable = service.serviceGetAPI(part, methodName, parameterMap);
        }
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(new Observer<BaseReseponseInfo>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    //网络通讯失败
                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("http", "BaseParser--e==" + e);
                        onResponse(methodName, null, REQUEST_FAILURE, parameterMap, e.getMessage());
                    }

                    //获取数据
                    @Override
                    public void onNext(BaseReseponseInfo baseResponse) {
                        LogUtil.e("http", "网络响应" + gson.toJson(baseResponse));
                        responseData(baseResponse, methodName, parameterMap, typeToken);
                    }
                });
    }


    /**
     * 服务器返回数据解析
     *
     * @param receivedStr
     * @param typeToken
     * @return
     */
    public Object requestServer(Object receivedStr, TypeToken<?> typeToken) {
        GsonBuilder gsonBuilder =new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson =gsonBuilder.create();

        try {
            Object result = null;
            if (!TextUtils.isEmpty(receivedStr + "")) {
                result = gson.fromJson(gson.toJson(receivedStr), typeToken.getType());
            }
            return result;
        } catch (Exception e) {
            LogUtil.e("gson", "BaseParser--e==" + e);
        }
        return null;
    }

    /**
     * 详情数据处理
     *
     * @param baseResponse 响应数据
     * @param methodName   请求方法名
     * @param parameterMap 请求参数
     * @param typeToken    返回数据类型
     */
    private void responseData(BaseReseponseInfo baseResponse, String methodName, Map<String, String> parameterMap, TypeToken<?> typeToken) {
        if (baseResponse.code == REQUEST_SUCCESS) {
            Object object = null;
            if (null != typeToken) {
                if (null == baseResponse.rows || TextUtils.isEmpty(baseResponse.rows + "")) {
                    object = requestServer(baseResponse.data, typeToken);
                } else {
                    object = requestServer(baseResponse, typeToken);
                }
            }
            //成功回调数据
            onResponse(methodName, object, REQUEST_SUCCESS, parameterMap, baseResponse.msg);

        } else if (baseResponse.code == REQUEST_TOKEN_LOSE) {    //token失效
            onResponse(methodName, baseResponse, REQUEST_FAILURE, parameterMap, baseResponse.msg);
            //token失效
            EventBus.getDefault().post(new TokenLoseEvent(false));
        } else {   //请求失败
            onResponse(methodName, baseResponse, REQUEST_FAILURE, parameterMap, baseResponse.msg);
        }
    }


    /**
     * 接口返回数据
     *
     * @param methodName 方法名
     * @param object     返回数据对象
     * @param status     是否成功标识
     */

    public abstract void onResponse(String methodName, Object object, int status, Map<String, String> parameterMap, String Msg);


}
