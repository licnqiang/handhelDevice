package cn.piesat.sanitation.model.presenter;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Map;

import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.HomeNewsBean;
import cn.piesat.sanitation.data.MyAttendanceLBean;
import cn.piesat.sanitation.data.UserListBean;
import cn.piesat.sanitation.model.contract.MineContract;
import cn.piesat.sanitation.networkdriver.common.CommonPresenter;
import cn.piesat.sanitation.networkdriver.common.ICommonAction;

import static cn.piesat.sanitation.networkdriver.common.BasePresenter.REQUEST_SUCCESS;

/**
 * Created by sen.luo on 2019/10/24.
 */

public class MinePresenter implements ICommonAction,MineContract.MyAttendancePresenter {

    private CommonPresenter commonPresenter;
    private MineContract.MyAttendanceListView myAttendanceListView;
    private MineContract.getUserListView getUserListView;

    public MinePresenter(MineContract.MyAttendanceListView myAttendanceListView) {
        this.myAttendanceListView = myAttendanceListView;
        commonPresenter=new CommonPresenter(this);
    }

    public MinePresenter(MineContract.getUserListView getUserListView) {
        this.getUserListView = getUserListView;
        commonPresenter=new CommonPresenter(this);
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status, Map<String, String> parameterMap, String Msg) {
        switch (methodIndex){

            //考勤列表
            case UrlContant.MySourcePart.attendance_List:
                if (status == REQUEST_SUCCESS) {//成功
                    myAttendanceListView.SuccessOnAttendanceList((MyAttendanceLBean) data);
                }else {
                    myAttendanceListView.Error(Msg);

                }
                break;
             //人员列表
             case UrlContant.OutSourcePart.user_list:
                 if (status == REQUEST_SUCCESS) {//成功
                     getUserListView.SuccessOnUserList((UserListBean) data);
                 }else {
                     myAttendanceListView.Error(Msg);

                 }
                 break;
        }


    }

    /**
     * 考勤列表
     * @param map
     */
    @Override
    public void getMyAttendanceList(Map<String, String> map) {
        commonPresenter.invokeInterfaceObtainData(true, false, false,false, UrlContant.MySourcePart.attendance_part, UrlContant.MySourcePart.attendance_List,
                map, new TypeToken< MyAttendanceLBean>() {
                });
    }

    /**
     * 人员列表
     * @param map
     */
    @Override
    public void getUserList(Map<String, String> map) {
        commonPresenter.invokeInterfaceObtainData(false, false, false,false, UrlContant.OutSourcePart.part, UrlContant.OutSourcePart.user_list,
                map, new TypeToken<UserListBean>() {
                });
    }

}
