package cn.piesat.sanitation.model.presenter;

import com.google.gson.reflect.TypeToken;

import java.util.Map;

import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.model.contract.AccidentReportContract;
import cn.piesat.sanitation.model.contract.ReportContract;
import cn.piesat.sanitation.networkdriver.common.CommonPresenter;
import cn.piesat.sanitation.networkdriver.common.ICommonAction;

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

            case UrlContant.MySourcePart.accident_report_add:
                break;

            case UrlContant.MySourcePart.accident_report_list:
                break;
            case UrlContant.MySourcePart.accident_report_select_id:
                break;

        }
    }
}
