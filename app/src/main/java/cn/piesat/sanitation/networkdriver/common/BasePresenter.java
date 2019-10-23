package cn.piesat.sanitation.networkdriver.common;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.IPConfig;
import cn.piesat.sanitation.networkdriver.module.NetApi;
import cn.piesat.sanitation.networkdriver.module.RetrofitUtils;
import cn.piesat.sanitation.util.FileUtil;
import cn.piesat.sanitation.util.Log;
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

    public static final int REQUEST_SUCCESS = 200;//请求成功
    public static final int REQUEST_SUCCESS_TWO = 0;//请求成功
    public static final int REQUEST_FAILURE = 500;//请求失败
    protected NetApi service = null;


    public <T> void TransmitCommonApi(boolean switchService, boolean TestSwitch, boolean isPost, String part, String methodName, Map<String, String> parameterMap, TypeToken<?> typeToken) {

        Log.e("http", "http--url==" + (switchService ? IPConfig.getURLPreFix() : IPConfig.getOutSourceURLPreFix()) + part + "/" + methodName);
        Log.e("http", "http--requestMethod==" + (isPost ? "post" : "get"));
        Log.e("http", "http--parameter==" + new Gson().toJson(parameterMap));
        BaseReseponseInfo baseResponse = null;
        /**
         * 判断是否是测试模式
         */
        if (TestSwitch) {
            InputStream in = null;
            try {
                in = BaseApplication.ApplicationContext.getAssets().open(methodName + ".txt");
                String result = FileUtil.ToString(in);
                Log.e("http", "Http--本地响应===" + result);
                baseResponse = new Gson().fromJson(result, BaseReseponseInfo.class);
                responseData(baseResponse, methodName, parameterMap, typeToken);
            } catch (IOException e) {
                e.printStackTrace();
                onResponse(methodName, null, REQUEST_FAILURE, parameterMap, null != baseResponse ? baseResponse.msg : "");
                Log.e("http", "BaseParser--e==" + e);
            }

        } else {

            service = getRetrofit(switchService, null).create(NetApi.class);

            /**
             *网络请求
             */
            commonApi(isPost, part, methodName, parameterMap, typeToken);
        }
    }


    /**
     * 接口调用
     *
     * @param methodName   方法名
     * @param parameterMap 参数
     * @param typeToken    返回值类型
     */
    public void commonApi(boolean isPost, final String part, final String methodName, final Map<String, String> parameterMap, final TypeToken<?> typeToken) {
        Observable<BaseReseponseInfo> observable;
        if (isPost) {
            observable = service.serviceAPI(part, methodName, parameterMap);
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
                        Log.e("http", "BaseParser--e==" + e);
                        onResponse(methodName, null, REQUEST_FAILURE, parameterMap, e.getMessage());
                    }

                    //获取数据
                    @Override
                    public void onNext(BaseReseponseInfo baseResponse) {
                        Log.e("http", "onNext" + new Gson().toJson(baseResponse));
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
        try {
            Object result = null;
            if (!TextUtils.isEmpty(receivedStr.toString())) {
                result = new Gson().fromJson(new Gson().toJson(receivedStr), typeToken.getType());
            }
            return result;
        } catch (Exception e) {
            Log.e("gson", "BaseParser--e==" + e);
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
                object = requestServer(baseResponse.data, typeToken);
            }
            //成功回调数据
            onResponse(methodName, object, REQUEST_SUCCESS, parameterMap, baseResponse.msg);
        } else if (baseResponse.code == REQUEST_SUCCESS_TWO) {
            Object objects = null;
            if (null != typeToken) {
                objects = requestServer(baseResponse, typeToken);
            }
            onResponse(methodName, objects, REQUEST_SUCCESS_TWO, parameterMap, baseResponse.msg);
        } else {
            onResponse(methodName, baseResponse, REQUEST_FAILURE, parameterMap, baseResponse.msg);
        }

//        switch (baseResponse.code) {
//            case REQUEST_SUCCESS: //成功
//                Object object = null;
//                if (null != typeToken) {
//                    object = requestServer(baseResponse.data, typeToken);
//                }
//                //成功回调数据
//                onResponse(methodName, object, REQUEST_SUCCESS, parameterMap, baseResponse.msg);
//                break;
////            case REQUEST_FAILURE://失败
////                onResponse(methodName, baseResponse, REQUEST_FAILURE, parameterMap, baseResponse.msg);
////                break;
//            //为了区分分页数据暂定该方法
//            case REQUEST_SUCCESS_TWO: //成功
//                onResponse(methodName, baseResponse, REQUEST_SUCCESS, parameterMap, baseResponse.msg);
//                break;
//            default:
//                Object objects = null;
//                if (null != typeToken) {
//                    objects = requestServer(new Gson().toJson(baseResponse), typeToken);
//                }
//                onResponse(methodName, objects, REQUEST_FAILURE, parameterMap, baseResponse.msg);
//                break;
//        }
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
