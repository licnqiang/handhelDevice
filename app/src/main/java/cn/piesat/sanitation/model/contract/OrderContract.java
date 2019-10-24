package cn.piesat.sanitation.model.contract;

import java.util.List;

import cn.piesat.sanitation.data.CheckRecord;
import cn.piesat.sanitation.data.OrderList;
import cn.piesat.sanitation.data.StationCheckSet;

/**
 * @author lq
 * @fileName LoginContract
 * @data on  2019/7/25 15:44
 * @describe TODO
 */
public interface OrderContract {

    interface AssignOrderView {
        void Error(String errorMsg);
        void SuccessFinshByAssign();  //派单成功返回

    }

    interface QueryOrderList{
        void Error(String errorMsg);
        void SuccessFinshByOrderList(OrderList orderList);  //获取订单列表成功返回
    }



    interface OrderPresenter {
        //派单接口
        void AssignOrder(String fscId,String fscmc,String clId,String jhddsjBiztyd,String jhqysjBiztyd,
                         String pdsmBiztyd,String sjId,String yszId,String yszName);
        //站长查询运单列表接口
        void QueryOrderList(int pageNum, int pageSize,String sjId,String clId,String status);

    }
}
