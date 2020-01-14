package cn.piesat.sanitation.model.contract;

import java.util.List;

import cn.piesat.sanitation.data.ApprovalStateBean;


/**
 * 审核
 */
public interface ApprovalContract {


    interface ApprovalView{
        void Error(String msg);
        void SuccessOnReport(Object object);

        void ApprovalStateError(String msg);
        void ApprovalStateSuccess(List<ApprovalStateBean> approvalStates);
        void UpdateSuccess(Object object);
    }



    interface ApprovalPresenter{
        void approvalHandlePass(String appFlowInstId);
        void approvalHandleTurn(String appFlowInstId,String msg);
        void approvalStateById(String appFlowInstId);
        void upKeepUpDate(String appFlowInstId,String Id); //保养更新
        void maintainUpDate(String appFlowInstId,String Id); //维修更新
        void insuranceUpDate(String appFlowInstId,String Id); //年检更新
    }
}
