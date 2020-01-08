package cn.piesat.sanitation.model.presenter;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;
import java.util.Map;
import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.GasonLines;
import cn.piesat.sanitation.model.contract.GasonLineReportContract;
import cn.piesat.sanitation.networkdriver.common.CommonPresenter;
import cn.piesat.sanitation.networkdriver.common.ICommonAction;

import static cn.piesat.sanitation.networkdriver.common.BasePresenter.REQUEST_SUCCESS;

/**
 * Created by sen.luo on 2020/1/2.
 */
public class GasonLineReportPresenter implements ICommonAction, GasonLineReportContract.ReportViolatePresenter {
    private CommonPresenter commonPresenter;
    private GasonLineReportContract.getGasonLineReportAddIView gasonLineReportAddIView;
    private GasonLineReportContract.getGasonLineReportSIView getGasonLineReportSIView;


    public GasonLineReportPresenter(GasonLineReportContract.getGasonLineReportAddIView gasonLineReportAddIView) {
        this.gasonLineReportAddIView = gasonLineReportAddIView;
        commonPresenter=new CommonPresenter(this);
    }

    public GasonLineReportPresenter(GasonLineReportContract.getGasonLineReportSIView getGasonLineReportSIView) {
        this.getGasonLineReportSIView = getGasonLineReportSIView;
        commonPresenter=new CommonPresenter(this);
    }



    /**
     * 新增加油上报
     * @param map
     */
    @Override
    public void getGosolineReportAdd(Map<String, String> map) {
        commonPresenter.invokeInterfaceObtainData(true, false, true,true, UrlContant.MySourcePart.gasoline_report_get,UrlContant.MySourcePart.gasoline_report_add
                ,map,new TypeToken<Object>(){});
    }

    /**
     * 加油上报列表
     */
    @Override
    public void getGosolineReportList(int curren) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("curren", curren+"");
        hashMap.put("size ", "20"); //每页显示20条
        commonPresenter.invokeInterfaceObtainData(true, false, true,true, UrlContant.MySourcePart.gasoline_report_get,UrlContant.MySourcePart.gasoline_report_list
                ,hashMap,new TypeToken<GasonLines>(){});
    }


    @Override
    public void obtainData(Object data, String methodIndex, int status, Map<String, String> parameterMap, String Msg) {
        switch (methodIndex){
            //加油新增
            case UrlContant.MySourcePart.gasoline_report_add:
                if (status==REQUEST_SUCCESS){
                    gasonLineReportAddIView.SuccessOnReportAdd(data);
                }else {
                    gasonLineReportAddIView.Error(Msg);
                }
                break;
            //加油列表
            case UrlContant.MySourcePart.gasoline_report_list:
                if (status==REQUEST_SUCCESS){
                    GasonLines gasonLines=(GasonLines)data;
                    getGasonLineReportSIView.SuccessOnReportList(gasonLines);
                }else {
                    getGasonLineReportSIView.Error(Msg);
                }
                break;
        }
    }
}
