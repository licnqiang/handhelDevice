package cn.piesat.sanitation.ui.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;

import butterknife.BindView;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;

/**
 * 保养
 */
public class UpkeepActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.springView)
    SpringView springView;
    @BindView(R.id.iv_right)
    ImageView iv_right;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_upkeep;
    }

    @Override
    protected void initView() {
        tv_title.setText("事故上报");
        findViewById(R.id.img_back).setOnClickListener(v -> finish());
        iv_right.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.icon_add));
        iv_right.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {

    }
}
