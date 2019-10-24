package cn.piesat.sanitation.data;

import java.util.List;

/**
 * Created by sen.luo on 2019/10/24.
 */

public class UserListBean {


    /**
     * code : 0
     * rows : [{"id":"123456","userType":"4","account":"cll","phone":"13111111111","name":"cll","email":"1@1.com","job":"常柳柳","idSysdept":"9519295C29644324E05011AC03000AF7","updateTimeSysuser":"2019-07-25 10:28:36","createTimeSysuser":"2019-07-25 10:28:36","deptNameCount":"高桥压缩站"},{"id":"FFEFB9F2489D4F54BABD38F5B8329680","userType":"3","account":"zhangsan","phone":"13299090458","name":"张三","sex":"1","idSysdept":"9519295C29644324E05011AC03000AF7","deptNameCount":"高桥压缩站"},{"id":"1728D88EABB241EF8035DCDB8DCB8148","userType":"4","account":"zhanz","phone":"13299090458","name":"站长A","sex":"1","idSysdept":"9519295C29644324E05011AC03000AF7","lay1Sysuser":"/upload/8a926d7ed24b485e9fa9bda11e74de00.jpg","deptNameCount":"高桥压缩站"},{"id":"E2EBDD3F78FF4EF29FB171AFADBA824E","userType":"5","account":"caozuog","phone":"13299090458","name":"操作工A","sex":"1","idSysdept":"9519295C29644324E05011AC03000AF7","deptNameCount":"高桥压缩站"},{"id":"FFC1EC7FF0D94B59B06578E6438470A3","userType":"6","account":"sbry","phone":"13299090458","name":"扫保人员A","sex":"1","idSysdept":"9519295C29644324E05011AC03000AF7","deptNameCount":"高桥压缩站"},{"id":"E46DF34C93B3469CB7D610C77B202C88","userType":"7","account":"sjB","phone":"13299090458","name":"B司机","sex":"1","idSysdept":"9519295C29644324E05011AC03000AF7","deptNameCount":"高桥压缩站"},{"id":"D5311DFA203E4DA5A3E8188CE7B27AA8","userType":"7","account":"sj","phone":"13299090458","name":"A司机","sex":"1","idSysdept":"9519295C29644324E05011AC03000AF7","deptNameCount":"高桥压缩站"}]
     * totle : 7
     */

    public int code;
    public int totle;
    public List<RowsBean> rows;



    public static class RowsBean {
        /**
         * id : 123456
         * userType : 4
         * account : cll
         * phone : 13111111111
         * name : cll
         * email : 1@1.com
         * job : 常柳柳
         * idSysdept : 9519295C29644324E05011AC03000AF7
         * updateTimeSysuser : 2019-07-25 10:28:36
         * createTimeSysuser : 2019-07-25 10:28:36
         * deptNameCount : 高桥压缩站
         * sex : 1
         * lay1Sysuser : /upload/8a926d7ed24b485e9fa9bda11e74de00.jpg
         */

       public String id;
       public String userType;
       public String account;
       public String phone;
       public String name;
       public String email;
       public String job;
       public String idSysdept;
       public String updateTimeSysuser;
       public String createTimeSysuser;
       public String deptNameCount;
       public String sex;
       public String lay1Sysuser;


    }
}
