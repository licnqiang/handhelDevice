package cn.piesat.sanitation.data;

import java.util.List;

public class AccidentReportBean {

    public List<AccidentListBean>records;


    public class AccidentListBean{
        public String id;
        public String administrativeArea;
        public String siteName;
        public String reportperson;
        public String carNumber;
        public String accidentPeople;
        public String accidentTime;
        public String proportionalAmount;
        public String fee;
        public String scenePhotos;
        public String accidentDescription;
        public String remark;


    }

}
