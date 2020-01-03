package cn.piesat.sanitation.model.presenter;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.sql.language.Delete;

import java.util.HashMap;
import java.util.Map;

import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.LoginInfo_Respose;
import cn.piesat.sanitation.database.dbTab.UserInfo_Tab;
import cn.piesat.sanitation.model.contract.LoginContract;
import cn.piesat.sanitation.networkdriver.common.CommonPresenter;
import cn.piesat.sanitation.networkdriver.common.ICommonAction;
import cn.piesat.sanitation.util.SpHelper;

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
        commonPresenter.invokeInterfaceObtainData(false, false, true, false, UrlContant.OutSourcePart.part, UrlContant.OutSourcePart.login,
                hashMap, new TypeToken<LoginInfo_Respose>() {
                });
    }


    @Override
    public void obtainData(Object data, String methodIndex, int status, Map<String, String> parameterMap, String Msg) {

        if (status == REQUEST_SUCCESS) {//成功
            LoginInfo_Respose loginInfo_respose = (LoginInfo_Respose) data;
            //通过用户id初始化数据库，保证用户数据库的唯一性
            BaseApplication.initDB(loginInfo_respose.user.id);
            new Delete().from(UserInfo_Tab.class).execute();
            //保存用户基本信息
            loginInfo_respose.user.save();
            SpHelper.setStringValue("token", loginInfo_respose.token);
            SpHelper.setStringValue("userId", loginInfo_respose.user.id); //保存该id主要用于开启数据库
            mLoginView.jumpToMain();
        } else {
            mLoginView.loginError(Msg);
        }


    }
}
