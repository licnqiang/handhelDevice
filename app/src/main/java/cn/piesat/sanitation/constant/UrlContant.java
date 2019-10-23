package cn.piesat.sanitation.constant;

/**
 * @author lq
 * @fileName UrlContant
 * @data on  2019/7/18 16:18
 * @describe TODO
 */
public class UrlContant {

    /**
     * 外协提供的接口
     */
    public interface OutSourcePart {

        String part = "v1";
        String login = "login";

        String query_compress_station = "getRoleYsz";
        String query_car = "getRoleCar";
        String query_burn_staion = "getRoleFsc";
        String upload = "upload";

    }


    /**
     * 王浩提供的接口
     */
    public interface MySourcePart {

        String part = "api/punch";
        String check_set_par = "api/attendance";


        String check_record = "getOneByUserIdAndCreateDate";
        String check_set = "param/listBySiteId";


    }


    //注册
    public static final String register = "register";
    //忘记密码
    public static final String forget_psw = "forget_psw";
    //修改密码
    public static final String modify_psw = "modify_psw";
    //获取验证码
    public static final String get_code = "get_code";
}



