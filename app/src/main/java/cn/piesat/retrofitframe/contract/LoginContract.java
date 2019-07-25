package cn.piesat.retrofitframe.contract;

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

    interface LoginPresenter {
        void login(String userName, String passWord);
    }
}
