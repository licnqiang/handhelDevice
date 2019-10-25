package cn.piesat.sanitation.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sen.luo on 2019/10/24.
 */

public class MyAttendanceLBean {


    public int size;
    public int total;
    public int pages;
    public List<DataBean> list;


    public static class DataBean {

        public String id;
        public String startTime;
        public String endTime;
        public String userId;
        public String createDate;



    }
}
