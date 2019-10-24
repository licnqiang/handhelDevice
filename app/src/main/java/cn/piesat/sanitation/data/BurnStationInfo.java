package cn.piesat.sanitation.data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lq
 * @fileName BurnStationInfo
 * @data on  2019/10/24 11:24
 * @describe TODO
 */
public class BurnStationInfo implements Serializable{

    public int code;
    public int totle;
    public List<RowsBean> rows;
    
    public static class RowsBean implements Serializable{
        public String idFsc;    //2B9D2D2B71D7487B857E538B10619ECD
        public String nameFsc;  //西咸新区生活垃圾焚烧发电厂
        public String idSysdept;  //DCFF515981DF43F4A2FEAC5DFF4F61DC
        public String addrFsc;
        public String longitudeFsc;
        public String latitudeFsc;
        public String chargeFsc;
        public String phoneFsc;
        public String telephoneFsc;
        public String lay1Fsc;
        public String lay2Fsc;
        public String lay3Fsc;
        public String lay4Fsc;
        public String lay5Fsc;
        public String introduction;
        public String area;
        public String createUserBizfsc;
        public String createtimeBizfsc;
        public String updateUserBizfsc;
        public String updatetimeBizfsc;
        public String scaleYsz;
        public String deptNameCount;
    }
}
