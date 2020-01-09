package cn.piesat.sanitation.model.presenter;

import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.MaintainList;
import cn.piesat.sanitation.data.UpKeepList;
import cn.piesat.sanitation.model.contract.MaintainReportContract;
import cn.piesat.sanitation.model.contract.UpKeepReportContract;
import cn.piesat.sanitation.networkdriver.common.CommonPresenter;
import cn.piesat.sanitation.networkdriver.common.ICommonAction;

import static cn.piesat.sanitation.networkdriver.common.BasePresenter.REQUEST_SUCCESS;

/**
 * Created by sen.luo on 2020/1/2.
 */
public class MaintainReportPresenter implements ICommonAction, MaintainReportContract.ReportMaintainPresenter {
    private CommonPresenter commonPresenter;
    private MaintainReportContract.getMaintainReportAddIView MaintainReportAddIView;
    private MaintainReportContract.getMaintainIsReportSIView MaintainIsReportSIView;
    private MaintainReportContract.getMaintainNoReportSIView MaintainNoReportSIView;


    public MaintainReportPresenter(MaintainReportContract.getMaintainReportAddIView getMaintainReportAddIView) {
        this.MaintainReportAddIView = getMaintainReportAddIView;
        commonPresenter=new CommonPresenter(this);
    }

    public MaintainReportPresenter(MaintainReportContract.getMaintainIsReportSIView MaintainIsReportSIView) {
        this.MaintainIsReportSIView = MaintainIsReportSIView;
        commonPresenter=new CommonPresenter(this);
    }

    public MaintainReportPresenter(MaintainReportContract.getMaintainNoReportSIView getMaintainNoReportSIView) {
        this.MaintainNoReportSIView = getMaintainNoReportSIView;
        commonPresenter=new CommonPresenter(this);
    }




    /**
     * 新增维修上报
     * @param map
     */
    @Override
    public void getMaintainReportAdd(Map<String, String> map) {
        commonPresenter.invokeInterfaceObtainData(true, false, true,true, "",UrlContant.MySourcePart.add_maintain_report
                ,map,new TypeToken<Object>(){});
    }

    /**
     * 获取未审批列表
     * @param curren
     */
    @Override
    public void getMaintainNoReportList(int curren) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("curren", curren+"");
        hashMap.put("size", "20"); //每页显示20条
        hashMap.put("siteName", BaseApplication.getUserInfo().deptNameCount); //站点名称
        hashMap.put("roleId", BaseApplication.getUserInfo().userType+""); //角色id
        hashMap.put("userId", BaseApplication.getUserInfo().id); //用户id
        commonPresenter.invokeInterfaceObtainData(true, false, true,true, "",UrlContant.MySourcePart.maintain_approval_no
                ,hashMap,new TypeToken<MaintainList>(){});
    }

    /**
     * 获取审批列表
     * @param curren
     */
    @Override
    public void getMaintainIsReportList(int curren) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("curren", curren+"");
        hashMap.put("size ", "20"); //每页显示20条
        hashMap.put("siteName", BaseApplication.getUserInfo().deptNameCount); //站点名称
        hashMap.put("roleId", BaseApplication.getUserInfo().userType+""); //角色id
        hashMap.put("userId", BaseApplication.getUserInfo().id); //用户id
        commonPresenter.invokeInterfaceObtainData(true, false, true,true, "",UrlContant.MySourcePart.maintain_approval_is
                ,hashMap,new TypeToken<MaintainList>(){});
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
            //维修新增
            case UrlContant.MySourcePart.add_maintain_report:
                if (status==REQUEST_SUCCESS){
                    MaintainReportAddIView.SuccessOnReportAdd(data);
                }else {
                    MaintainReportAddIView.Error(Msg);
                }
                break;
            //维修未审批列表
            case UrlContant.MySourcePart.maintain_approval_no:
                if (status==REQUEST_SUCCESS){
                    MaintainList MaintainList=(MaintainList)data;
                    MaintainNoReportSIView.SuccessOnReportList(MaintainList);
                }else {
                    MaintainNoReportSIView.Error(Msg);
                }
                break;
            //维修已审批列表
            case UrlContant.MySourcePart.maintain_approval_is:
                if (status==REQUEST_SUCCESS){
                    MaintainList maintainList=(MaintainList)data;
                    MaintainIsReportSIView.SuccessOnReportList(maintainList);
                }else {
                    MaintainIsReportSIView.Error(Msg);
                }
                break;
            //获取审批id
            case UrlContant.MySourcePart.start_report:
                if (status==REQUEST_SUCCESS){
                    String approveId=(String)data;
                    MaintainReportAddIView.ApproveSuccess(approveId);
                }else {
                    MaintainReportAddIView.ApproveError(Msg);
                }
                break;
        }
    }

}
