package cn.piesat.sanitation.model.contract;

import cn.piesat.sanitation.data.CheckUpdateBean;

/**
 * @author lq
 * @fileName LoginContract
 * @data on  2019/7/25 15:44
 * @describe TODO
 */
public interface LoginContract {

    interface LoginView {
        void loginError(String errorMsg);
        void jumpToMain();
    }

    interface CheckVersionUpdate{
        void checkSuccess(CheckUpdateBean checkBean);
        void checkError(String msg);
    }

    interface LoginPresenter {
        void login(String userName, String passWord);
        void checkUpdate();
    }
}
