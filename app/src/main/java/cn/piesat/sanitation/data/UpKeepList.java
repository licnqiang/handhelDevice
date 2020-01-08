package cn.piesat.sanitation.data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lq
 * @fileName UpKeepList
 * @data on  2020/1/8 17:49
 * @describe TODO
 */
public class UpKeepList implements Serializable{
      
        public String total;
        public String size;
        public String current;
        public boolean searchCount;
        public String pages;
        public List<RecordsBean> records;
        

        public static class RecordsBean implements Serializable{
            /**
             * id : 759fca374a847a0b9c30af26683113b1
             * administrativeArea : 沣西新城
             * siteName : 高桥压缩站
             * carNumber : 陕E2121
             * maintainUnit : 中通维修单位
             * maintainPrice : 158.6
             * maintainDescribe : 保养变速箱
             * remark : 保养变速箱
             * approval : d2cb6774e68cb521129a449536a3d20f
             * createtime : 2020-01-08 17:25:32
             * roleId : 4
             * userId : 328e01db8d15482c8e2e901e9ffc98ad
             * appFlowInst : {"appFlowInstId":"d2cb6774e68cb521129a449536a3d20f","instId":"16fbc03d6d935307f370c2a9e34b7fe3","flowId":"1","seqno":2,"status":"1","sendUser":"站长B","sendDate":"2020-01-08 17:25:32","sendDept":"站长","roleId":"ORM"}
             */

            public String id;
            public String administrativeArea;
            public String siteName;
            public String carNumber;
            public String maintainUnit;
            public String maintainPrice;
            public String maintainDescribe;
            public String remark;
            public String approval;
            public String createtime;
            public String roleId;
            public String userId;
            public AppFlowInstBean appFlowInst;
            

            public static class AppFlowInstBean implements Serializable{
                /**
                 * appFlowInstId : d2cb6774e68cb521129a449536a3d20f
                 * instId : 16fbc03d6d935307f370c2a9e34b7fe3
                 * flowId : 1
                 * seqno : 2.0
                 * status : 1
                 * sendUser : 站长B
                 * sendDate : 2020-01-08 17:25:32
                 * sendDept : 站长
                 * roleId : ORM
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
                
            
        }
    }
}
