package cn.piesat.sanitation.data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lq
 * @fileName MaintainList
 * @data on  2020/1/9 14:39
 * @describe TODO
 */
public class MaintainList implements Serializable{

    public int total;
    public int size;
    public int current;
    public boolean searchCount;
    public int pages;
    public List<RecordsBean> records;
    
    public static class RecordsBean implements Serializable{
        /**
         * id : e3e34186847f38a0d9581a4ced00a7e5
         * administrativeArea : 泾河新城
         * siteName : 泾河新城环卫中心
         * applicant : 孙磊
         * driver : 孙磊
         * carNumber : 陕A21321
         * maintenanceUnit : 汽修中心
         * maintenancePrice : 10000.0
         * maintenanceReason : 坏掉了
         * scenePhoto : http://192.168.5.51:19080/api/file/56279bc429a54f35abe6f4a53af84da9.png
         * maintainPhoto : http://192.168.5.51:19080/api/file/932e87c14a8b4c4a920244d94e167140.png
         * maintainBillPhoto : 
         * remark : 备注一下
         * approval : a625426ec1879ba7bfde68bb21dc9af2
         * createtime : 2020-01-10 15:54:26
         * roleId : 4
         * userId : 1001
         * updatetime : 
         * appFlowInst : {"appFlowInstId":"a625426ec1879ba7bfde68bb21dc9af2","instId":"c687aace70b87e451d2a40db8b35dc22","flowId":"1","seqno":2,"status":"1","sendUser":"站长老梁","sendDate":"2020-01-10 15:54:26","sendDept":"站长","roleId":"ORM","chkUser":"","chkDate":"","chkDept":"","appContent":"","scope":"","remark":""}
         * check_role : ORM
         * check_name : 运营部长
         * roleflag : 
         * rejectedflag : 2
         * completedflag : 2
         * approvalstatus : 01
         */

        public String id;
        public String administrativeArea;
        public String siteName;
        public String applicant;
        public String driver;
        public String carNumber;
        public String maintenanceUnit;
        public String maintenancePrice;
        public String maintenanceReason;
        public String scenePhoto;
        public String maintainPhoto;
        public String maintainBillPhoto;
        public String remark;
        public String approval;
        public String createtime;
        public String roleId;
        public String userId;
        public String updatetime;
        public AppFlowInstBean appFlowInst;
        public String check_role;
        public String check_name;
        public String roleflag;
        public String rejectedflag;
        public String completedflag;
        public String approvalstatus;
        
        public static class AppFlowInstBean implements Serializable{
            /**
             * appFlowInstId : a625426ec1879ba7bfde68bb21dc9af2
             * instId : c687aace70b87e451d2a40db8b35dc22
             * flowId : 1
             * seqno : 2
             * status : 1
             * sendUser : 站长老梁
             * sendDate : 2020-01-10 15:54:26
             * sendDept : 站长
             * roleId : ORM
             * chkUser : 
             * chkDate : 
             * chkDept : 
             * appContent : 
             * scope : 
             * remark : 
             */

            public String appFlowInstId;
            public String instId;
            public String flowId;
            public String seqno;
            public String status;
            public String sendUser;
            public String sendDate;
            public String sendDept;
            public String roleId;
            public String chkUser;
            public String chkDate;
            public String chkDept;
            public String appContent;
            public String scope;
            public String remark;
            
        }
    }
}
