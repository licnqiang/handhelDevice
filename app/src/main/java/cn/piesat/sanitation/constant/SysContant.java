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
     * 公用选择界面切换标签
     */
    public interface QueryType {
        String query_type = "query_type"; //key
        String compress_station_key = "compress_station_key"; //压缩站标识
        String car_key = "car_key"; //车辆标识
        String driver_key = "driver_key"; //司机标识
        String burn_key = "burn_key"; //焚烧厂标识
        int compress_station_code=100;  //压缩站传递数据给上个界面
        int car_code=101;  //压缩站传递数据给上个界面
        int driver_code=102;  //司机传递数据给上个界面
        int burn_code=103;  //焚烧厂传递数据给上个界面

    }


    /**
     * 公用标签
     */
    public interface CommentTag {
        String comment_key = "comment_key"; //key
        String face_tag_entering = "face_tag_entering"; //头像录入
        String face_tag_verify = "face_tag_verify"; //头像验证

    }


    /**
     * 用户配置
     */
    public interface userInfo {
        String USER_TOKEN = "user_token";

    }

}

