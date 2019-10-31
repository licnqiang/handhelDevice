package cn.piesat.sanitation.data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lq
 * @fileName 车辆故障
 * @data on  2019/10/31 15:26
 * @describe TODO
 */
public class CarBreakDown implements Serializable{

    public int code;
    public int totle;
    public List<RowsBean> rows;


    public static class RowsBean implements Serializable{
        public String bzCarfault;                 // 备注
        public String carfaultAddress;
        public String carfaultType;               // 故障类型
        public String createUserIdbizcaifault;
        public String createtimeIdbizcaifault;
        public String deptName;                   // 所属组织机构
        public String faultState;                 // 状态
        public String idBizcaifault;              // ID
        public String idBizcar;                   // 车辆VID
        public String lay1Idbizcaifault;          // 审批未通过理由
        public String licensePlate;               // 车牌号
        public String name;                       // 状态描述
        public int rowId;
        public String sbrCarfault;                 // 上报用户
        public String sbsjCarfault;                // 上报时间
        public String updateUserIdbizcaifault;     // 修改用户
        public String updatetimeIdbizcaifault;
        public String yjwcsjCarfault;              // 完成时间

    }
}
