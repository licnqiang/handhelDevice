package cn.piesat.sanitation.model.presenter;

import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.IPConfig;
import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.CheckRecord;
import cn.piesat.sanitation.data.FacePk;
import cn.piesat.sanitation.database.dbTab.UserInfo_Tab;
import cn.piesat.sanitation.model.contract.CheckingContract;
import cn.piesat.sanitation.model.contract.UserInfoContract;
import cn.piesat.sanitation.networkdriver.common.BaseReseponseInfo;
import cn.piesat.sanitation.networkdriver.common.CommonPresenter;
import cn.piesat.sanitation.networkdriver.common.ICommonAction;
import cn.piesat.sanitation.util.TimeUtils;

import static cn.piesat.sanitation.networkdriver.common.BasePresenter.REQUEST_SUCCESS;

/**
 * @author lq
 * @fileName 查询压缩站
 * @data on  2019/7/25 15:41
 * @describe TODO
 */
public class UserInfoPresenter implements ICommonAction, UserInfoContract.UserInfoPresenter {

    private CommonPresenter commonPresenter;
    private UserInfoContract.UserInfoView userInfoView;

    public UserInfoPresenter(UserInfoContract.UserInfoView UserInfoView) {
        userInfoView = UserInfoView;
        commonPresenter = new CommonPresenter(this);
    }


    @Override
    public void ModeyUserPic(String userPic) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", BaseApplication.getUserInfo().id);
        hashMap.put("lay1Sysuser", userPic);
        commonPresenter.invokeInterfaceObtainData(false, false, true,true, UrlContant.OutSourcePart.part, UrlContant.OutSourcePart.mody_user_pic,
                hashMap, new TypeToken<String>() {
                });
    }

    @Override
    public void UserPicVers(String userPic) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("filePath1", IPConfig.getOutSourceURLPreFix()+BaseApplication.getUserInfo().lay1Sysuser);
        hashMap.put("filePath2", userPic);
        commonPresenter.invokeInterfaceObtainData(true, false, true,false, UrlContant.MySourcePart.face_part, UrlContant.MySourcePart.face,
                hashMap, new TypeToken<FacePk>() {
                });
    }

    //打卡
    @Override
    public void WorkChecking(String type,String time) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", BaseApplication.getUserInfo().id);
        hashMap.put("time", time);
        hashMap.put("createDate", TimeUtils.getCurrentTime());
        hashMap.put("type", type);
        hashMap.put("siteId", BaseApplication.getUserInfo().yszIdCount);
        hashMap.put("serialVersionUID","1");
        commonPresenter.invokeInterfaceObtainData(true, false, true,true, UrlContant.MySourcePart.part, UrlContant.MySourcePart.check_user,
                hashMap, new TypeToken<BaseReseponseInfo>() {
                });
    }


    @Override
    public void obtainData(Object data, String methodIndex, int status, Map<String, String> parameterMap, String Msg) {
        switch (methodIndex) {
            case UrlContant.OutSourcePart.mody_user_pic:  //通过时间获取考勤记录
                if (status == REQUEST_SUCCESS) {//成功
                    parameterMap.get("lay1Sysuser");
                    //更新用户信息
                    BaseApplication.getUserInfo().lay1Sysuser=parameterMap.get("lay1Sysuser");
                    BaseApplication.getUserInfo().save();
                    userInfoView.SuccessFinsh();
                } else {
                    userInfoView.Error(Msg);
                }
                break;

            case UrlContant.MySourcePart.face:  //头像对比
                if (status == REQUEST_SUCCESS) {//成功
                    FacePk facePk=(FacePk)data;
                    if(facePk.contrast){
                        userInfoView.SuccessFinshPicVers();
                    }else {
                        userInfoView.Error(Msg);
                    }

                } else {
                    userInfoView.Error(Msg);
                }

                break;
            case UrlContant.MySourcePart.check_user:  //打卡
                if (status == REQUEST_SUCCESS) {//成功
                    userInfoView.SuccessFinshByWorkCheck();
                } else {
                    userInfoView.Error(Msg);
                }

                break;

        }
    }

}
