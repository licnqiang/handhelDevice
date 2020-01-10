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
        String assigin_order_part = "tyd";
        String car_state_part = "carfault";     //车辆故障上报
        String car_service_part = "repair";     //车辆维保上报
        String car_maintencen_port_list="repaircarView";  //查询车辆维保


        String car_service_sumbit = "reportRepair";  //车辆故障上报
        String car_falut_sumbit = "reportFault";  //车辆故障上报
        String login = "login";
        String query_compress_station = "getRoleYsz";
        String query_car = "getRoleCar";
        String query_burn_staion = "getAllFsc";
        String query_driver = "findRoleUser";
        String upload = "upload";
        String mody_user_pic = "editUser";
        String assigin_order = "toTyd";
        String query_order_list = "searchTyd";
        String query_take_order = "driverTyd";
        String query_start_strans = "startTyd";  //起运
        String query_save_bd = "saveBDTyd";  //榜单
        String query_finish_bd = "finishTyd";  //过磅确认
        String query_cancel_order = "cancelTyd";  //订单取消
        String user_list="findRoleUser"; //查看站点人员
        String car_maintencen_list="getAll";  //查询车辆维保
        String car_break_down_list="getAllFault";  //查询车辆故障


        String car_maintencen_audit_par="repair";  //车辆维保审批
        String car_maintencen_audit="approvalFault";  //车辆维保审批

        String car_break_down_audit_par="carfault";  //车辆故障审批
        String car_break_down_audit="approvalFault";  //车辆故障审批

    }


    /**
     * 王浩提供的接口
     */
    public interface MySourcePart {

        String part = "api/punch";
        String check_set_par = "api/attendance";
        String face_part = "api/piesat/face";


        String check_record = "getOneByUserIdAndCreateDate";
        String check_set = "param/listBySiteId";  //获取考勤时间规定
        String check_user = "save";  //用户打卡


        String new_part="api/news";
        String new_List="list";

        String face="contrast";

        String attendance_part="api/punch";
        String attendance_List="getDataOfMonthByUserId";

        String violate_report_get="api";
        String violate_report_add="IllegalReport/save";
        String violate_report_list="IllegalReport/list";
        String violate_report_select_id="IllegalReport/selectbyid";
        String violate_report_delete="IllegalReport/delete";
        //加油
        String gasoline_report_get="api";
        String gasoline_report_add="OilReport/save";
        String gasoline_report_list="OilReport/list";

        String accident_report_get="api/AccidentReport";
        String accident_report_add="save";
        String accident_report_list="list";
        String accident_report_select_id="selectbyid";

        //启动流程
        String start_report="api/flow/startFlow";
        //审批流程
        String approval_hanlder="api/flow/apprFlow";


        //添加保养审批
        String add_upkeep_report="api/MaintenanceApproval/save";

        //保养未审批列表
        String upkeep_approval_no="api/MaintenanceApproval/pendinglist";
        //保养已审批列表
        String upkeep_approval_is="api/MaintenanceApproval/auditedlistddd";
        //保养已审批列表
        String upkeep_approval_list="api/MaintenanceApproval/auditedlist";

        //添加维修审批
        String add_maintain_report="api/RepairApproval/save";
        //维修未审批列表
        String maintain_approval_no="api/RepairApproval/pendinglist";
        //维修已审批列表
        String maintain_approval_is="api/RepairApproval/auditedlists";

        //维修列表
        String maintain_approval_list="api/RepairApproval/auditedlist";

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



