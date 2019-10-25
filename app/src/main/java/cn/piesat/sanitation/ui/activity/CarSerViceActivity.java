package cn.piesat.sanitation.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;

/**
 * 汽车维保
 */
public class CarSerViceActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_car_ser_vice;
    }

    @Override
    protected void initView() {
        tvTitle.setText("汽车维保");
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.img_back, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_submit:
                break;
        }
    }
}
