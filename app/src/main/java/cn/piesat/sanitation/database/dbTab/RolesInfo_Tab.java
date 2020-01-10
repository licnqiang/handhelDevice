package cn.piesat.sanitation.database.dbTab;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

import cn.piesat.sanitation.database.AppDatabase;

/**
 * @author lq
 * @fileName RolesInfo
 * @data on  2020/1/10 17:24
 * @describe TODO
 */
@Table(database = AppDatabase.class)
public class RolesInfo_Tab extends BaseModel implements Serializable {
    @PrimaryKey(autoincrement = true)//ID自增
    public long _id;
    @Column
    public String idSysrole;
    @Column
    public String roleName;    //角色名称
    @Column
    public String description; //角色描述
    @Column
    public String idSysdept;
    @Column
    public String vieworder;
    @Column
    public String identity;   //"OR" 运营员工  "ORM" 运营部长  "GDM" 集团副总  "CB" 集团老总 "FD" 财务人员 "PP" 采购人员
    @Column
    public String updatetimeSysrole;
    @Column
    public String createtimeSysrole;
    @Column
    public String updateUserSysrole;
    @Column
    public String createUserSysrole;
    @Column
    public String flagsSysrole;
}
