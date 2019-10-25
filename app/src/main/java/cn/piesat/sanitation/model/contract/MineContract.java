package cn.piesat.sanitation.model.contract;

import java.util.ArrayList;
import java.util.Map;

import cn.piesat.sanitation.data.MyAttendanceLBean;
import cn.piesat.sanitation.data.UserListBean;

/**
 * Created by sen.luo on 2019/10/24.
 */

public interface MineContract {

    interface MyAttendanceListView{
        void Error(String msg);
        void SuccessOnAttendanceList(MyAttendanceLBean myAttendanceLBean);
    }

    interface  getUserListView{
        void Error(String msg);
        void SuccessOnUserList(UserListBean userListBean);
    }

    interface MyAttendancePresenter{
        void getMyAttendanceList(Map<String,String> map);
        void getUserList(Map<String,String> map);
    }


}
