package cn.piesat.retrofitframe.networkdriver.common;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.sql.language.Case;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import cn.piesat.retrofitframe.common.BaseApplication;
import cn.piesat.retrofitframe.constant.IPConfig;
import cn.piesat.retrofitframe.networkdriver.module.NetApi;
import cn.piesat.retrofitframe.networkdriver.module.RetrofitUtils;
import cn.piesat.retrofitframe.util.FileUtil;
import cn.piesat.retrofitframe.util.Log;
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

    public  final int REQUEST_SUCCESS = 1;//请求成功
    public  final int REQUEST_FAILURE = 0;//请求失败

    protected final NetApi service = getRetrofit(null).create(NetApi.class);

    public <T> void TransmitCommonApi(boolean TestSwitch, boolean isPost, String part, String methodName, Map<String, String> parameterMap, TypeToken<?> typeToken) {

        Log.e("http", "http--url==" + IPConfig.getURLPreFix() + "part" + "/" + methodName);
        Log.e("http", "http--requestMethod==" + (isPost ? "post" : "get"));
        Log.e("http", "http--parameter==" + new Gson().toJson(parameterMap));

        /**
         * 判断是否是测试模式
         */
        if (TestSwitch) {
            InputStream in = null;
            try {
                in = BaseApplication.ApplicationContext.getAssets().open(methodName + ".txt");
                String result = FileUtil.ToString(in);
                Log.e("http", "Http--本地响应===" + result);
                BaseReseponseInfo baseResponse = new Gson().fromJson(result, BaseReseponseInfo.class);
                responseData(baseResponse, methodName, parameterMap, typeToken);
            } catch (IOException e) {
                e.printStackTrace();
                onResponse(methodName, null, REQUEST_FAILURE, parameterMap);
                Log.e("http", "BaseParser--e==" + e);
            }

        } else {
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
                        onResponse(methodName, null, REQUEST_FAILURE, parameterMap);
                    }

                    //获取数据
                    @Override
                    public void onNext(BaseReseponseInfo baseResponse) {
//                        Log.e("http", "onNext" + new Gson().toJson(baseResponse));
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
                result = new Gson().fromJson(receivedStr.toString(), typeToken.getType());
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
        switch (baseResponse.status) {
            case REQUEST_SUCCESS: //成功
                Object object = null;
                if (null != typeToken) {
                    object = requestServer(baseResponse.data, typeToken);
                }
                //成功回调数据
                onResponse(methodName, object, REQUEST_SUCCESS, parameterMap);
                break;
            case REQUEST_FAILURE://失败
                onResponse(methodName, baseResponse, REQUEST_FAILURE, parameterMap);
                break;
        }
    }


    /**
     * 接口返回数据
     *
     * @param methodName 方法名
     * @param object     返回数据对象
     * @param status     是否成功标识
     */

    public abstract void onResponse(String methodName, Object object, int status, Map<String, String> parameterMap);
}
