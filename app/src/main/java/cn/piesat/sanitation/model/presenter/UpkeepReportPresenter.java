package cn.piesat.sanitation.model.presenter;

import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.GasonLines;
import cn.piesat.sanitation.data.UpKeepList;
import cn.piesat.sanitation.model.contract.GasonLineReportContract;
import cn.piesat.sanitation.model.contract.UpKeepReportContract;
import cn.piesat.sanitation.networkdriver.common.CommonPresenter;
import cn.piesat.sanitation.networkdriver.common.ICommonAction;
import cn.piesat.sanitation.util.RoleIdSortUtil;
import cn.piesat.sanitation.util.SpHelper;

import static cn.piesat.sanitation.networkdriver.common.BasePresenter.REQUEST_SUCCESS;

/**
 * Created by sen.luo on 2020/1/2.
 */
public class UpkeepReportPresenter implements ICommonAction, UpKeepReportContract.ReportUpkeepPresenter {
    private CommonPresenter commonPresenter;
    private UpKeepReportContract.getUpkeepReportAddIView UpkeepReportAddIView;
    private UpKeepReportContract.getUpkeepIsReportSIView UpkeepIsReportSIView;
    private UpKeepReportContract.getUpkeepNoReportSIView UpkeepNoReportSIView;
    private UpKeepReportContract.getUpkeepReportSIView UpkeepReportSIView;


    public UpkeepReportPresenter(UpKeepReportContract.getUpkeepReportAddIView getUpkeepReportAddIView) {
        this.UpkeepReportAddIView = getUpkeepReportAddIView;
        commonPresenter = new CommonPresenter(this);
    }

    public UpkeepReportPresenter(UpKeepReportContract.getUpkeepIsReportSIView UpkeepIsReportSIView) {
        this.UpkeepIsReportSIView = UpkeepIsReportSIView;
        commonPresenter = new CommonPresenter(this);
    }

    public UpkeepReportPresenter(UpKeepReportContract.getUpkeepNoReportSIView getUpkeepNoReportSIView) {
        this.UpkeepNoReportSIView = getUpkeepNoReportSIView;
        commonPresenter = new CommonPresenter(this);
    }


    public UpkeepReportPresenter(UpKeepReportContract.getUpkeepReportSIView getUpkeepReportSIView) {
        this.UpkeepReportSIView = getUpkeepReportSIView;
        commonPresenter = new CommonPresenter(this);
    }


    /**
     * ??????????????????
     *
     * @param map
     */
    @Override
    public void getUpkeepReportAdd(Map<String, String> map) {
        commonPresenter.invokeInterfaceObtainData(true, false, true, true, "", UrlContant.MySourcePart.add_upkeep_report
                , map, new TypeToken<Object>() {
                });
    }

    /**
     * ?????????????????????
     *
     * @param curren
     */
    @Override
    public void getUpkeepNoReportList(int curren) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("curren", curren + "");
        hashMap.put("size", SysContant.CommentTag.pageSize); //????????????20???
        if("3".equals(SpHelper.getStringValue(SysContant.userInfo.USER_ROLE_ID))){
            hashMap.put("siteName", BaseApplication.getUserInfo().deptNameCount); //????????????
        }
        hashMap.put("roleId", BaseApplication.getUserInfo().userType + ""); //??????id
        hashMap.put("userId", BaseApplication.getUserInfo().id); //??????id
        commonPresenter.invokeInterfaceObtainData(true, false, true, true, "", UrlContant.MySourcePart.upkeep_approval_no
                , hashMap, new TypeToken<UpKeepList>() {
                });
    }

    /**
     * ?????????????????????
     *
     * @param curren
     */
    @Override
    public void getUpkeepIsReportList(int curren) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("curren", curren + "");
        hashMap.put("size ", "20"); //????????????20???
        if("3".equals(SpHelper.getStringValue(SysContant.userInfo.USER_ROLE_ID))){
            hashMap.put("siteName", BaseApplication.getUserInfo().deptNameCount); //????????????
        }
        hashMap.put("roleId", BaseApplication.getUserInfo().userType + ""); //??????id
        hashMap.put("userId", BaseApplication.getUserInfo().id); //??????id
        commonPresenter.invokeInterfaceObtainData(true, false, true, true, "", UrlContant.MySourcePart.upkeep_approval_is
                , hashMap, new TypeToken<UpKeepList>() {
                });
    }


    /**
     * ????????????
     *
     * @param curren
     */
    @Override
    public void getUpkeepReportList(int curren) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("curren", curren + "");
        hashMap.put("size ", "20"); //????????????20???

        if(BaseApplication.getUserInfo().userType!=3){
            hashMap.put("siteName", BaseApplication.getUserInfo().deptNameCount); //????????????
        }

        String roleId = RoleIdSortUtil.getMinRoleId(BaseApplication.getIns().getUserRoleIdList());
        hashMap.put("roleId", roleId); //??????id
        hashMap.put("userId", SpHelper.getStringValue(SysContant.userInfo.USER_ID)); //??????id
        commonPresenter.invokeInterfaceObtainData(true, false, true, true, "", UrlContant.MySourcePart.upkeep_approval_list
                , hashMap, new TypeToken<UpKeepList>() {
                });
    }


    /**
     * ????????????id
     *
     * @param map
     */
    @Override
    public void getApporveIdReport(Map<String, String> map) {
        commonPresenter.invokeInterfaceObtainData(true, false, true, true, "", UrlContant.MySourcePart.start_report
                , map, new TypeToken<String>() {
                });
    }


    @Override
    public void obtainData(Object data, String methodIndex, int status, Map<String, String> parameterMap, String Msg) {
        switch (methodIndex) {
            //????????????
            case UrlContant.MySourcePart.add_upkeep_report:
                if (status == REQUEST_SUCCESS) {
                    UpkeepReportAddIView.SuccessOnReportAdd(data);
                } else {
                    UpkeepReportAddIView.Error(Msg);
                }
                break;
            //?????????????????????
            case UrlContant.MySourcePart.upkeep_approval_no:
                if (status == REQUEST_SUCCESS) {
                    UpKeepList upKeepList = (UpKeepList) data;
                    UpkeepNoReportSIView.SuccessOnReportList(upKeepList);
                } else {
                    UpkeepNoReportSIView.Error(Msg);
                }
                break;
            //?????????????????????
            case UrlContant.MySourcePart.upkeep_approval_is:
                if (status == REQUEST_SUCCESS) {
                    UpKeepList upKeepList = (UpKeepList) data;
                    UpkeepIsReportSIView.SuccessOnReportList(upKeepList);
                } else {
                    UpkeepIsReportSIView.Error(Msg);
                }
                break;
            //????????????
            case UrlContant.MySourcePart.upkeep_approval_list:
                if (status == REQUEST_SUCCESS) {
                    UpKeepList upKeepList = (UpKeepList) data;
                    UpkeepReportSIView.SuccessOnReportList(upKeepList);
                } else {
                    UpkeepReportSIView.Error(Msg);
                }
                break;
            //????????????id
            case UrlContant.MySourcePart.start_report:
                if (status == REQUEST_SUCCESS) {
                    String approveId = (String) data;
                    UpkeepReportAddIView.ApproveSuccess(approveId);
                } else {
                    UpkeepReportAddIView.ApproveError(Msg);
                }
                break;
        }
    }

}
