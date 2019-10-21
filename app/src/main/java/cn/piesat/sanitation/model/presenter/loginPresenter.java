package cn.piesat.sanitation.model.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.HashMap;
import java.util.Map;

import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.LoginInfo_Respose;
import cn.piesat.sanitation.data.User;
import cn.piesat.sanitation.model.contract.LoginContract;
import cn.piesat.sanitation.networkdriver.common.BasePresenter;
import cn.piesat.sanitation.networkdriver.common.CommonPresenter;
import cn.piesat.sanitation.networkdriver.common.ICommonAction;
import cn.piesat.sanitation.util.Log;

import static cn.piesat.sanitation.networkdriver.common.BasePresenter.REQUEST_SUCCESS;

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
        hashMap.put("username", userName);
        hashMap.put("password", passWord);
        commonPresenter.invokeInterfaceObtainData(false,false, true, UrlContant.OutSourcePart.part, UrlContant.OutSourcePart.login,
                hashMap, new TypeToken<LoginInfo_Respose>() {
                });
    }


    @Override
    public void obtainData(Object data, String methodIndex, int status, Map<String, String> parameterMap,String Msg) {
        switch (status) {
            case REQUEST_SUCCESS: //成功
                LoginInfo_Respose loginInfo_respose=(LoginInfo_Respose)data;
                //通过用户id初始化数据库，保证用户数据库的唯一性
                BaseApplication.initDB(loginInfo_respose.user.id);
                //保存用户基本信息
                loginInfo_respose.user.save();
                mLoginView.jumpToMain();
                break;
            case BasePresenter.REQUEST_FAILURE://失败
                mLoginView.loginError(Msg);
                break;
        }

    }
}
