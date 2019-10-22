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
        void SuccessFinshByUserInfo(List<CheckRecord> checkRecords);
    }

    interface UserInfoPresenter {
        //上传头像
        void uploadByHeadPic(String date);
    }
}
