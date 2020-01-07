package cn.piesat.sanitation.model.presenter;

import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.AccidentReportBean;
import cn.piesat.sanitation.data.ViolateReportBean;
import cn.piesat.sanitation.model.contract.AccidentReportContract;
import cn.piesat.sanitation.model.contract.ReportContract;
import cn.piesat.sanitation.networkdriver.common.CommonPresenter;
import cn.piesat.sanitation.networkdriver.common.ICommonAction;

import static cn.piesat.sanitation.networkdriver.common.BasePresenter.REQUEST_SUCCESS;

public class AccidentReportPresenter implements ICommonAction, AccidentReportContract.ReportAccidentPresenter {


    private CommonPresenter commonPresenter;
    private AccidentReportContract.GetAccidentAddIView accidentAddIView;
    private AccidentReportContract.GetAccidentReportIView accidentReportIView;


    public AccidentReportPresenter(AccidentReportContract.GetAccidentAddIView accidentAddIView) {
        this.accidentAddIView = accidentAddIView;
        commonPresenter=new CommonPresenter(this);
    }

    public AccidentReportPresenter(AccidentReportContract.GetAccidentReportIView accidentReportIView) {
        this.accidentReportIView = accidentReportIView;
        commonPresenter=new CommonPresenter(this);
    }

    /**
     * 事故上报列表
     * @param map
     */
    @Override
    public void getAccidentReportList(Map<String, String> map) {
        commonPresenter.invokeInterfaceObtainData(true, false, false,false, UrlContant.MySourcePart.accident_report_get,UrlContant.MySourcePart.accident_report_list
                ,map,new TypeToken<AccidentReportBean>(){});
    }

    /**
     * 事故上报新增
     * @param map
     */
    @Override
    public void getAccidentReportAdd(Map<String, String> map) {
        commonPresenter.invokeInterfaceObtainData(true, false, true,true, UrlContant.MySourcePart.accident_report_get,UrlContant.MySourcePart.accident_report_add
                ,map,new TypeToken<Object>(){});
    }

    /**
     * 事故上报详情
     * @param map
     */
    @Override
    public void getAccidentReportDetail(Map<String, String> map) {
        commonPresenter.invokeInterfaceObtainData(true, false, false,false, UrlContant.MySourcePart.accident_report_get,UrlContant.MySourcePart.accident_report_select_id
                ,map,new TypeToken<AccidentReportBean.AccidentListBean>(){});
    }

    /**
     * 事故上报删除
     * @param map
     */
    @Override
    public void getAccidentReportDelete(Map<String, String> map) {

    }

    @Override
    public void obtainData(Object data, String methodIndex, int status, Map<String, String> parameterMap, String Msg) {
        switch (methodIndex){
                //新增
            case UrlContant.MySourcePart.accident_report_add:
                if (status==REQUEST_SUCCESS){
                    accidentAddIView.successOnAccidentAdd(Msg);
                }else {
                    accidentAddIView.error(Msg);
                }

                break;

                //列表
            case UrlContant.MySourcePart.accident_report_list:
                if (status==REQUEST_SUCCESS){
                    accidentReportIView.successOnAccidentList((AccidentReportBean) data);
                }else {
                    accidentReportIView.error(Msg);
                }

                break;
            //详情
            case UrlContant.MySourcePart.accident_report_select_id:
                if (status==REQUEST_SUCCESS){
                    accidentAddIView.successOnAccidentReportDetail((AccidentReportBean.AccidentListBean) data);
                }else {
                    accidentAddIView.error(Msg);
                }
                break;



        }
    }
}
