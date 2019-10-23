package cn.piesat.sanitation.data;

import java.util.List;

/**
 * @author lq
 * @fileName CheckRecord
 * @data on  2019/10/22 17:27
 * @describe TODO
 */
public class CheckRecord {

    public List<RecordBean> RecordBeans;

    public class RecordBean {
        public String id;
        public String userId;
        public String time;
        public String createDate;
        public int type;   //1为早上  2为下午
        public String remark;
        public String siteId;

    }

}
