package cn.piesat.sanitation.model.contract;

import java.util.Map;

import cn.piesat.sanitation.data.ViolateReportListBean;


/**
 * Created by sen.luo on 2020/1/2.
 */
public interface ReportContract {


    interface getReportAddIView{
        void Error(String msg);
        void SuccessOnReportAdd(Object o);
    }



    interface getViolateReportIView{
        void SuccessOnReportList(ViolateReportListBean bean);
        void Error(String msg);
        void SuccessOnReportDelete(Object o);
    }


    interface ReportViolatePresenter{
        void getViolateReportList(Map<String,String> map);
        void getViolateReportAdd(Map<String,String> map);
        void getViolateReportDetail(Map<String,String>map);
        void getViolateReportDelete(Map<String,String>map);
    }
}
