package cn.piesat.sanitation.model.presenter;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.sql.language.Delete;
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
    private UserInfoContract.MyDetailUserInfoView myDetailUserInfoView;


    public UserInfoPresenter(UserInfoContract.UserInfoView UserInfoView) {
        userInfoView = UserInfoView;
        commonPresenter = new CommonPresenter(this);
    }

    public UserInfoPresenter(UserInfoContract.MyDetailUserInfoView MyDetailUserInfoView) {
        myDetailUserInfoView = MyDetailUserInfoView;
        commonPresenter = new CommonPresenter(this);
    }


    @Override
    public void ModeyUserPic(String userPic) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", BaseApplication.getUserInfo().id);
        hashMap.put("lay1Sysuser", userPic);
        commonPresenter.invokeInterfaceObtainData(false, false, true, true, UrlContant.OutSourcePart.part, UrlContant.OutSourcePart.mody_user_pic,
                hashMap, new TypeToken<String>() {
                });
    }

    //头像验证
    @Override
    public void UserPicVers(String userPic) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("filePath1", IPConfig.getOutSourceURLPreFix() + BaseApplication.getUserInfo().lay1Sysuser);
        hashMap.put("filePath2", userPic);
        commonPresenter.invokeInterfaceObtainData(true, false, true, false, UrlContant.MySourcePart.face_part, UrlContant.MySourcePart.face,
                hashMap, new TypeToken<FacePk>() {
                });
    }

    //打卡
    @Override
    public void WorkChecking(String type, String time) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", BaseApplication.getUserInfo().id);
        hashMap.put("time", time);
        hashMap.put("createDate", TimeUtils.getCurrentTime());
        hashMap.put("type", type);
        hashMap.put("siteId", BaseApplication.getUserInfo().yszIdCount);
        hashMap.put("serialVersionUID", "1");
        commonPresenter.invokeInterfaceObtainData(true, false, true, true, UrlContant.MySourcePart.part, UrlContant.MySourcePart.check_user,
                hashMap, new TypeToken<BaseReseponseInfo>() {
                });
    }

    //修改用户信息
    @Override
    public void ModeyUserInfo(String userPic, String name, String sex, String phone, String birthday, String address) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", BaseApplication.getUserInfo().id);
        hashMap.put("lay1Sysuser", userPic);
        hashMap.put("name", name);
        hashMap.put("sex", sex);
        hashMap.put("phone", phone);
        if (TextUtils.isEmpty(birthday)) {             //生日加判断 原因后台接受时间字段是时分秒格式，用户有可能不输入时间字段，加此判断避免接口报错
            hashMap.put("birthday", birthday);
        }
        hashMap.put("address", address);
        commonPresenter.invokeInterfaceObtainData(false, false, true, true, UrlContant.OutSourcePart.part, UrlContant.OutSourcePart.mody_user_pic,
                hashMap, new TypeToken<String>() {
                });
    }


    @Override
    public void obtainData(Object data, String methodIndex, int status, Map<String, String> parameterMap, String Msg) {
        switch (methodIndex) {
            //两个界面共用一个方法，接受相应数据要通过相应的view接口是否为null判断
            case UrlContant.OutSourcePart.mody_user_pic:  //修改头像
                if (status == REQUEST_SUCCESS) {//成功
                    UserInfo_Tab userInfo = BaseApplication.getUserInfo();
                    parameterMap.get("lay1Sysuser");  //获取发送的图片参数，更新本地图片
                    //更新用户信息
                    userInfo.lay1Sysuser = parameterMap.get("lay1Sysuser");  //更新图像
                    if (null != myDetailUserInfoView) {
                        userInfo.name = parameterMap.get("name");
                        userInfo.sex = parameterMap.get("sex");
                        userInfo.phone = parameterMap.get("phone");
                        userInfo.birthday = parameterMap.get("birthday");
                        userInfo.address = parameterMap.get("address");
                        myDetailUserInfoView.SuccessFinsh();  //个人中心回掉
                    }
                    /**
                     * 更新本地数据，用户数据永远只有一个
                     */
                    userInfo.update(); //更新本地数据

                    if (null != userInfoView) {
                        userInfoView.SuccessFinsh();  //打卡收录头像回掉
                    }
                } else {
                    if (null != myDetailUserInfoView) {
                        myDetailUserInfoView.Error(Msg);      //打卡收录头像回掉
                    }
                    if (null != userInfoView) {
                        userInfoView.Error(Msg);              //个人中心回掉
                    }
                }
                break;

            case UrlContant.MySourcePart.face:  //头像对比
                if (status == REQUEST_SUCCESS) {//成功
                    FacePk facePk = (FacePk) data;
                    if (facePk.contrast) {
                        userInfoView.SuccessFinshPicVers();
                    } else {
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
