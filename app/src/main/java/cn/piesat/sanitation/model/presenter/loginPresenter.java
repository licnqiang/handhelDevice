package cn.piesat.sanitation.model.presenter;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.sql.language.Delete;

import java.util.HashMap;
import java.util.Map;

import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.LoginInfo_Respose;
import cn.piesat.sanitation.database.dbTab.RolesInfo_Tab;
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
            //保存用户角色，因暂时用户角色唯一 所以只取第一条数据，
            if(null!=loginInfo_respose.roles&&loginInfo_respose.roles.size()>0){
                loginInfo_respose.roles.get(0).save();
            }

//            if (null != loginInfo_respose.roles && loginInfo_respose.roles.size() > 0) {
//                for (RolesInfo_Tab tab : loginInfo_respose.roles) {
//                    tab.save();
//                }
//            }

            SpHelper.setStringValue(SysContant.userInfo.USER_TOKEN, loginInfo_respose.token);
            SpHelper.setStringValue(SysContant.userInfo.USER_ID, loginInfo_respose.user.id); //保存该id主要用于开启数据库
            SpHelper.setStringValue(SysContant.userInfo.USER_SITE_NAME, loginInfo_respose.user.deptNameCount);//站点
            mLoginView.jumpToMain();
        } else {
            mLoginView.loginError(Msg);
        }


    }
}
