package cn.piesat.sanitation.model.presenter;

import com.google.gson.reflect.TypeToken;

import java.util.Map;

import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.InsuranceBean;
import cn.piesat.sanitation.model.contract.InsuranceContract;
import cn.piesat.sanitation.networkdriver.common.CommonPresenter;
import cn.piesat.sanitation.networkdriver.common.ICommonAction;

import static cn.piesat.sanitation.networkdriver.common.BasePresenter.REQUEST_SUCCESS;

public class InsurancePresenter implements ICommonAction, InsuranceContract.GetInsurancePresenter {


    private CommonPresenter commonPresenter;
    private InsuranceContract.GetInsuranceAddIView insuranceAddIView;
    private InsuranceContract.GetInsuranceListIView insuranceListIView;


    public InsurancePresenter(InsuranceContract.GetInsuranceAddIView insuranceAddIView) {
        this.insuranceAddIView = insuranceAddIView;
        commonPresenter=new CommonPresenter(this);
    }

    public InsurancePresenter(InsuranceContract.GetInsuranceListIView insuranceListIView) {
        this.insuranceListIView = insuranceListIView;
        commonPresenter=new CommonPresenter(this);
    }

    /**
     * 新增保险年检
     * @param map
     */
    @Override
    public void getInsuranceAdd(Map<String, String> map) {
        commonPresenter.invokeInterfaceObtainData(true, false, true,true, "", UrlContant.MySourcePart.insuracne_add
                ,map,new TypeToken<Object>(){});
    }

    /**
     * 保险年检详情
     * @param map
     */
    @Override
    public void getInsuranceDetail(Map<String, String> map) {
        commonPresenter.invokeInterfaceObtainData(true, false, false,false, UrlContant.MySourcePart.insuracne_get,UrlContant.MySourcePart.insuracne_detail
                ,map,new TypeToken<InsuranceBean>(){});

    }

    /**
     * 保险年检列表
     * @param map
     */

    @Override
    public void getInsuranceList(Map<String, String> map) {
        commonPresenter.invokeInterfaceObtainData(true, false, true,false, "",UrlContant.MySourcePart.insuracne_list
                ,map,new TypeToken<InsuranceBean>(){});
    }

    /**
     * 获取审批流程id
     * @param map
     */
    @Override
    public void getReportProcess(Map<String, String> map) {
        commonPresenter.invokeInterfaceObtainData(true, false, true,true, "",UrlContant.MySourcePart.start_report
                ,map,new TypeToken<String>(){});
    }

    @Override
    public void obtainData(Object data, String methodIndex, int status, Map<String, String> parameterMap, String Msg) {
        switch (methodIndex){
            //获取审批流程id
            case UrlContant.MySourcePart.start_report:
                if (status==REQUEST_SUCCESS){
                    insuranceAddIView.getReportProcessId(data);
                }else {
                    insuranceAddIView.insuranceError(Msg);
                }
                break;
            //新增
            case UrlContant.MySourcePart.insuracne_add:
                if (status==REQUEST_SUCCESS){
                    insuranceAddIView.getAddInsuranceSuccess(Msg);
                }else {
                    insuranceAddIView.insuranceError(Msg);
                }
                break;
                //列表
            case  UrlContant.MySourcePart.insuracne_list:
                if (status==REQUEST_SUCCESS){
                    insuranceListIView.getInsuranceListOnSuccess((InsuranceBean) data);
                }else {
                    insuranceListIView.error(Msg);
                }
                break;
                //详情
            case UrlContant.MySourcePart.insuracne_detail:
                if (status==REQUEST_SUCCESS){
                    insuranceAddIView.getInsuranceDetailSuccess((InsuranceBean) data);
                }else {
                    insuranceAddIView.insuranceError(Msg);
                }
                break;
        }
    }
}
