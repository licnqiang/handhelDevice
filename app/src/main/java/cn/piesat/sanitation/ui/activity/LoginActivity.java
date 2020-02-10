package cn.piesat.sanitation.ui.activity;


import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import java.util.HashMap;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.database.dbTab.UserInfo_Tab;
import cn.piesat.sanitation.model.contract.LoginContract;
import cn.piesat.sanitation.model.presenter.loginPresenter;
import cn.piesat.sanitation.util.LogUtil;
import cn.piesat.sanitation.util.ToastUtil;


public class LoginActivity extends BaseActivity implements LoginContract.LoginView{

    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_user_psw)
    EditText etUserPsw;

    private loginPresenter loginPresenterImp;

    @Override
    protected int getLayoutId() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.regitster, R.id.forget_psw, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.regitster:
                toActivity(RegisterActivity.class);
                break;
            case R.id.forget_psw:
                toActivity(ForgetPswActivity.class);
                break;
            case R.id.btn_login:
                login();
                break;
        }
    }


    private void login(){
        String userName = etUserName.getText().toString().trim();
        String userPsw = etUserPsw.getText().toString().trim();
        if (userName.isEmpty() || userPsw.isEmpty()) {
            ToastUtil.show(LoginActivity.this, "用户名或密码不能为空");
        } else {
            showLoadingDialog("正在登陆",false);
            loginPresenterImp = new loginPresenter(this);
            loginPresenterImp.login(userName, userPsw);
        }
    }


    @Override
    public void loginError(String errorMsg) {
        dismiss();
        ToastUtil.show(this,errorMsg);
    }

    @Override
    public void jumpToMain() {



        if (BaseApplication.getUserInfo()!=null&!TextUtils.isEmpty(BaseApplication.getUserInfo().id)){
            setJPushAlias("user_"+BaseApplication.getUserInfo().id);
        }else {
            dismiss();
            ToastUtil.show(this,"推送Alias设置失败");
            toActivity(MainActivity.class);
            finish();
        }


    }

    /**
     * 登录成功后设置推送标签
     */
    private void setJPushAlias(String aliasId) {
        JPushInterface.setAlias(this, aliasId, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                dismiss();
                toActivity(MainActivity.class);
                finish();
                LogUtil.e("设置别名结果：","i:"+i+"/s:"+s+"/set:"+set);
            }
        });

    }
}

