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
public interface QueryContract {

    interface QueryView {
        void Error(String errorMsg);
        void SuccessFinshByCompress(CompressStations compressStations);
        void SuccessFinshByCar(CarInfo carInfo);
        void SuccessFinshByBurnStation(BurnStationInfo burnStationInfo);
        void SuccessFinshByDriver(DriverInfo driverInfo);
    }

    interface QueryPresenter {
        //查询压缩站
        void QueryCompress(int pageNum, String pageSize);
        //查询车辆
        void QueryCar(int pageNum, String pageSize,int status);
        //查询焚烧厂
        void QueryBurnStation(int pageNum, String pageSize);
        //查询司机
        void QueryDriver(int pageNum, String pageSize,int userType,String idSysdept);
    }
}
