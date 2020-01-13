package cn.piesat.sanitation.data;

import java.io.Serializable;
import java.util.List;

public class InsuranceBean implements Serializable{


    public double total;
    public double size;
    public double current;
    public boolean searchCount;
    public double pages;
    public List<InsuranceListBean> records;
    
    public static class InsuranceListBean implements Serializable{
        /**
         * id : d1db97c0083e8378d74b2a4f991af765
         * administrativeArea : 沣西新城
         * siteName : 高桥压缩站
         * applicant : 站长B
         * coverage : uhhh
         * carNumber : 陕E2121
         * oldInsuranceStarttime : 2020-01-13
         * oldInsuranceEndtime : 2020-01-13
         * insuranceSign : /upload/7481ff7ba9c34819873ab879ec922cc8.jpg
         * purchaseEndtime : 2020-01-13
         * remark : ijkn
         * approval : 00df3364c8ce1e2c470db810521a8199
         * createtime : 2020-01-13 15:05:54
         * roleId : 4
         * userId : 328e01db8d15482c8e2e901e9ffc98ad
         * appFlowInst : {"appFlowInstId":"00df3364c8ce1e2c470db810521a8199","instId":"40a1400b7a2985252ed84d92c620ab3d","flowId":"1","seqno":2,"status":"1","sendUser":"站长B","sendDate":"2020-01-13 15:05:54","sendDept":"站长","roleId":"ORM"}
         * check_role : ORM
         * check_name : 运营部长
         * roleflag : 1
         * rejectedflag : 2
         * completedflag : 2
         * approvalstatus : 01
         */

        public String id;
        public String administrativeArea;
        public String siteName;
        public String applicant;
        public String coverage;
        public String carNumber;
        public String oldInsuranceStarttime;
        public String oldInsuranceEndtime;
        public String insuranceSign;
        public String purchaseEndtime;
        public String remark;
        public String approval;
        public String createtime;
        public String roleId;
        public String userId;
        public AppFlowInstBean appFlowInst;
        public String check_role;
        public String check_name;
        public String roleflag;
        public String rejectedflag;
        public String completedflag;
        public String approvalstatus;

        public static class AppFlowInstBean implements Serializable{
            /**
             * appFlowInstId : 00df3364c8ce1e2c470db810521a8199
             * instId : 40a1400b7a2985252ed84d92c620ab3d
             * flowId : 1
             * seqno : 2.0
             * status : 1
             * sendUser : 站长B
             * sendDate : 2020-01-13 15:05:54
             * sendDept : 站长
             * roleId : ORM
             */
            public String appFlowInstId;
            public String instId;
            public String flowId;
            public double seqno;
            public String status;
            public String sendUser;
            public String sendDate;
            public String sendDept;
            public String roleId;
            public String appContent;

        }
    }
}
