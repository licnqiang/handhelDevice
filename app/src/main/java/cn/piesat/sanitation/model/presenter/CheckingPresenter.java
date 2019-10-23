package cn.piesat.sanitation.model.presenter;

import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.CheckRecord;
import cn.piesat.sanitation.data.CompressStations;
import cn.piesat.sanitation.data.LoginInfo_Respose;
import cn.piesat.sanitation.data.StationCheckSet;
import cn.piesat.sanitation.database.dbTab.UserInfo_Tab;
import cn.piesat.sanitation.model.contract.CheckingContract;
import cn.piesat.sanitation.model.contract.QueryContract;
import cn.piesat.sanitation.networkdriver.common.BaseReseponseInfo;
import cn.piesat.sanitation.networkdriver.common.CommonPresenter;
import cn.piesat.sanitation.networkdriver.common.ICommonAction;
import cn.piesat.sanitation.util.SpHelper;
import cn.piesat.sanitation.util.TimeUtils;

import static cn.piesat.sanitation.networkdriver.common.BasePresenter.REQUEST_SUCCESS;

/**
 * @author lq
 * @fileName 查询压缩站
 * @data on  2019/7/25 15:41
 * @describe TODO
 */
public class CheckingPresenter implements ICommonAction, CheckingContract.CheckingPresenter {

    private CommonPresenter commonPresenter;
    private CheckingContract.CheckingView CheckingView;

    public CheckingPresenter(CheckingContract.CheckingView checkingView) {
        CheckingView = checkingView;
        commonPresenter = new CommonPresenter(this);
    }

    //查询考勤情况
    @Override
    public void QueryCheckingState(String date) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", BaseApplication.getUserInfo().id);
        hashMap.put("date", date);
        commonPresenter.invokeInterfaceObtainData(true, false, false,false, UrlContant.MySourcePart.part, UrlContant.MySourcePart.check_record,
                hashMap, new TypeToken<CheckRecord>() {
                });
    }

    //打卡
    @Override
    public void WorkChecking(String type,String time) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", BaseApplication.getUserInfo().idSysdept);
        hashMap.put("time", time);
        hashMap.put("createDate", TimeUtils.getCurrentTime());
        hashMap.put("type", type);
        hashMap.put("siteId", BaseApplication.getUserInfo().idSysdept);
        hashMap.put("serialVersionUID","1");
        commonPresenter.invokeInterfaceObtainData(true, false, true,true, UrlContant.MySourcePart.part, UrlContant.MySourcePart.check_user,
                hashMap, new TypeToken<BaseReseponseInfo>() {
                });
    }

    //获取考勤时间位置限制
    @Override
    public void WorKTimeSet() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("siteId", BaseApplication.getUserInfo().idSysdept);
        commonPresenter.invokeInterfaceObtainData(true, false, false,false, UrlContant.MySourcePart.check_set_par, UrlContant.MySourcePart.check_set,
                hashMap, new TypeToken<StationCheckSet>() {
                });
    }


    @Override
    public void obtainData(Object data, String methodIndex, int status, Map<String, String> parameterMap, String Msg) {
        switch (methodIndex) {
            case UrlContant.MySourcePart.check_record:  //通过时间获取考勤记录
                if (status == REQUEST_SUCCESS) {//成功
                    CheckRecord checkRecords = (CheckRecord) data;
                    CheckingView.SuccessFinshByCheckRecord(checkRecords);
                } else {
                    CheckingView.Error(Msg);
                }
                break;

            case UrlContant.MySourcePart.check_set:  //通过时间获取考勤记录
                if (status == REQUEST_SUCCESS) {//成功
                    StationCheckSet stationCheckSet = (StationCheckSet) data;
                    CheckingView.SuccessFinshByWorKTimeSet(stationCheckSet);
                } else {
                    CheckingView.Error(Msg);
                }

                break;

            case UrlContant.MySourcePart.check_user:  //打卡
                if (status == REQUEST_SUCCESS) {//成功
                    CheckingView.SuccessFinshByWorkCheck();
                } else {
                    CheckingView.Error(Msg);
                }

                break;

        }
    }
}
