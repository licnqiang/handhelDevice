package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
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
import cn.piesat.sanitation.ui.fragment.InsuranceReviewedFragment;
import cn.piesat.sanitation.ui.fragment.InsuranceReviewFragment;

/**
 * 保险年检
 */
public class InsuranceActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.iv_right)
    ImageView iv_right;


    private List<Fragment> listFragment;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_insuracnce;
    }

    @Override
    protected void initView() {
        tv_title.setText("保险年检");
        findViewById(R.id.img_back).setOnClickListener(v -> finish());


        iv_right.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.icon_add));
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setOnClickListener(v -> startActivityForResult(new Intent(this,AddInsuranceActivity.class).putExtra("isEdit",true),0));




        listFragment =new ArrayList<>();
        listFragment.add(new InsuranceReviewedFragment());
        listFragment.add(new InsuranceReviewFragment());
        String[] title={"待审批","已审批"};
        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(),title,listFragment));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initData() {

    }
}
