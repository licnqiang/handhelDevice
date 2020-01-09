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
        public String id;
        public String administrativeArea;
        public String siteName;
        public String applicant;
        public String driver;
        public String carNumber;
        public String maintenanceUnit;
        public double maintenancePrice;
        public String maintenanceReason;
        public String scenePhoto;
        public String maintainPhoto;
        public String maintainBillPhoto;
        public String remark;
        public String approval;
        public String createtime;
        public String roleId;
        public String userId;
        public Object updatetime;
        public AppFlowInstBean appFlowInst;
        

        public static class AppFlowInstBean implements Serializable{
            public String appFlowInstId;
            public String instId;
            public String flowId;
            public int seqno;
            public String status;
            public String sendUser;
            public String sendDate;
            public String sendDept;
            public String roleId;
            public Object chkUser;
            public Object chkDate;
            public Object chkDept;
            public Object appContent;
            public Object scope;
            public Object remark;
        }
    }
}
