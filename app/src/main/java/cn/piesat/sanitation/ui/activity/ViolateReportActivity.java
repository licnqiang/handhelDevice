package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;

/**
 * 站长  违章上报
 * Created by sen.luo on 2020/1/2.
 */
public class ViolateReportActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_right)
    ImageView iv_right;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_violate_report;
    }

    @Override
    protected void initView() {
        tv_title.setText("违章上报");
        findViewById(R.id.img_back).setOnClickListener(v -> finish());
        iv_right.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.icon_add));
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setOnClickListener(v -> startActivity(new Intent(this,AddViolateReportActivity.class)));
    }

    @Override
    protected void initData() {

    }
}
