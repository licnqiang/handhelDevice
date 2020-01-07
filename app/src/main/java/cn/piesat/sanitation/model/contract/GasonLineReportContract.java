package cn.piesat.sanitation.model.contract;

import java.util.Map;

import cn.piesat.sanitation.data.GasonLines;
import cn.piesat.sanitation.data.ViolateReportListBean;


/**
 * Created by sen.luo on 2020/1/2.
 */
public interface GasonLineReportContract {


    interface getGasonLineReportAddIView{
        void Error(String msg);
        void SuccessOnReportAdd(Object o);
    }


    interface getGasonLineReportSIView{
        void SuccessOnReportList(GasonLines gasonLines);
        void Error(String msg);
    }


    interface ReportViolatePresenter{
        void getGosolineReportAdd(Map<String, String> map);
        void getGosolineReportList(int curren);
    }
}
