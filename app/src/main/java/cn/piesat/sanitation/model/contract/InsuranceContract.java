package cn.piesat.sanitation.model.contract;

import java.util.Map;

import cn.piesat.sanitation.data.InsuranceBean;

public interface InsuranceContract {

    interface GetInsuranceAddIView{
        void insuranceError(String msg);
        void getInsuranceDetailSuccess(InsuranceBean bean);
        void getAddInsuranceSuccess(String msg);
        void getReportProcessId(Object o);
    }
    interface GetInsuranceListIView{
        void error(String msg);
        void getInsuranceListOnSuccess(InsuranceBean insuranceBean);
    }


    interface GetInsurancePresenter{
        void getInsuranceAdd(Map<String,String>map);
        void getInsuranceDetail(Map<String,String>map);
        void getInsuranceList(Map<String,String>map);
        void getReportProcess(Map<String,String>map);
    }

}
