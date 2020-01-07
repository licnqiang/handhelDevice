package cn.piesat.sanitation.data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lq
 * @fileName GasonLines
 * @data on  2020/1/7 14:19
 * @describe TODO
 */
public class GasonLines implements Serializable{


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
        public String reportperson;
        public String refuelingPerson;
        public String carNumber;
        public String gasStation;
        public String oldMileage;
        public String oilPrice;
        public String oilLitre;
        public String oilPhoto;
        public String remark;
        
    }
}
