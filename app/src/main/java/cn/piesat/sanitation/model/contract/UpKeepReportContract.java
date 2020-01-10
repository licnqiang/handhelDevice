package cn.piesat.sanitation.model.contract;

import java.util.Map;

import cn.piesat.sanitation.data.GasonLines;
import cn.piesat.sanitation.data.UpKeepList;


/**
 * Created by sen.luo on 2020/1/2.
 */
public interface UpKeepReportContract {


    interface getUpkeepReportAddIView{
        void Error(String msg);
        void SuccessOnReportAdd(Object o);

        void ApproveError(String msg);
        void ApproveSuccess(Object o);

    }

    interface getUpkeepReportSIView{
        void SuccessOnReportList(UpKeepList upKeepList);
        void Error(String msg);
    }

    interface getUpkeepIsReportSIView{
        void SuccessOnReportList(UpKeepList upKeepList);
        void Error(String msg);
    }

    interface getUpkeepNoReportSIView{
        void SuccessOnReportList(UpKeepList upKeepList);
        void Error(String msg);
    }


    interface ReportUpkeepPresenter{
        void getUpkeepReportAdd(Map<String, String> map);
        void getUpkeepNoReportList(int curren);
        void getUpkeepIsReportList(int curren);
        void getUpkeepReportList(int curren);
        void getApporveIdReport(Map<String, String> map); //获取审批id
    }
}
