package cn.piesat.sanitation.data;

import java.util.List;

public class InsuranceBean {

    public List<InsuranceListBean>records;

    public class InsuranceListBean{

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
        public String updatetime;

        public String check_role;//当前审核角色
        public String check_name;//审核角色姓名
        public String approvalstatus; //审核状态 01 审核中  02 驳回 03 审核完成了
        public AppFlowInst appFlowInst;

        public class AppFlowInst{
            public String sendUser;
            public String sendDate;
            public String appContent; //审核信息
        }

    }
}
