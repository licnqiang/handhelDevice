package cn.piesat.sanitation.model.contract;

import java.util.Map;

import cn.piesat.sanitation.data.MaintainList;
import cn.piesat.sanitation.data.UpKeepList;


/**
 * Created by sen.luo on 2020/1/2.
 */
public interface MaintainReportContract {


    interface getMaintainReportAddIView{
        void Error(String msg);
        void SuccessOnReportAdd(Object o);

        void ApproveError(String msg);
        void ApproveSuccess(Object o);

    }


    interface getMaintainIsReportSIView{
        void SuccessOnReportList(MaintainList maintainList);
        void Error(String msg);
    }

    interface getMaintainNoReportSIView {
        void SuccessOnReportList(MaintainList maintainList);

        void Error(String msg);
    }

    interface getMaintainReportSIView{
        void SuccessOnReportList(MaintainList maintainList);
        void Error(String msg);
    }


    interface ReportMaintainPresenter{
        void getMaintainReportAdd(Map<String, String> map);
        void getMaintainNoReportList(int curren);
        void getMaintainIsReportList(int curren);
        void getMaintainReportList(int curren);
        void getApporveIdReport(Map<String, String> map); //获取审批id
    }
}

