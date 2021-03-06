package cn.piesat.sanitation.database.dbTab;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

import cn.piesat.sanitation.database.AppDatabase;


@Table(database = AppDatabase.class)
public class UserInfo_Tab extends BaseModel implements Serializable {
    @PrimaryKey(autoincrement = true)//ID自增
    public long _id;
    @Column
    public String id;    //ID
    @Column
    public int userType; // 1管理员 2普通用户 3环卫集团员工 4站长 5操作工 6扫保人员 7司机
    @Column
    public String account;  //账号
    @Column
    public String phone;    //电话
    @Column
    public String telephone; //固话
    @Column
    public String address;   //地址
    @Column
    public String name;      //姓名
    @Column
    public String sex;       //性别 0女 1男
    @Column
    public String birthday;  //出生年月
    @Column
    public String email;     //电子邮箱
    @Column
    public String job;       //职位
    @Column
    public String idSysdept; //组织机构id
    @Column
    public String token;     //用户标识
    @Column
    public String status;    //1 正常 0 禁用
    @Column
    public String yszIdCount; //站点id
    @Column
    public String updateTimeSysuser;
    @Column
    public String createTimeSysuser;
    @Column
    public String updateUserSysuser;
    @Column
    public String createUserSysuser;
    @Column
    public String onlineTime;
    @Column
    public String lay1Sysuser;  //照片
    @Column
    public String lay2Sysuser;
    @Column
    public String lay3Sysuser;
    @Column
    public String lay4Sysuser;
    @Column
    public String lay5Sysuser;
    @Column
    public String deptNameCount;  //组织机构名称(聚合查询，非原对象，仅作显示)
    @Column
    public String areaCount;  //行政区划

    //以下是司机用户 特有的信息字段
    @Column
    public String certfirsttime;   //驾驶证初领日期
    @Column
    public String idCard;         //证件号码
    @Column
    public String createtimeBizdriver;
    @Column
    public String remark;         //说明
    @Column
    public String eqccertfirsttime;   //资格证初次领证日期
    @Column
    public String idSysuser;     //用户ID
    @Column
    public String nativeplace;   //籍贯
    @Column
    public String eqcNo;         //从业资格证号
    @Column
    public String driverNo;      //工号
    @Column
    public String yearCheck;     //驾驶证年审日期
    @Column
    public String yszNameCount;  //垃圾压缩站站点id名称（若未分配站点则为空）
    @Column
    public String idType;        //证件类型（数据字典 - zjlx）
    @Column
    public String updatetimeBizdriver;
    @Column
    public String idBizdriver;       //司机ID
    @Column
    public String eqcnumberchecktime;  //资格证年审日期
    @Column
    public String createUserBizdriver;
    @Column
    public String updateUserBizdriver;
    @Column
    public String userTypeX;
    @Column
    public String issueUnit;     //签发机关

}
