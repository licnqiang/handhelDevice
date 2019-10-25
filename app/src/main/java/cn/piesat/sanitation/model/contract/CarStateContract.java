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
public interface CarStateContract {

    interface CarStateView {
        void Error(String errorMsg);
        void SuccessFinshByCarFaultSubmit();
    }

    interface CarStateViewService {
        void Error(String errorMsg);
        void SuccessFinshByCarServiceSubmit();
    }

    interface CarStatePresenter {
        //车辆故障上报
        void CarFaultSubmit(String idBizcar,String carfaultType,String carfaultAddress,String sbrCarfault,String sbsjCarfault,String bzCarfault,String yjwcsjRepair);
        //车辆维保上报
        void CarServiceSubmit(String idBizcar,String typeRepair,String sgdwRepair,String sjRepair,String xmRepair,String jeRepair,String sbrRpair
                ,String bzRepair,String yjwcsjCarfault);

    }
}
