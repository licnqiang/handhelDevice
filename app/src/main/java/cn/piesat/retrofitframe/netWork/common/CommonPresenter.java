package cn.piesat.retrofitframe.netWork.common;

import com.google.gson.reflect.TypeToken;
import java.util.HashMap;
import java.util.Map;

/**
 * 【CommonPresenter presenter】
 * AUTHOR: Alex
 * DATE: 22/10/2015 10:57
 */
public class CommonPresenter extends BasePresenter {


    public ICommonAction iCommonAction;

    public CommonPresenter(ICommonAction iCommonAction) {
        this.iCommonAction = iCommonAction;
    }


    /**
     * 有参公共方法调用
     *
     * @param methodName
     * @param typeToken
     */
    public void invokeInterfaceObtainData(boolean test,boolean isPost, String part, String methodName, Map<String, String> parameterMap, TypeToken<?> typeToken) {
        if (parameterMap == null) {
            TransmitCommonApi(test,isPost, part, methodName, new HashMap<String, String>(), typeToken);
        } else {
            TransmitCommonApi(test,isPost, part, methodName, parameterMap, typeToken);

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
