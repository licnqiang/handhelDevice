package cn.piesat.sanitation.model.presenter;

import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.CheckRecord;
import cn.piesat.sanitation.data.OrderList;
import cn.piesat.sanitation.model.contract.CarStateContract;
import cn.piesat.sanitation.model.contract.OrderContract;
import cn.piesat.sanitation.networkdriver.common.CommonPresenter;
import cn.piesat.sanitation.networkdriver.common.ICommonAction;

import static cn.piesat.sanitation.networkdriver.common.BasePresenter.REQUEST_SUCCESS;

/**
 * @author lq
 * @fileName 和车辆状态相关的接口
 * @data on  2019/7/25 15:41
 * @describe TODO
 */
public class CarStatePresenter implements ICommonAction, CarStateContract.CarStatePresenter {

    private CommonPresenter commonPresenter;
    private CarStateContract.CarStateView carStateView;
    private CarStateContract.CarStateViewService carStateViewService;

    public CarStatePresenter(CarStateContract.CarStateView CarStateView) {
        carStateView = CarStateView;
        commonPresenter = new CommonPresenter(this);
    }

    public CarStatePresenter(CarStateContract.CarStateViewService CarStateViewService) {
        carStateViewService = CarStateViewService;
        commonPresenter = new CommonPresenter(this);
    }


    /**  车辆故障上报
     * @param idBizcar        车辆ID;（选择车辆的ID）
     * @param carfaultType    故障类型;（填写）
     * @param carfaultAddress 故障地点;（填写）
     * @param sbrCarfault     上报人姓名;（填写）
     * @param sbsjCarfault    上报时间;（填写yyyy-mm-dd 24hh:mi:ss）
     * @param bzCarfault      备注;（填写）
     * @param yjwcsjRepair    完成时间
     */
    @Override
    public void CarFaultSubmit(String idBizcar, String carfaultType, String carfaultAddress, String sbrCarfault, String sbsjCarfault, String bzCarfault, String yjwcsjRepair) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("idBizcar", idBizcar);
        hashMap.put("carfaultType", carfaultType);
        hashMap.put("carfaultAddress", carfaultAddress);
        hashMap.put("sbrCarfault", sbrCarfault);
        hashMap.put("sbsjCarfault", sbsjCarfault);
        hashMap.put("bzCarfault", bzCarfault);
        hashMap.put("yjwcsjRepair", yjwcsjRepair);
        commonPresenter.invokeInterfaceObtainData(false, false, true, false, UrlContant.OutSourcePart.car_state_part, UrlContant.OutSourcePart.car_falut_sumbit,
                hashMap, new TypeToken<String>() {
                });
    }

    /**
     *  车辆维保上报
     * @param idBizcar                车辆ID;（选择车辆的ID）
     * @param typeRepair              维保类型(1维修;2保养);（选择，传1或者2）
     * @param sgdwRepair              车辆保养/维修施工单位;（填写）
     * @param sjRepair                保养/维修时间;（填写）
     * @param xmRepair                保养/维修项目(可能有多个);（填写）
     * @param jeRepair                保养/维修项目金额;（填写）
     * @param sbrRpair                上报人姓名;（填写）
     * @param bzRepair                备注;（填写）
     * @param yjwcsjCarfault          完成时间
     */
    @Override
    public void CarServiceSubmit(String idBizcar, String typeRepair, String sgdwRepair, String sjRepair, String xmRepair, String jeRepair, String sbrRpair, String bzRepair, String yjwcsjCarfault) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("idBizcar", idBizcar);
        hashMap.put("typeRepair", typeRepair);
        hashMap.put("sgdwRepair", sgdwRepair);
        hashMap.put("sjRepair", sjRepair);
        hashMap.put("xmRepair", xmRepair);
        hashMap.put("jeRepair", jeRepair);
        hashMap.put("sbrRpair", sbrRpair);
        hashMap.put("bzRepair", bzRepair);
        hashMap.put("yjwcsjCarfault", yjwcsjCarfault);
        commonPresenter.invokeInterfaceObtainData(false, false, true, true, UrlContant.OutSourcePart.car_service_part, UrlContant.OutSourcePart.car_service_sumbit,
                hashMap, new TypeToken<String>() {
                });
    }


    @Override
    public void obtainData(Object data, String methodIndex, int status, Map<String, String> parameterMap, String Msg) {
        switch (methodIndex) {
            case UrlContant.OutSourcePart.car_falut_sumbit:  //车辆故障上报 返回
                if (status == REQUEST_SUCCESS) {//成功

                    carStateView.SuccessFinshByCarFaultSubmit();
                } else {

                    carStateView.Error(Msg);
                }
                break;

            case UrlContant.OutSourcePart.car_service_sumbit:  //车辆维保上报 返回
                if (status == REQUEST_SUCCESS) {//成功

                    carStateViewService.SuccessFinshByCarServiceSubmit();
                } else {

                    carStateViewService.Error(Msg);
                }
                break;

        }
    }

}
