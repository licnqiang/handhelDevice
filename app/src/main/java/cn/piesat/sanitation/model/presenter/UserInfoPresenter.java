package cn.piesat.sanitation.model.presenter;

import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.CheckRecord;
import cn.piesat.sanitation.database.dbTab.UserInfo_Tab;
import cn.piesat.sanitation.model.contract.CheckingContract;
import cn.piesat.sanitation.networkdriver.common.CommonPresenter;
import cn.piesat.sanitation.networkdriver.common.ICommonAction;

import static cn.piesat.sanitation.networkdriver.common.BasePresenter.REQUEST_SUCCESS;

/**
 * @author lq
 * @fileName 查询压缩站
 * @data on  2019/7/25 15:41
 * @describe TODO
 */
public class UserInfoPresenter implements ICommonAction, CheckingContract.CheckingPresenter {

    private CommonPresenter commonPresenter;
    private CheckingContract.CheckingView CheckingView;

    public UserInfoPresenter(CheckingContract.CheckingView checkingView) {
        CheckingView = checkingView;
        commonPresenter = new CommonPresenter(this);
    }


    @Override
    public void QueryCheckingState(String date) {
        HashMap<String, String> hashMap = new HashMap<>();
        UserInfo_Tab userInfo_tab = new Select().from(UserInfo_Tab.class).querySingle();
        hashMap.put("userId", userInfo_tab.id);
        hashMap.put("date", date);
        commonPresenter.invokeInterfaceObtainData(true, false, false, UrlContant.MySourcePart.part, UrlContant.MySourcePart.check_record,
                hashMap, new TypeToken<List<CheckRecord>>() {
                });
    }

    @Override
    public void WorkChecking(String date) {

    }


    @Override
    public void obtainData(Object data, String methodIndex, int status, Map<String, String> parameterMap, String Msg) {
        switch (methodIndex) {
            case UrlContant.MySourcePart.check_record:  //通过时间获取考勤记录
                if (status == REQUEST_SUCCESS) {//成功
                    List<CheckRecord> checkRecords = (List<CheckRecord>) data;
                    CheckingView.SuccessFinshByCheckRecord(checkRecords);
                } else {
                    CheckingView.Error(Msg);
                }


                break;

        }
    }
}
