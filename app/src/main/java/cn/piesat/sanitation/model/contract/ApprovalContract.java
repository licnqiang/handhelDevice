package cn.piesat.sanitation.model.contract;

import java.util.Map;

import cn.piesat.sanitation.data.ViolateReportBean;


/**
 * 审核
 */
public interface ApprovalContract {


    interface ApprovalView{
        void Error(String msg);
        void SuccessOnReport(Object object);
    }


    interface ApprovalPresenter{
        void approvalHandlePass(String appFlowInstId);
        void approvalHandleTurn(String appFlowInstId,String msg);

    }
}
