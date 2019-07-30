package cn.piesat.retrofitframe.networkdriver.common;

import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author :lq
 * @Date :created at 2019/7/27 11:13
 * @Description: Presenter基类
 */
public class CommonPresenter extends BasePresenter {


    public ICommonAction iCommonAction;

    public CommonPresenter(ICommonAction iCommonAction) {
        this.iCommonAction = iCommonAction;
    }


    /**
     * 有参公共方法调用
     *
     * @param testModel    是否是测试模式
     * @param isPost       是否是post方法
     * @param part         部分url
     * @param methodName   部分url
     * @param parameterMap 请求参数
     * @param typeToken    响应数据类型
     */
    public void invokeInterfaceObtainData(boolean testModel, boolean isPost, String part, String methodName, Map<String, String> parameterMap, TypeToken<?> typeToken) {
        if (parameterMap == null) {
            TransmitCommonApi(testModel, isPost, part, methodName, new HashMap<String, String>(), typeToken);
        } else {
            TransmitCommonApi(testModel, isPost, part, methodName, parameterMap, typeToken);

        }
    }


    @Override
    public void onResponse(String methodName, Object object, int status, Map<String, String> parameterMap) {

        switch (methodName) {
            default:
                iCommonAction.obtainData(object, methodName, status, parameterMap);
                break;

        }

    }


}
