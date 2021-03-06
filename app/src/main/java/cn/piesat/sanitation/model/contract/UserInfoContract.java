package cn.piesat.sanitation.model.contract;

import java.util.List;

import cn.piesat.sanitation.data.CheckRecord;

/**
 * @author lq
 * @fileName LoginContract
 * @data on  2019/7/25 15:44
 * @describe TODO
 */
public interface UserInfoContract {

    interface UserInfoView {
        void Error(String errorMsg);
        void SuccessFinsh();
        void SuccessFinshPicVers();
        void SuccessFinshByWorkCheck();

    }

    interface MyDetailUserInfoView {
        void Error(String errorMsg);
        void SuccessFinsh();
    }

    interface UserInfoPresenter {
        //上传头像
        void ModeyUserPic(String userPic);
        //头像对比
        void UserPicVers(String userPic);
        //打卡
        void WorkChecking(String type,String time);
        //修改用户信息
        void ModeyUserInfo(String userPic,String name,String sex,String phone,String birthday,String address);
    }
}
