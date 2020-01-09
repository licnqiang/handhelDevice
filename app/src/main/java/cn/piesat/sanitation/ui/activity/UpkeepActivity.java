package cn.piesat.sanitation.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.ui.adapter.TabPagerAdapter;
import cn.piesat.sanitation.ui.fragment.FragmentUpkeepIs;
import cn.piesat.sanitation.ui.fragment.FragmentUpkeepNo;

/**
 * 保养
 */
public class UpkeepActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_right)
    ImageView iv_right;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<Fragment> listFragment;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_upkeep;
    }

    @Override
    protected void initView() {
        tv_title.setText("保养审批");
        findViewById(R.id.img_back).setOnClickListener(v -> finish());
        iv_right.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.icon_add));
        iv_right.setVisibility(3!= BaseApplication.getUserInfo().userType?View.VISIBLE:View.GONE); //判断用户类型是否是集团人员  目前集团人员仅仅开放查看功能
        iv_right.setOnClickListener(v -> toActivity(AddUpKeepReportActivity.class));
        listFragment =new ArrayList<>();
        listFragment.add(new FragmentUpkeepNo());
        listFragment.add(new FragmentUpkeepIs());
        String[] title={"待审批","已审批"};
        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(),title,listFragment));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initData() {

    }
}
