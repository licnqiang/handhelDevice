package cn.piesat.sanitation.model.contract;

import java.util.List;
import java.util.Map;

import cn.piesat.sanitation.data.AccidentReportBean;

public interface AccidentReportContract {

    interface GetAccidentAddIView{
        void error(String msg);
        void successOnAccidentAdd(String msg);
        void successOnAccidentReportDetail(AccidentReportBean.AccidentListBean accidentListBean);
    }

    interface GetAccidentReportIView{
        void successOnAccidentList(AccidentReportBean beans);
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
