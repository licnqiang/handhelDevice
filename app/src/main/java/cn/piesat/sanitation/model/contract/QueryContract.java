package cn.piesat.sanitation.model.contract;

import cn.piesat.sanitation.data.CarInfo;
import cn.piesat.sanitation.data.CompressStations;

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
        void SuccessFinshByBurnStation(CompressStations compressStations);
    }

    interface QueryPresenter {
        //查询压缩站
        void QueryCompress(int pageNum, int pageSize);
        //查询车辆
        void QueryCar(int pageNum, int pageSize,int status);
        //查询焚烧厂
        void QueryBurnStation(int pageNum, int pageSize);
    }
}
