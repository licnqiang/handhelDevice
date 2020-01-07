package cn.piesat.sanitation.data;

import java.util.List;

public class ViolateReportBean {

    public List<ViolateListBean>records;


    public class ViolateListBean{
        public String id;
        public String administrativeArea;
        public String siteName;
        public String reportperson;
        public String carNumber;
        public String illegalPeople;
        public String illegalTime;
        public String illegalProject;
        public String illegalMoney;
        public String illegalPhoto;
        public String remark;

    }

}
