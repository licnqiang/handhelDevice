package cn.piesat.retrofitframe.database.dbTab;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

import cn.piesat.retrofitframe.database.AppDatabase;


@Table(database = AppDatabase.class)
public class UserInfo_Tab extends BaseModel implements Serializable {
    @PrimaryKey(autoincrement = true)//ID自增
    public long id;
    @Column
    public String userName; //姓名

}
