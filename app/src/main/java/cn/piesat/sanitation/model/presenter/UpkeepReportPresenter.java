package cn.piesat.sanitation.model.presenter;

import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.GasonLines;
import cn.piesat.sanitation.data.UpKeepList;
import cn.piesat.sanitation.model.contract.GasonLineReportContract;
import cn.piesat.sanitation.model.contract.UpKeepReportContract;
import cn.piesat.sanitation.networkdriver.common.CommonPresenter;
import cn.piesat.sanitation.networkdriver.common.ICommonAction;

import static cn.piesat.sanitation.networkdriver.common.BasePresenter.REQUEST_SUCCESS;

/**
 * Created by sen.luo on 2020/1/2.
 */
public class UpkeepReportPresenter implements ICommonAction, UpKeepReportContract.ReportUpkeepPresenter {
    private CommonPresenter commonPresenter;
    private UpKeepReportContract.getUpkeepReportAddIView UpkeepReportAddIView;
    private UpKeepReportContract.getUpkeepIsReportSIView UpkeepIsReportSIView;
    private UpKeepReportContract.getUpkeepNoReportSIView UpkeepNoReportSIView;


    public UpkeepReportPresenter(UpKeepReportContract.getUpkeepReportAddIView getUpkeepReportAddIView) {
        this.UpkeepReportAddIView = getUpkeepReportAddIView;
        commonPresenter=new CommonPresenter(this);
    }

    public UpkeepReportPresenter(UpKeepReportContract.getUpkeepIsReportSIView UpkeepIsReportSIView) {
        this.UpkeepIsReportSIView = UpkeepIsReportSIView;
        commonPresenter=new CommonPresenter(this);
    }

    public UpkeepReportPresenter(UpKeepReportContract.getUpkeepNoReportSIView getUpkeepNoReportSIView) {
        this.UpkeepNoReportSIView = getUpkeepNoReportSIView;
        commonPresenter=new CommonPresenter(this);
    }




    /**
     * 新增保养上报
     * @param map
     */
    @Override
    public void getUpkeepReportAdd(Map<String, String> map) {
        commonPresenter.invokeInterfaceObtainData(true, false, true,true, "",UrlContant.MySourcePart.add_upkeep_report
                ,map,new TypeToken<Object>(){});
    }

    /**
     * 获取未审批列表
     * @param curren
     */
    @Override
    public void getUpkeepNoReportList(int curren) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("curren", curren+"");
        hashMap.put("size", "20"); //每页显示20条
        hashMap.put("siteName", BaseApplication.getUserInfo().deptNameCount); //站点名称
        hashMap.put("roleId", BaseApplication.getUserInfo().userType+""); //角色id
        hashMap.put("userId", BaseApplication.getUserInfo().id); //用户id
        commonPresenter.invokeInterfaceObtainData(true, false, true,true, "",UrlContant.MySourcePart.upkeep_approval_no
                ,hashMap,new TypeToken<UpKeepList>(){});
    }

    /**
     * 获取审批列表
     * @param curren
     */
    @Override
    public void getUpkeepIsReportList(int curren) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("curren", curren+"");
        hashMap.put("size ", "20"); //每页显示20条
        commonPresenter.invokeInterfaceObtainData(true, false, true,true, "",UrlContant.MySourcePart.upkeep_approval_is
                ,hashMap,new TypeToken<UpKeepList>(){});
    }


    /**
     * 获取审批id
     * @param map
     */
    @Override
    public void getApporveIdReport(Map<String, String> map) {
        commonPresenter.invokeInterfaceObtainData(true, false, true,true, "",UrlContant.MySourcePart.start_report
                ,map,new TypeToken<String>(){});
    }


    @Override
    public void obtainData(Object data, String methodIndex, int status, Map<String, String> parameterMap, String Msg) {
        switch (methodIndex){
            //保养新增
            case UrlContant.MySourcePart.add_upkeep_report:
                if (status==REQUEST_SUCCESS){
                    UpkeepReportAddIView.SuccessOnReportAdd(data);
                }else {
                    UpkeepReportAddIView.Error(Msg);
                }
                break;
            //保养未审批列表
            case UrlContant.MySourcePart.upkeep_approval_no:
                if (status==REQUEST_SUCCESS){
                    UpKeepList upKeepList=(UpKeepList)data;
                    UpkeepNoReportSIView.SuccessOnReportList(upKeepList);
                }else {
                    UpkeepNoReportSIView.Error(Msg);
                }
                break;
            //保养已审批列表
            case UrlContant.MySourcePart.upkeep_approval_is:
                if (status==REQUEST_SUCCESS){
                    UpKeepList upKeepList=(UpKeepList)data;
                    UpkeepIsReportSIView.SuccessOnReportList(upKeepList);
                }else {
                    UpkeepIsReportSIView.Error(Msg);
                }
                break;
            //获取审批id
            case UrlContant.MySourcePart.start_report:
                if (status==REQUEST_SUCCESS){
                    String approveId=(String)data;
                    UpkeepReportAddIView.ApproveSuccess(approveId);
                }else {
                    UpkeepReportAddIView.ApproveError(Msg);
                }
                break;
        }
    }

}
