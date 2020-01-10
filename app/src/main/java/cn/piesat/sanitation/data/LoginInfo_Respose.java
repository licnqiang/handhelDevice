package cn.piesat.sanitation.data;

import java.util.List;

import cn.piesat.sanitation.database.dbTab.RolesInfo_Tab;
import cn.piesat.sanitation.database.dbTab.UserInfo_Tab;

/**
 * @author lq
 * @fileName 登陆返回信息
 * @data on  2019/10/21 17:05
 * @describe TODO
 */
public class LoginInfo_Respose {
    public List<RolesInfo_Tab> roles;
    public String power;
    public UserInfo_Tab user;
    public String token;
    public List<LoginMenus_Response> menus;

}
