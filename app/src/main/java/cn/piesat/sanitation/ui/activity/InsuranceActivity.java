package cn.piesat.sanitation.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.ui.adapter.TabPagerAdapter;
import cn.piesat.sanitation.ui.fragment.FragmentOne;
import cn.piesat.sanitation.ui.fragment.FragmentTwo;


public class InsuranceActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<Fragment> listFragment;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_insuracnce;
    }

    @Override
    protected void initView() {
        tv_title.setText("保险年检");
        findViewById(R.id.img_back).setOnClickListener(v -> finish());
        listFragment =new ArrayList<>();
        listFragment.add(new FragmentOne());
        listFragment.add(new FragmentTwo());
        String[] title={"年检一","年检二"};
        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(),title,listFragment));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initData() {

    }
}
