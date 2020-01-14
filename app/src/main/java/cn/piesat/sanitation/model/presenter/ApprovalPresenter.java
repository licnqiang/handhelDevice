package cn.piesat.sanitation.model.presenter;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.ApprovalStateBean;
import cn.piesat.sanitation.model.contract.ApprovalContract;
import cn.piesat.sanitation.networkdriver.common.CommonPresenter;
import cn.piesat.sanitation.networkdriver.common.ICommonAction;
import cn.piesat.sanitation.util.SpHelper;

import static cn.piesat.sanitation.networkdriver.common.BasePresenter.REQUEST_SUCCESS;

/**
 * Created by sen.luo on 2020/1/2.
 */
public class ApprovalPresenter implements ICommonAction, ApprovalContract.ApprovalPresenter {
    private CommonPresenter commonPresenter;
    private ApprovalContract.ApprovalView approvalView;


    public ApprovalPresenter(ApprovalContract.ApprovalView approvalView) {
        this.approvalView = approvalView;
        commonPresenter = new CommonPresenter(this);
    }


    @Override
    public void approvalHandlePass(String appFlowInstId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userName", BaseApplication.getUserInfo().name);
        hashMap.put("userType", SpHelper.getStringValue(SysContant.userInfo.USER_ROLE_ID)); //角色id
        hashMap.put("roleName", SpHelper.getStringValue(SysContant.userInfo.USER_ROLE_NAME)); //角色名
        hashMap.put("userId", SpHelper.getStringValue(SysContant.userInfo.USER_ID)); //用户id
        hashMap.put("apprContent", ""); //内容
        hashMap.put("apprResult", "T"); //T 通过 F 未通过 R 驳回
        hashMap.put("appFlowInstId", appFlowInstId); //审批id
        commonPresenter.invokeInterfaceObtainData(true, false, true, true, "", UrlContant.MySourcePart.approval_hanlder
                , hashMap, new TypeToken<String>() {
                });
    }

    @Override
    public void approvalHandleTurn(String appFlowInstId, String msg) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userName", BaseApplication.getUserInfo().name);
        hashMap.put("userType", SpHelper.getStringValue(SysContant.userInfo.USER_ROLE_ID)); //角色id
        hashMap.put("roleName", SpHelper.getStringValue(SysContant.userInfo.USER_ROLE_NAME)); //角色名
        hashMap.put("userId", SpHelper.getStringValue(SysContant.userInfo.USER_ID)); //用户id
        hashMap.put("apprContent", msg); //内容
        hashMap.put("apprResult", "R"); //T 通过 F 未通过 R 驳回
        hashMap.put("appFlowInstId", appFlowInstId); //审批id

        commonPresenter.invokeInterfaceObtainData(true, false, true, true, "", UrlContant.MySourcePart.approval_hanlder
                , hashMap, new TypeToken<String>() {
                });
    }

    @Override
    public void approvalStateById(String appFlowInstId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("appFlowInstId", appFlowInstId); //审批id
        commonPresenter.invokeInterfaceObtainData(true, false, true, true, "", UrlContant.MySourcePart.approval_state
                , hashMap, new TypeToken<List<ApprovalStateBean>>() {
                });
    }

    /**
     * 保养更新
     *
     * @param appFlowInstId
     * @param Id
     */
    @Override
    public void upKeepUpDate(String appFlowInstId, String Id) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", Id); //审批id
        hashMap.put("approval", appFlowInstId); //审批id
        commonPresenter.invokeInterfaceObtainData(true, false, true, true, "", UrlContant.MySourcePart.approval_upkeep_update
                , hashMap, new TypeToken<List<ApprovalStateBean>>() {
                });
    }

    /**
     * 维修更新
     *
     * @param appFlowInstId
     * @param Id
     */
    @Override
    public void maintainUpDate(String appFlowInstId, String Id) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", Id); //审批id
        hashMap.put("approval", appFlowInstId); //审批id
        commonPresenter.invokeInterfaceObtainData(true, false, true, true, "", UrlContant.MySourcePart.approval_maintain_update
                , hashMap, new TypeToken<List<ApprovalStateBean>>() {
                });
    }

    /**
     * 年检更新
     *
     * @param appFlowInstId
     * @param Id
     */
    @Override
    public void insuranceUpDate(String appFlowInstId, String Id) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", Id); //审批id
        hashMap.put("approval", appFlowInstId); //审批id
        commonPresenter.invokeInterfaceObtainData(true, false, true, true, "", UrlContant.MySourcePart.approval_insuracne_update
                , hashMap, new TypeToken<List<ApprovalStateBean>>() {
                });
    }


    @Override
    public void obtainData(Object data, String methodIndex, int status, Map<String, String> parameterMap, String Msg) {
        switch (methodIndex) {
            //审批
            case UrlContant.MySourcePart.approval_hanlder:
                String approid=(String)data;
                if (status == REQUEST_SUCCESS) {
                    approvalView.SuccessOnReport(approid);
                } else {
                    approvalView.Error(Msg);
                }
                break;
            //审批情况
            case UrlContant.MySourcePart.approval_state:
                if (status == REQUEST_SUCCESS) {
                    List<ApprovalStateBean> approvalStates = (List<ApprovalStateBean>) data;
                    approvalView.ApprovalStateSuccess(approvalStates);
                } else {
                    approvalView.ApprovalStateError(Msg);
                }
                break;

            //年检更新
            case UrlContant.MySourcePart.approval_insuracne_update:
                if (status == REQUEST_SUCCESS) {
                    approvalView.UpdateSuccess(null);
                } else {
                    approvalView.Error(Msg);
                }
                break;
            //维修更新
            case UrlContant.MySourcePart.approval_maintain_update:
                if (status == REQUEST_SUCCESS) {
                    approvalView.UpdateSuccess(null);
                } else {
                    approvalView.Error(Msg);
                }
                break;
            //保养更新
            case UrlContant.MySourcePart.approval_upkeep_update:
                if (status == REQUEST_SUCCESS) {
                    approvalView.UpdateSuccess(null);
                } else {
                    approvalView.Error(Msg);
                }
                break;
        }
    }

}
