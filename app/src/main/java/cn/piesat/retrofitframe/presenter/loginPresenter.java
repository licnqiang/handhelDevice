package cn.piesat.retrofitframe.presenter;

import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import cn.piesat.retrofitframe.contract.LoginContract;
import cn.piesat.retrofitframe.netWork.common.CommonPresenter;
import cn.piesat.retrofitframe.netWork.configuration.ICommonAction;

/**
 * @author lq
 * @fileName loginPresenter
 * @data on  2019/7/25 15:41
 * @describe TODO
 */
public class loginPresenter implements ICommonAction, LoginContract.LoginPresenter {

    private CommonPresenter commonPresenter;
    private LoginContract.LoginView mLoginView;

    public loginPresenter(LoginContract.LoginView loginView) {
        mLoginView = loginView;
        commonPresenter = new CommonPresenter(this);
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status, Map<String, String> parameterMap) {
    }

    @Override
    public void login(String userName, String passWord) {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("loginName",userName);
        hashMap.put("password",passWord);
        commonPresenter.invokeInterfaceObtainData(false, true, "users","login",
                hashMap, null);
    }

}
