package cn.piesat.sanitation.data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lq
 * @fileName OrderList
 * @data on  2019/10/24 14:51
 * @describe TODO
 */
public class OrderList implements Serializable{
    public int code;
    public int totle;
    public List<RowsBean> rows;

    public static class RowsBean implements Serializable{
        public String createUserBiztydsjgbd;
        public String updateUserBiztydsjgbd;
        public String yszId;      // 压缩站ID
        public String clId;      // 车辆ID
        public String lay2Biztydsjgbd;
        public String jsrwsjBiztydsjgbd;   // 接收任务时间
        public String pdsjBiztyd;  // 派单时间
        public String bdtp;     // 过磅单图片路径
        public String pdsmBiztyd;   // 派单说明
        public String idBiztydsjgbd;
        public String lay5Biztydsjgbd;
        public String bdId;          // 磅单ID
        public String licensePlate;   // 车牌
        public String lay1Biztydsjgbd;
        public String cancelBiz;     // 取消时间
        public String ydhBiztyd;     // 运单号
        public String fscmc;        // 焚烧厂名称
        public String yszName;     // 压缩站名称
        public String fscId;       //   焚烧厂ID
        public String lay4Biztydsjgbd;
        public String createtimeBiztydsjgbd;
        public String sjId;        // 司机ID
        public String idBiztyd;   // 运单ID
        public String qrsjBiztydsjgbd;   // 确认时间
        public String jzBizgbd;     // 净重（吨）
        public String sjqysjBiztydsjgbd;   // 实际起运时间
        public String lay3Biztydsjgbd;
        public String sjddsjBiztydsjgbd;   // 实际达到时间
        public String name;    // 司机名字
        public String updatetimeBiztydsjgbd;
        public String mzBizgbd;   // 毛重（吨）
        public String pzBizgbd;   //   皮重（吨）
        public String jhddsjBiztyd;  // 计划抵达时间
        public int status;    //运单状态：0 -指派取消、1-已指派未接单、2-已接单未起运、3-已起运未过磅、4-已过磅未确认、5- 已完成
        public String jhqysjBiztyd;   // 计划起运时间
    }
}
