package cn.piesat.sanitation.ui.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 行政审批
 */
public class StationHeaderApproveActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_station_header_work;
    }

    @Override
    protected void initView() {
        tvTitle.setText("行政审批");
    }

    @Override
    protected void initData() {
    }


    @OnClick({R.id.img_back, R.id.baoxian_nianjian, R.id.weixiu_shenpi, R.id.baoyang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.baoxian_nianjian:
                toActivity(InsuranceActivity.class);
//                ToastUtil.show(this,"保险");
                break;
            case R.id.weixiu_shenpi:
                ToastUtil.show(this,"维修");
                break;
            case R.id.baoyang:
                ToastUtil.show(this,"保养");
                break;
        }
    }
}
