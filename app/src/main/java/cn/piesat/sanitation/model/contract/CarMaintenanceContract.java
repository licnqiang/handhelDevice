package cn.piesat.sanitation.model.contract;

import cn.piesat.sanitation.data.CarBreakDown;
import cn.piesat.sanitation.data.CarMaintenance;

/**
 * @author lq
 * @fileName LoginContract
 * @data on  2019/7/25 15:44
 * @describe TODO
 */
public interface CarMaintenanceContract {

    interface CarMaintenanceView {
        void Error(String errorMsg);
        void SuccessFinsh(CarMaintenance carMaintenance);
    }

    interface CarBreakDownView {
        void Error(String errorMsg);
        void SuccessFinsh(CarBreakDown carBreakDown);
    }

    interface AuditView {
        void Error(String errorMsg);
        void SuccessFinsh();
    }


    interface CarMaintenancePresenter {
        /**
         *   查询车辆维保分页
         *   站长默认查询所管理的站下的车辆
         *   司机默认搜索自己提交的信息
         */
       void QueryCarMaintenance(int pageNum, int pageSize);

        /**
         *   车辆故障
         *   站长默认查询所管理的站下的车辆
         *   司机默认搜索自己提交的信息
         */
        void QueryCarBreakDown(int pageNum, int pageSize);


        /**
         *   审核故障维保
         */
        void AuditCarMaintenance(String idBizrepair, String faultState, String lay1Idbizcaifault);

        /**
         *   审核故障
         */
        void AuditCarBreakDown(String idBizcaifault, String faultState, String lay1Idbizcaifault);

    }
}
