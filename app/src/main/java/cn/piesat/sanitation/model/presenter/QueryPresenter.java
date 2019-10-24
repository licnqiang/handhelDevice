package cn.piesat.sanitation.model.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.BurnStationInfo;
import cn.piesat.sanitation.data.CarInfo;
import cn.piesat.sanitation.data.CompressStations;
import cn.piesat.sanitation.data.DriverInfo;
import cn.piesat.sanitation.data.LoginInfo_Respose;
import cn.piesat.sanitation.model.contract.LoginContract;
import cn.piesat.sanitation.model.contract.QueryContract;
import cn.piesat.sanitation.networkdriver.common.BasePresenter;
import cn.piesat.sanitation.networkdriver.common.CommonPresenter;
import cn.piesat.sanitation.networkdriver.common.ICommonAction;

import static cn.piesat.sanitation.networkdriver.common.BasePresenter.REQUEST_SUCCESS;

/**
 * @author lq
 * @fileName 查询压缩站
 * @data on  2019/7/25 15:41
 * @describe TODO
 */
public class QueryPresenter implements ICommonAction, QueryContract.QueryPresenter {

    private CommonPresenter commonPresenter;
    private QueryContract.QueryView QueryView;

    public QueryPresenter(QueryContract.QueryView queryView) {
        QueryView = queryView;
        commonPresenter = new CommonPresenter(this);
    }


    @Override
    public void QueryCompress(int pageNum, int pageSize) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pageNum", pageNum + "");
        hashMap.put("pageSize", pageSize + "");
        commonPresenter.invokeInterfaceObtainData(false, false, false,false, UrlContant.OutSourcePart.part, UrlContant.OutSourcePart.query_compress_station,
                hashMap, new TypeToken<CompressStations>() {
                });
    }

    @Override
    public void QueryCar(int pageNum, int pageSize, int status) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pageNum", pageNum + "");
        hashMap.put("pageSize", pageSize + "");
        hashMap.put("status", status + "");
        commonPresenter.invokeInterfaceObtainData(false, false, false,false, UrlContant.OutSourcePart.part, UrlContant.OutSourcePart.query_car,
                hashMap, new TypeToken<CarInfo>() {
                });

    }

    @Override
    public void QueryBurnStation(int pageNum, int pageSize) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pageNum", pageNum + "");
        hashMap.put("pageSize", pageSize + "");
        commonPresenter.invokeInterfaceObtainData(false, false, false,false, UrlContant.OutSourcePart.part, UrlContant.OutSourcePart.query_burn_staion,
                hashMap, new TypeToken<BurnStationInfo>() {
                });

    }

    /**
     *
     * @param pageNum
     * @param pageSize
     * @param userType    查询用户类型 1管理员2普通用户3环卫集团员工4站长5操作工6扫保人员7司机
     * @param idSysdept   组织机构id
     */
    @Override
    public void QueryDriver(int pageNum, int pageSize, int userType, String idSysdept) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pageNum", pageNum + "");
        hashMap.put("pageSize", pageSize + "");
        hashMap.put("userType", userType + "");
        hashMap.put("idSysdept", idSysdept);
        commonPresenter.invokeInterfaceObtainData(false, false, false,false, UrlContant.OutSourcePart.part, UrlContant.OutSourcePart.query_driver,
                hashMap, new TypeToken<DriverInfo>() {
                });
    }


    @Override
    public void obtainData(Object data, String methodIndex, int status, Map<String, String> parameterMap, String Msg) {
        switch (methodIndex) {
            //查询压缩站
            case UrlContant.OutSourcePart.query_compress_station:
                if (status == REQUEST_SUCCESS) {
                    CompressStations compressStations = (CompressStations) data;
                    QueryView.SuccessFinshByCompress(compressStations);
                } else {
                    QueryView.Error(Msg);
                }
                break;
            //车辆
            case UrlContant.OutSourcePart.query_car:
                if (status == REQUEST_SUCCESS) {
                    CarInfo carInfo = (CarInfo) data;
                    QueryView.SuccessFinshByCar(carInfo);
                } else {
                    QueryView.Error(Msg);
                }
                break;
            //焚烧厂
            case UrlContant.OutSourcePart.query_burn_staion:
                if (status == REQUEST_SUCCESS) {
                    BurnStationInfo burnStationInfo=(BurnStationInfo)data;
                    QueryView.SuccessFinshByBurnStation(burnStationInfo);
                } else {
                    QueryView.Error(Msg);
                }
                break;
            //司机
            case UrlContant.OutSourcePart.query_driver:
                if (status == REQUEST_SUCCESS) {
                    DriverInfo driverInfo=(DriverInfo)data;
                    QueryView.SuccessFinshByDriver(driverInfo);
                } else {
                    QueryView.Error(Msg);
                }
                break;
        }


    }

}
