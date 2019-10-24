package cn.piesat.sanitation.model.presenter;

import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.CheckRecord;
import cn.piesat.sanitation.data.OrderList;
import cn.piesat.sanitation.model.contract.ChangeOrderStateContract;
import cn.piesat.sanitation.model.contract.OrderContract;
import cn.piesat.sanitation.networkdriver.common.CommonPresenter;
import cn.piesat.sanitation.networkdriver.common.ICommonAction;

import static cn.piesat.sanitation.networkdriver.common.BasePresenter.REQUEST_SUCCESS;

/**
 * @author lq
 * @fileName 和订单相关的presenter
 * @data on  2019/7/25 15:41
 * @describe TODO
 */
public class ChangeOrderStatePresenter implements ICommonAction, ChangeOrderStateContract.changeOrderStatePresenter {

    private CommonPresenter commonPresenter;
    private ChangeOrderStateContract.HeaderchangeOrderStateView headerchangeOrderStateView;
    private ChangeOrderStateContract.changeOrderStateView changeOrderStateView;

    /**
     * 司机端
     * @param ChangeOrderStateView
     */
    public ChangeOrderStatePresenter(ChangeOrderStateContract.changeOrderStateView ChangeOrderStateView) {
        changeOrderStateView = ChangeOrderStateView;
        commonPresenter = new CommonPresenter(this);
    }

    /**
     * 站长端
     * @param HeaderchangeOrderStateView
     */
    public ChangeOrderStatePresenter(ChangeOrderStateContract.HeaderchangeOrderStateView HeaderchangeOrderStateView) {
        headerchangeOrderStateView = HeaderchangeOrderStateView;
        commonPresenter = new CommonPresenter(this);
    }


    /**
     *  司机接单
     * @param idBiztydsjgbd  ID
     * @param jsrwsjBiztydsjgbd   接单时间（年-月-日 时:分:秒）
     */
    @Override
    public void GetOrder(String idBiztydsjgbd, String jsrwsjBiztydsjgbd) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("idBiztydsjgbd", idBiztydsjgbd);
        hashMap.put("jsrwsjBiztydsjgbd", jsrwsjBiztydsjgbd);

        commonPresenter.invokeInterfaceObtainData(false, false, true, true, UrlContant.OutSourcePart.assigin_order_part, UrlContant.OutSourcePart.query_take_order,
                hashMap, new TypeToken<OrderList>() {
                });
    }

    @Override
    public void StartTransport(String idBiztydsjgbd, String jsrwsjBiztydsjgbd) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("idBiztydsjgbd", idBiztydsjgbd);
        hashMap.put("jsrwsjBiztydsjgbd", jsrwsjBiztydsjgbd);

        commonPresenter.invokeInterfaceObtainData(false, false, true, true, UrlContant.OutSourcePart.assigin_order_part, UrlContant.OutSourcePart.query_start_strans,
                hashMap, new TypeToken<OrderList>() {
                });
    }

    /**
     * 过磅
     * @param bdtp              图片路径，（需先调用上传图片存储相对路径）
     * @param idBizgbd          磅单ID
     * @param idBiztydsjgbd     ID
     * @param mzBizgbd          毛重（吨）
     * @param pzBizgbd          皮重（吨）
     * @param jzBizgbd          净重（吨）
     */
    @Override
    public void CountWeight(String bdtp, String idBizgbd, String idBiztydsjgbd, String mzBizgbd, String pzBizgbd, String jzBizgbd) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("bdtp", bdtp);
        hashMap.put("idBizgbd", idBizgbd);
        hashMap.put("idBiztydsjgbd", idBiztydsjgbd);
        hashMap.put("mzBizgbd", mzBizgbd);
        hashMap.put("pzBizgbd", pzBizgbd);
        hashMap.put("jzBizgbd", jzBizgbd);

        commonPresenter.invokeInterfaceObtainData(false, false, true, true, UrlContant.OutSourcePart.assigin_order_part, UrlContant.OutSourcePart.query_save_bd,
                hashMap, new TypeToken<OrderList>() {
                });
    }

    @Override
    public void AffrimCountWeight(String idBiztydsjgbd, String qrsjBiztydsjgbd) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("idBiztydsjgbd", idBiztydsjgbd);
        hashMap.put("qrsjBiztydsjgbd", qrsjBiztydsjgbd);

        commonPresenter.invokeInterfaceObtainData(false, false, true, true, UrlContant.OutSourcePart.assigin_order_part, UrlContant.OutSourcePart.query_finish_bd,
                hashMap, new TypeToken<OrderList>() {
                });
    }

    @Override
    public void CancelSendOrder(String tydsjgbdId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("tydsjgbdId", tydsjgbdId);

        commonPresenter.invokeInterfaceObtainData(false, false, false, false, UrlContant.OutSourcePart.assigin_order_part, UrlContant.OutSourcePart.query_cancel_order,
                hashMap, new TypeToken<OrderList>() {
                });
    }


    @Override
    public void obtainData(Object data, String methodIndex, int status, Map<String, String> parameterMap, String Msg) {
        switch (methodIndex) {
            case UrlContant.OutSourcePart.query_take_order:  //派单返回
                if (status == REQUEST_SUCCESS) {//成功
                    changeOrderStateView.SuccessFinshByGetOrder();
                } else {
                    changeOrderStateView.Error(Msg);
                }
                break;
            case UrlContant.OutSourcePart.query_start_strans:  //起运
                if (status == REQUEST_SUCCESS) {//成功
                    changeOrderStateView.SuccessFinshByStartTransport();
                } else {
                    changeOrderStateView.Error(Msg);
                }
                break;
            case UrlContant.OutSourcePart.query_save_bd:  //过磅
                if (status == REQUEST_SUCCESS) {//成功
                    changeOrderStateView.SuccessFinshByCountWeight();
                } else {
                    changeOrderStateView.Error(Msg);
                }
                break;

            case UrlContant.OutSourcePart.query_finish_bd:  //榜单确认
                if (status == REQUEST_SUCCESS) {//成功
                    headerchangeOrderStateView.SuccessFinshByAffrimCountWeight();
                } else {
                    changeOrderStateView.Error(Msg);
                }
                break;
            case UrlContant.OutSourcePart.query_cancel_order:  //订单取消
                if (status == REQUEST_SUCCESS) {//成功
                    headerchangeOrderStateView.SuccessFinshByCancelSendOrder();
                } else {
                    headerchangeOrderStateView.Error(Msg);
                }
                break;

        }
    }

}
