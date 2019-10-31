package cn.piesat.sanitation.data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lq
 * @fileName CarMaintenance
 * @data on  2019/10/31 13:13
 * @describe TODO
 */
public class CarMaintenance implements Serializable {
    

    public int code;
    public int totle;
    public List<RowsBean> rows;

    public static class RowsBean implements Serializable{
        public String idBizrepair;   //维修保养id
        public String idBizcar;      //汽车id
        public String typeRepair;    //维保类型(1维修;2保养)
        public String sgdwRepair;    //车辆保养/维修施工单位
        public String sjRepair;      //保养/维修时间
        public String xmRepair;      //保养/维修项目
        public String jeRepair;      //保养/维修项目金额
        public String sbrRepair;     //上报人姓名
        public String bzRepair;      //备注
        public String createUserBizrepair;   //创建用户
        public String createtimeBizrepair;   //上报时间
        public String lay1Bizrepair;
        public String lay2Bizrepair;
        public String lay3Bizrepair;
        public String lay4Bizrepair;
        public String lay5Bizrepair;
        public String yjwcsjRepair;           //预计完成时间
        public String licensePlate;           //车牌号
        public String deptName;               //组织机构名称
        public String owner;                  //司机
        public String name;                   //维修/保养状态
        public String repairState;            //维修/保养状态code
    }
}
