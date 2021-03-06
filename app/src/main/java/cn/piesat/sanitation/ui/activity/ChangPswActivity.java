package cn.piesat.sanitation.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import butterknife.BindView;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.ui.view.CountDownTextView;
import cn.piesat.sanitation.util.DeviceTool;
import cn.piesat.sanitation.util.ToastUtil;

public class ChangPswActivity extends BaseActivity {


    HashMap<String, String> mHashMap = new HashMap<>();
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_user_yzm)
    EditText etUserYzm;
    @BindView(R.id.et_user_next_psw)
    EditText etUserNextPsw;
    @BindView(R.id.send_yzm)
    CountDownTextView sendYzm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_psw;
    }

    @Override
    protected void initView() {
        tvTitle.setText("修改密码");
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.img_back, R.id.btn_forgetPsw, R.id.send_yzm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_forgetPsw:
//                judgeData();
                finish();
                break;
            case R.id.send_yzm:
                String etUserPhone = etUserName.getText().toString();
                if (TextUtils.isEmpty(etUserPhone)) {
                    ToastUtil.show(this, "请输入手机号");
                    sendYzm.reset();
                } else {
                    if (!DeviceTool.isMobileNO(etUserPhone)) {
                        ToastUtil.show(this, "输入手机号有误");
                        sendYzm.reset();
                        return;
                    }
                    sendYzm.start();
                    mHashMap.put("userPhone", etUserPhone);
//                    NetControl.GetCode(callback, mHashMap);
                }
                break;
        }
    }

    private void judgeData() {
        String userName = etUserName.getText().toString().trim();
        String userYzm = etUserYzm.getText().toString().trim();
        String userNextPsw = etUserNextPsw.getText().toString().trim();

        if (userName.isEmpty() || userYzm.isEmpty() || userNextPsw.isEmpty()) {
            ToastUtil.show(ChangPswActivity.this, "填写所有信息");
        } else {
            mHashMap.clear();
            mHashMap.put("userPhone", userName);
            mHashMap.put("proof", userYzm);
            mHashMap.put("userPassword", userNextPsw);
//            NetControl.changpsw(registerCallback, mHashMap);
        }
    }

}
