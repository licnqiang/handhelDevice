package cn.piesat.sanitation.util;

import java.util.ArrayList;
import java.util.List;

import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.database.dbTab.RolesInfo_Tab;

/**
 * 讲用户角色Id 数组进行优先级排序
 */
public class RoleIdSortUtil {


    /**
     * 依次取最小的角色
     * @param roleIdList
     * @return
     */
    public static String getMinRoleId(List<RolesInfo_Tab>roleIdList){
        if (roleIdList.size()<1){
            return "";
        }
        List<String>idList=new ArrayList<>();

        for (int i = 0; i < roleIdList.size(); i++) {
            if (roleIdList.get(i).identity!=null){
                idList.add(roleIdList.get(i).identity);
            }

        }


        if (idList.contains("OR")){
            return "OR";
        }

        if (idList.contains("ORM")){
            return "ORM";
        }

        if (idList.contains("GDM")){
            return "GDM";
        }

        if (idList.contains("CB")){
            return "CB";
        }


   /*     List<String> roleIdList =new ArrayList<>();
        //角色按此顺序
        roleIdList.add("OR"); // 运营员工
        roleIdList.add("ORM"); //运营部长
        roleIdList.add("GDM"); //集团副总
        roleIdList.add("CB"); //集团老总

            for (int j = 0; j < idList.size(); j++) {

                if (idList.get(j).equals(roleIdList.get(0))){
                    return "OR";
                }

                if (idList.get(j).equals(roleIdList.get(1))){
                    return "ORM";
                }

                if (idList.get(j).equals(roleIdList.get(2))){
                    return "GDM";
                }

                if (idList.get(j).equals(roleIdList.get(3))){
                    return "CB";
                }

            }*/

        return "";
    }
}
