package cn.piesat.sanitation.constant;

/**
 * @author lq
 * @fileName 系统常量类
 * @data on  2019/2/18 10:38
 * @describe 保存配置
 */
public class SysContant {


    //数据库相关常量
    public interface DBContats {
        String db_name = "AppDatabase";   //数据库名
        int db_version = 1;               //数据库版本号
        String db_psw = SystemContats.PROJECT_NAME+"123456";      //数据库密码
    }

    //系统设置相关常量

    public interface SystemContats{
        String PROJECT_NAME = "sanitation";
    }


    /**
     * 用户配置
     */
    public interface userInfo {
        String USER_TOKEN = "user_token";

    }

}

