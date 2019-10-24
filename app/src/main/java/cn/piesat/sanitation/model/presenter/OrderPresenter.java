package cn.piesat.sanitation.model.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.CheckRecord;
import cn.piesat.sanitation.data.OrderList;
import cn.piesat.sanitation.data.StationCheckSet;
import cn.piesat.sanitation.model.contract.CheckingContract;
import cn.piesat.sanitation.model.contract.OrderContract;
import cn.piesat.sanitation.networkdriver.common.CommonPresenter;
import cn.piesat.sanitation.networkdriver.common.ICommonAction;
import cn.piesat.sanitation.util.LogUtil;

import static cn.piesat.sanitation.networkdriver.common.BasePresenter.REQUEST_SUCCESS;

/**
 * @author lq
 * @fileName 和订单相关的presenter
 * @data on  2019/7/25 15:41
 * @describe TODO
 */
public class OrderPresenter implements ICommonAction, OrderContract.OrderPresenter {

    private CommonPresenter commonPresenter;
    private OrderContract.AssignOrderView assignOrderView;
    private OrderContract.QueryOrderList queryOrderList;

    public OrderPresenter(OrderContract.AssignOrderView orderView) {
        assignOrderView = orderView;
        commonPresenter = new CommonPresenter(this);
    }

    public OrderPresenter(OrderContract.QueryOrderList  QueryOrderList) {
        queryOrderList = QueryOrderList;
        commonPresenter = new CommonPresenter(this);
    }

    /**
     * @param fscId        焚烧厂ID
     * @param fscmc        焚烧厂名称
     * @param clId         车辆VID
     * @param jhddsjBiztyd 计划抵达时间
     * @param jhqysjBiztyd 计划起运时间
     * @param pdsmBiztyd   派单说明
     * @param sjId         司机ID
     * @param yszId        压缩站ID
     * @param yszName      压缩站名称
     */
    @Override
    public void AssignOrder(String fscId, String fscmc, String clId, String jhddsjBiztyd, String jhqysjBiztyd,
                            String pdsmBiztyd, String sjId, String yszId, String yszName) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("fscId", fscId);
        hashMap.put("fscmc", fscmc);
        hashMap.put("clId", clId);
        hashMap.put("jhddsjBiztyd", jhddsjBiztyd);
        hashMap.put("jhqysjBiztyd", jhqysjBiztyd);
        hashMap.put("pdsmBiztyd", pdsmBiztyd);
        hashMap.put("sjId", sjId);
        hashMap.put("yszId", yszId);
        hashMap.put("yszName", yszName);
        commonPresenter.invokeInterfaceObtainData(false, false, true, true, UrlContant.OutSourcePart.assigin_order_part, UrlContant.OutSourcePart.assigin_order,
                hashMap, new TypeToken<List<CheckRecord>>() {
                });
    }

    /**
     *   运单列表
     * @param pageNum    当前页(必传)
     * @param pageSize   每页条数(必传 -1 为全查)
     * @param sjId       司机ID(非必传)
     * @param clId       车辆ID(非必传)
     * @param status     运单状态(非必传)：0 - 指派取消、1 - 已指派未接单、2 - 已接单未起运、3 - 已起运未过磅、4 - 已过磅未确认、5- 已完成
     */
    @Override
    public void QueryOrderList(int pageNum, int pageSize,String sjId,String clId,String status) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pageNum", pageNum + "");
        hashMap.put("pageSize", pageSize + "");
        if(null!=sjId){
            hashMap.put("sjId", sjId);
        }
        if(null!=clId){
            hashMap.put("clId", clId);
        }
        if(null!=status){
            hashMap.put("status", status);
        }
        commonPresenter.invokeInterfaceObtainData(false, false, false, false, UrlContant.OutSourcePart.assigin_order_part, UrlContant.OutSourcePart.query_order_list,
                hashMap, new TypeToken<OrderList>() {
                });
    }


    @Override
    public void obtainData(Object data, String methodIndex, int status, Map<String, String> parameterMap, String Msg) {
        switch (methodIndex) {
            case UrlContant.OutSourcePart.assigin_order:  //派单返回
                if (status == REQUEST_SUCCESS) {//成功

                    assignOrderView.SuccessFinshByAssign();
                } else {
                    assignOrderView.Error(Msg);
                }
                break;
            case UrlContant.OutSourcePart.query_order_list:  //运单列表
                if (status == REQUEST_SUCCESS) {//成功
                    OrderList orderList = (OrderList) data;
                    queryOrderList.SuccessFinshByOrderList(orderList);
                } else {
                    queryOrderList.Error(Msg);
                }
                break;


        }
    }
}
