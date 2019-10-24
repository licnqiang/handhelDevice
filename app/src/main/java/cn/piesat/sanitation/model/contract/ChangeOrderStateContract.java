package cn.piesat.sanitation.model.contract;

import cn.piesat.sanitation.data.BurnStationInfo;
import cn.piesat.sanitation.data.CarInfo;
import cn.piesat.sanitation.data.CompressStations;
import cn.piesat.sanitation.data.DriverInfo;

/**
 * @author lq
 * @fileName LoginContract
 * @data on  2019/7/25 15:44
 * @describe TODO
 */
public interface ChangeOrderStateContract {

    interface changeOrderStateView {
        void Error(String errorMsg);
        void SuccessFinshByGetOrder();
        void SuccessFinshByStartTransport();
        void SuccessFinshByCountWeight();

    }

    interface HeaderchangeOrderStateView {
        void Error(String errorMsg);
        void SuccessFinshByAffrimCountWeight();
        void SuccessFinshByCancelSendOrder();


    }

    interface changeOrderStatePresenter {
        //接单
        void GetOrder(String idBiztydsjgbd, String jsrwsjBiztydsjgbd);

        //起运
        void StartTransport(String idBiztydsjgbd, String jsrwsjBiztydsjgbd);

        //司机过磅
        void CountWeight(String bdtp,String idBizgbd,String idBiztydsjgbd,String mzBizgbd,String pzBizgbd,String jzBizgbd);

        //司机过磅
        void AffrimCountWeight(String idBiztydsjgbd, String qrsjBiztydsjgbd);

        //取消派单
        void CancelSendOrder(String tydsjgbdId);

    }
}
