package cn.piesat.sanitation.model.presenter;

import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import cn.piesat.sanitation.constant.UrlContant;
import cn.piesat.sanitation.data.CarBreakDown;
import cn.piesat.sanitation.data.CarMaintenance;
import cn.piesat.sanitation.model.contract.CarMaintenanceContract;
import cn.piesat.sanitation.model.contract.CarStateContract;
import cn.piesat.sanitation.networkdriver.common.CommonPresenter;
import cn.piesat.sanitation.networkdriver.common.ICommonAction;

import static cn.piesat.sanitation.networkdriver.common.BasePresenter.REQUEST_SUCCESS;

/**
 * @author lq
 * @fileName 车辆维修保养
 * @data on  2019/7/25 15:41
 * @describe TODO
 */
public class CarMaintenancePresenter implements ICommonAction, CarMaintenanceContract.CarMaintenancePresenter {

    private CommonPresenter commonPresenter;
    private CarMaintenanceContract.CarMaintenanceView carMaintenanceView;
    private CarMaintenanceContract.CarBreakDownView carBreakDownView;
    private CarMaintenanceContract.AuditView auditView;

    public CarMaintenancePresenter(CarMaintenanceContract.CarMaintenanceView CarMaintenanceView) {
        carMaintenanceView = CarMaintenanceView;
        commonPresenter = new CommonPresenter(this);
    }

    public CarMaintenancePresenter(CarMaintenanceContract.CarBreakDownView CarBreakDownView) {
        carBreakDownView = CarBreakDownView;
        commonPresenter = new CommonPresenter(this);
    }

    public CarMaintenancePresenter(CarMaintenanceContract.AuditView AuditView) {
        auditView = AuditView;
        commonPresenter = new CommonPresenter(this);
    }

    /**
     * 查询车辆维保分页
     * 站长默认查询所管理的站下的车辆
     * 司机默认搜索自己提交的信息
     */
    @Override
    public void QueryCarMaintenance(int pageNum, int pageSize) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pageNum", pageNum + "");
        hashMap.put("pageSize", pageSize + "");
        commonPresenter.invokeInterfaceObtainData(false, false, true, false, UrlContant.OutSourcePart.car_maintencen_port_list, UrlContant.OutSourcePart.car_maintencen_list,
                hashMap, new TypeToken<CarMaintenance>() {
                });
    }
    /**
     * 查询车辆故障分页
     * 站长默认查询所管理的站下的车辆
     * 司机默认搜索自己提交的信息
     */
    @Override
    public void QueryCarBreakDown(int pageNum, int pageSize) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pageNum", pageNum + "");
        hashMap.put("pageSize", pageSize + "");
        commonPresenter.invokeInterfaceObtainData(false, false, true, false, UrlContant.OutSourcePart.car_maintencen_port_list, UrlContant.OutSourcePart.car_break_down_list,
                hashMap, new TypeToken<CarBreakDown>() {
                });
    }

    /**
     * 审核维保
     *
     * @param idBizrepair       维保单id
     * @param faultState        审批通过1；不通过 0
     * @param lay1Idbizcaifault 不通过理由（通过可不传）
     */
    @Override
    public void AuditCarMaintenance(String idBizrepair, String faultState, String lay1Idbizcaifault) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("idBizrepair", idBizrepair);
        hashMap.put("repairState", faultState);
        hashMap.put("lay1Idbizcaifault", lay1Idbizcaifault);
        commonPresenter.invokeInterfaceObtainData(false, false, true, true, UrlContant.OutSourcePart.car_maintencen_audit_par, UrlContant.OutSourcePart.car_maintencen_audit,
                hashMap, new TypeToken<CarBreakDown>() {
                });

    }

    /**
     * 审核故障
     *
     * @param idBizcaifault     故障单id
     * @param faultState        审批通过1；不通过 0
     * @param lay1Idbizcaifault 不通过理由（通过可不传）
     */
    @Override
    public void AuditCarBreakDown(String idBizcaifault, String faultState, String lay1Idbizcaifault) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("idBizcaifault", idBizcaifault);
        hashMap.put("faultState", faultState);
        hashMap.put("lay1Idbizcaifault", lay1Idbizcaifault);
        commonPresenter.invokeInterfaceObtainData(false, false, true, true, UrlContant.OutSourcePart.car_break_down_audit_par, UrlContant.OutSourcePart.car_break_down_audit,
                hashMap, new TypeToken<CarBreakDown>() {
                });
    }


    @Override
    public void obtainData(Object data, String methodIndex, int status, Map<String, String> parameterMap, String Msg) {
        switch (methodIndex) {
            case UrlContant.OutSourcePart.car_maintencen_list:  //查询车辆维保 列表
                if (status == REQUEST_SUCCESS) {//成功
                    CarMaintenance carMaintenance = (CarMaintenance) data;
                    carMaintenanceView.SuccessFinsh(carMaintenance);
                } else {

                    carMaintenanceView.Error(Msg);
                }
                break;
            case UrlContant.OutSourcePart.car_break_down_list:  //查询车辆故障 列表
                if (status == REQUEST_SUCCESS) {//成功
                    CarBreakDown carBreakDown = (CarBreakDown) data;
                    carBreakDownView.SuccessFinsh(carBreakDown);
                } else {

                    carBreakDownView.Error(Msg);
                }
                break;
            case UrlContant.OutSourcePart.car_maintencen_audit:  //维保审核和故障审核同意接收是否成功
                if (status == REQUEST_SUCCESS) {//成功
                    auditView.SuccessFinsh();
                } else {

                    auditView.Error(Msg);
                }
                break;

        }
    }

}
