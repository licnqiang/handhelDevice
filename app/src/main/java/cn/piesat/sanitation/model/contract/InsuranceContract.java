package cn.piesat.sanitation.model.contract;

import java.util.Map;

public interface InsuranceContract {

    interface GetInsuranceAddIView{
        void insuranceError(String msg);
        void getInsuranceDetailSuccess(Object o);
        void getAddInsuranceSuccess(String msg);
        void getReportProcessId(Object o);
    }
    interface GetInsuranceListIView{
        void error(String msg);
        void getInsuranceListOnSuccess(Object o);
    }


    interface GetInsurancePresenter{
        void getInsuranceAdd(Map<String,String>map);
        void getInsuranceDetail(Map<String,String>map);
        void getInsuranceList(Map<String,String>map);
        void getReportProcess(Map<String,String>map);
    }

}
