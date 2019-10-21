package cn.piesat.sanitation.data;

import java.util.List;

/**
 * @author lq
 * @fileName User
 * @data on  2019/7/25 15:52
 * @describe TODO
 */
public class User {

    /**
     * menus : [{"idSysmenu":"A22593C938EB4F65BDDAD201DDC914DC","menuName":"个人中心","type":"1","menuType":"2"}]
     * power : [edit:userinfo]
     * user : {"id":"D5311DFA203E4DA5A3E8188CE7B27AA8","userType":"7","account":"sj","phone":"13299090458","name":"A司机","sex":"1","idSysdept":"9519295C29644324E05011AC03000AF7","status":"1","deptNameCount":"高桥压缩站"}
     * token : 3ee60676-6797-486c-80ef-42c2328b6db4
     */

    private String power;
    private UserBean user;
    private String token;
    private List<MenusBean> menus;

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<MenusBean> getMenus() {
        return menus;
    }

    public void setMenus(List<MenusBean> menus) {
        this.menus = menus;
    }

    public static class UserBean {
        /**
         * id : D5311DFA203E4DA5A3E8188CE7B27AA8
         * userType : 7
         * account : sj
         * phone : 13299090458
         * name : A司机
         * sex : 1
         * idSysdept : 9519295C29644324E05011AC03000AF7
         * status : 1
         * deptNameCount : 高桥压缩站
         */

        private String id;
        private String userType;
        private String account;
        private String phone;
        private String name;
        private String sex;
        private String idSysdept;
        private String status;
        private String deptNameCount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getIdSysdept() {
            return idSysdept;
        }

        public void setIdSysdept(String idSysdept) {
            this.idSysdept = idSysdept;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDeptNameCount() {
            return deptNameCount;
        }

        public void setDeptNameCount(String deptNameCount) {
            this.deptNameCount = deptNameCount;
        }
    }

    public static class MenusBean {
        /**
         * idSysmenu : A22593C938EB4F65BDDAD201DDC914DC
         * menuName : 个人中心
         * type : 1
         * menuType : 2
         */

        private String idSysmenu;
        private String menuName;
        private String type;
        private String menuType;

        public String getIdSysmenu() {
            return idSysmenu;
        }

        public void setIdSysmenu(String idSysmenu) {
            this.idSysmenu = idSysmenu;
        }

        public String getMenuName() {
            return menuName;
        }

        public void setMenuName(String menuName) {
            this.menuName = menuName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMenuType() {
            return menuType;
        }

        public void setMenuType(String menuType) {
            this.menuType = menuType;
        }
    }
}
