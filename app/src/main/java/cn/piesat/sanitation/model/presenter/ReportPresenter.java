package cn.piesat.sanitation.model.presenter;

import com.google.gson.reflect.TypeToken;

import java.util.Map;

import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.ViolateReportListBean;
import cn.piesat.sanitation.model.contract.ReportContract;
import cn.piesat.sanitation.networkdriver.common.CommonPresenter;
import cn.piesat.sanitation.networkdriver.common.ICommonAction;

import static cn.piesat.sanitation.networkdriver.common.BasePresenter.REQUEST_SUCCESS;

/**
 * Created by sen.luo on 2020/1/2.
 */
public class ReportPresenter implements ICommonAction, ReportContract.ReportViolatePresenter {
    private CommonPresenter commonPresenter;
    private ReportContract.getViolateReportIView reportIView;
    private ReportContract.getReportAddIView reportAddIView;


    public ReportPresenter(ReportContract.getReportAddIView reportAddIView) {
        this.reportAddIView = reportAddIView;
        commonPresenter=new CommonPresenter(this);

    }

    public ReportPresenter(ReportContract.getViolateReportIView reportIView) {
        this.reportIView = reportIView;
        commonPresenter=new CommonPresenter(this);
    }

    /**
     * 违章上报列表
     * @param map
     */
    @Override
    public void getViolateReportList(Map<String, String> map) {

    }

    /**
     * 新增违章上报
     * @param map
     */
    @Override
    public void getViolateReportAdd(Map<String, String> map) {
        commonPresenter.invokeInterfaceObtainData(true, false, true,false, UrlContant.MySourcePart.violate_report_get,UrlContant.MySourcePart.violate_report_add
                ,map,new TypeToken<Object>(){});
    }

    /**
     * 获取违章上报详情
     * @param map
     */
    @Override
    public void getViolateReportDetail(Map<String, String> map) {

    }

    /**
     * 违章上报删除
     * @param map
     */
    @Override
    public void getViolateReportDelete(Map<String, String> map) {

    }

    @Override
    public void obtainData(Object data, String methodIndex, int status, Map<String, String> parameterMap, String Msg) {
        switch (methodIndex){
            //列表
            case UrlContant.MySourcePart.violate_report_list:
                if (status==REQUEST_SUCCESS){
                    reportIView.SuccessOnReportList((ViolateReportListBean) data);
                }else {
                    reportIView.Error(Msg);
                }

                break;

                //新增
            case UrlContant.MySourcePart.violate_report_add:
                if (status==REQUEST_SUCCESS){
                    reportAddIView.SuccessOnReportAdd(data);
                }else {
                    reportAddIView.Error(Msg);
                }
                break;
        }
    }
}
