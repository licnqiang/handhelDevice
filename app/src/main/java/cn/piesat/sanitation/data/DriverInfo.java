package cn.piesat.sanitation.data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lq
 * @fileName DriverInfo
 * @data on  2019/10/24 10:54
 * @describe TODO
 */
public class DriverInfo implements Serializable{


    public int code;
    public int totle;
    public List<RowsBean> rows;


    public static class RowsBean implements Serializable {

        public String idCard;  //14051119700101011x
        public String idSysuser; //E46DF34C93B3469CB7D610C77B202C88
        public String id;        //E46DF34C93B3469CB7D610C77B202C88
        public String idSysdept;  //9519295C29644324E05011AC03000AF7
        public String telephone;     //13299090458
        public String idBizdriver;    //7D03896875B54485BDEC8112B5280BE8
        public String deptNameCount;  //高桥压缩站
        public String name;           //B司机
        public String birthday;
        public String updateUserSysuser;
        public String certfirsttime;
        public String createtimeBizdriver;
        public String remark;
        public String eqccertfirsttime;
        public String nativeplace;
        public String eqcNo;
        public String email;
        public String driverNo;
        public String yearCheck;
        public String createUserSysuser;
        public String address;
        public String idType;
        public String sex;
        public String lay1Sysuser;
        public String lay2Sysuser;
        public String lay3Sysuser;
        public String lay4Sysuser;
        public String createTimeSysuser;
        public String updatetimeBizdriver;
        public String updateTimeSysuser;
        public String eqcnumberchecktime;
        public String phone;
        public String lay5Sysuser;
        public String createUserBizdriver;
        public String updateUserBizdriver;
        public String userType;
        public String job;
        public String account;
        public String issueUnit;
    }
}
