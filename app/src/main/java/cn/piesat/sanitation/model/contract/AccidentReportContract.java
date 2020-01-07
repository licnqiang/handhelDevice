package cn.piesat.sanitation.model.contract;

import java.util.Map;

public interface AccidentReportContract {

    interface GetAccidentAddIView{
        void error(String msg);
        void successOnAccidentAdd(String msg);
        void successOnAccidentReportDetail(Object o);
    }

    interface GetAccidentReportIView{
        void successOnAccidentList();
        void error(String msg);
        void successOnAccidentDelete(String msg);
    }
    interface ReportAccidentPresenter{
        void getAccidentReportList(Map<String,String>map);
        void getAccidentReportAdd(Map<String,String>map);
        void getAccidentReportDetail(Map<String,String>map);
        void getAccidentReportDelete(Map<String,String>map);
    }
}
