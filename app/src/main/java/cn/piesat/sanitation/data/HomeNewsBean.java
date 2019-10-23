package cn.piesat.sanitation.data;

import java.util.List;

/**
 * Created by sen.luo on 2019/10/23.
 */

public class HomeNewsBean {
    public int total;
    public List<NewsList> list;

    public class NewsList{

        public String id;
        public String title;
        public String createTime;
        public String context;
        public int readCount;
        public int type;
        public String cover;
    }
}
