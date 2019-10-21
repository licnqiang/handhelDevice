package cn.piesat.sanitation.model.presenter;

import com.google.gson.reflect.TypeToken;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.HashMap;
import java.util.Map;

import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.User;
import cn.piesat.sanitation.model.contract.LoginContract;
import cn.piesat.sanitation.networkdriver.common.CommonPresenter;
import cn.piesat.sanitation.networkdriver.common.ICommonAction;
import cn.piesat.sanitation.util.Log;

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
    public void login(String userName, String passWord) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("loginName", userName);
        hashMap.put("password", passWord);
        commonPresenter.invokeInterfaceObtainData(false,false, true, UrlContant.part, UrlContant.login,
                hashMap, new TypeToken<String>() {
                });
    }


    @Override
    public void obtainData(Object data, String methodIndex, int status, Map<String, String> parameterMap) {
       // Log.e("--obtainData--", "---" + data.toString());
    }
}
