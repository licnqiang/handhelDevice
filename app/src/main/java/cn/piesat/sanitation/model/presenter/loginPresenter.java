package cn.piesat.sanitation.model.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.CheckUpdateBean;
import cn.piesat.sanitation.data.LoginInfo_Respose;
import cn.piesat.sanitation.database.dbTab.RolesInfo_Tab;
import cn.piesat.sanitation.database.dbTab.UserInfo_Tab;
import cn.piesat.sanitation.model.contract.LoginContract;
import cn.piesat.sanitation.networkdriver.common.CommonPresenter;
import cn.piesat.sanitation.networkdriver.common.ICommonAction;
import cn.piesat.sanitation.util.LogUtil;
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

    private LoginContract.CheckVersionUpdate checkVersionUpdate;

    public loginPresenter(LoginContract.LoginView loginView) {
        mLoginView = loginView;
        commonPresenter = new CommonPresenter(this);
    }

    public loginPresenter(LoginContract.CheckVersionUpdate checkVersionUpdate) {
        this.checkVersionUpdate = checkVersionUpdate;
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
    public void checkUpdate() {
        commonPresenter.invokeInterfaceObtainData(false,false,false,false,UrlContant.OutSourcePart.part,UrlContant.OutSourcePart.check_version_update,
                null,new TypeToken<CheckUpdateBean>(){});
    }


    @Override
    public void obtainData(Object data, String methodIndex, int status, Map<String, String> parameterMap, String Msg) {

        switch (methodIndex){
            case UrlContant.OutSourcePart.login:
                if (status == REQUEST_SUCCESS) {//成功
                    LoginInfo_Respose loginInfo_respose = (LoginInfo_Respose) data;
                    if (loginInfo_respose.user==null){
                        mLoginView.loginError("返回用户信息有误，无法登陆");
                        return;
                    }
                    //通过用户id初始化数据库，保证用户数据库的唯一性
                    BaseApplication.initDB(loginInfo_respose.user.id);
                    new Delete().from(UserInfo_Tab.class).execute();
                    //保存用户基本信息
                    loginInfo_respose.user.save();

                    SpHelper.setStringValue(SysContant.userInfo.USER_TOKEN, loginInfo_respose.token);
                    SpHelper.setStringValue(SysContant.userInfo.USER_ID, loginInfo_respose.user.id); //保存该id主要用于开启数据库
                    SpHelper.setStringValue(SysContant.userInfo.USER_SITE_NAME, loginInfo_respose.user.deptNameCount);//站点
                    //保存用户角色，因暂时用户角色唯一 所以只取第一条数据，
            /*if(null!=loginInfo_respose.roles&&loginInfo_respose.roles.size()>0){
                loginInfo_respose.roles.get(0).save();
            }
*/
                    // userType=3 保存用户角色list信息
                    if (loginInfo_respose.user.userType==3){
                        if (null != loginInfo_respose.roles && loginInfo_respose.roles.size() > 0) {
                   /* for (RolesInfo_Tab tab : loginInfo_respose.roles) {
                        tab.save();
                    }*/
                            Gson gson =new Gson();
                            List<String>roleList =new ArrayList<>();

                            for (int i = 0; i <loginInfo_respose.roles.size() ; i++) {
                                if (loginInfo_respose.roles.get(i).identity!=null){
                                    roleList.add(loginInfo_respose.roles.get(i).identity);
                                }

                            }
                            SpHelper.setStringValue(SysContant.userInfo.USER_ROLE_ID_LIST,gson.toJson(roleList));

                        }


                    }
                    mLoginView.jumpToMain();
                } else {
                    mLoginView.loginError(Msg);
                }

                break;
            case UrlContant.OutSourcePart.check_version_update:
                if (status==REQUEST_SUCCESS){
                    checkVersionUpdate.checkSuccess((CheckUpdateBean) data);
                }else {
                    checkVersionUpdate.checkError(Msg);
                }
                break;
        }



    }
}
