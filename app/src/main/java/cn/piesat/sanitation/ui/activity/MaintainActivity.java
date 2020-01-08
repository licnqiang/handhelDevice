package cn.piesat.sanitation.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.ui.adapter.TabPagerAdapter;
import cn.piesat.sanitation.ui.fragment.FragmentMaintainIs;
import cn.piesat.sanitation.ui.fragment.FragmentMaintainNo;
import cn.piesat.sanitation.ui.fragment.FragmentUpkeepIs;
import cn.piesat.sanitation.ui.fragment.FragmentUpkeepNo;

/**
 * 维修
 */
public class MaintainActivity extends BaseActivity {
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
        return R.layout.activity_maintain;
    }

    @Override
    protected void initView() {
        tv_title.setText("维修审批");
        findViewById(R.id.img_back).setOnClickListener(v -> finish());
        iv_right.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.icon_add));
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setOnClickListener(v -> toActivity(AddMaintainReportActivity.class));
        listFragment =new ArrayList<>();
        listFragment.add(new FragmentMaintainNo());
        listFragment.add(new FragmentMaintainIs());
        String[] title={"待审批","已审批"};
        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(),title,listFragment));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initData() {

    }
}
