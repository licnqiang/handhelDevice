package cn.piesat.sanitation.data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lq
 * @fileName CompressStations
 * @data on  2019/10/22 15:53
 * @describe TODO
 */
public class CompressStations implements Serializable{

    public int code;
    public int totle;
    public List<RowsBean> rows;

    public static class RowsBean implements Serializable{
        public String idYsz;
        public String nameYsz;
        public String addrYsz;
        public String longitudeYsz;
        public String latitudeYsz;
        public String chargeYsz;
        public String telephoneYsz;
        public String fixedphoneYsz;
        public String lay1Ysz;
        public String lay2Ysz;
        public String lay3Ysz;
        public String lay4Ysz;
        public String lay5Ysz;
        public String introductionYsz;
        public String area;
        public String createUserBizysz;
        public String createtimeBizysz;
        public String updateUserBizysz;
        public String updatetimeBizysz;
        public String idSysdept;
        public String scaleYsz;
        public String surfaceYsz;
        public String deptNameCount;
    }
}
