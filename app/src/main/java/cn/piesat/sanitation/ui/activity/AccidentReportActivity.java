package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;

import butterknife.BindView;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;


/**
 * 站长事故上报列表
 *  Created by sen.luo on 2020/1/3.
 */
public class AccidentReportActivity extends BaseActivity {
    @BindView(R.id.lvAccident)
    ListView lvAccident;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.springView)
    SpringView springView;
    @BindView(R.id.iv_right)
    ImageView iv_right;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_accident_report;
    }

    @Override
    protected void initView() {
        tv_title.setText("事故上报");
        iv_right.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.icon_add));
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setOnClickListener(v -> startActivity(new Intent(this,AddAccidentReportActivity.class).putExtra("isEdit",true)));



    }

    @Override
    protected void initData() {

    }
}
