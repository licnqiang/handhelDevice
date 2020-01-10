package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.data.InsuranceBean;
import cn.piesat.sanitation.model.contract.InsuranceContract;
import cn.piesat.sanitation.model.presenter.InsurancePresenter;
import cn.piesat.sanitation.ui.adapter.InsuranceAdapter;
import cn.piesat.sanitation.util.ToastUtil;


/**
 * 保险年检
 */
public class InsuranceActivity extends BaseActivity implements InsuranceContract.GetInsuranceListIView {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_right)
    ImageView iv_right;
/*    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;*/

    @BindView(R.id.lvInsurance)
    ListView lvInsurance;
    @BindView(R.id.springView)
    SpringView springView;


    private int pageNumber=1;
    private List<InsuranceBean.InsuranceListBean>insuranceList;
    private InsuranceAdapter insuranceAdapter;
    private InsurancePresenter insurancePresenter;
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

/*
        listFragment =new ArrayList<>();
        listFragment.add(new InsuranceReviewFragment());
        listFragment.add(new InsuranceReviewedFragment());
        String[] title={"待审批","已审批"};
        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(),title,listFragment));
        tabLayout.setupWithViewPager(viewPager);*/

        insurancePresenter=new InsurancePresenter(this);
        insuranceList=new ArrayList<>();
        insuranceAdapter =new InsuranceAdapter(this,insuranceList);
        lvInsurance.setAdapter(insuranceAdapter);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNumber=1;
                initData();
            }

            @Override
            public void onLoadmore() {
                pageNumber++;
                initData();
            }
        });
        lvInsurance.setOnItemClickListener((parent, view, position, id) -> {
            startActivity(new Intent(this,AddInsuranceActivity.class)
                    .putExtra("isEdit",false)
                    .putExtra("report_id",insuranceList.get(position).id));
        });
    }

    @Override
    protected void initData() {
        Map<String,String> map =new HashMap<>();
        map.put("curren", String.valueOf(pageNumber));
        map.put("size", SysContant.CommentTag.pageSize);
        map.put("userId", BaseApplication.getIns().getUserId());
        map.put("siteName", BaseApplication.getIns().getSiteName());
        map.put("roleId", BaseApplication.getIns().getUserRoleId());
        showLoadingDialog();
        insurancePresenter.getInsuranceList(map);
    }

    @Override
    public void error(String msg) {
        dismiss();
        ToastUtil.show(this,msg);
        if (null!=springView){
            springView.onFinishFreshAndLoad();
        }
    }

    @Override
    public void getInsuranceListOnSuccess(InsuranceBean insuranceBean) {
        dismiss();
        if (null!=springView){
            springView.onFinishFreshAndLoad();
        }
        if (insuranceBean.records!=null&&insuranceBean.records.size()>0){
            if (pageNumber==1){
                insuranceList.clear();
            }
            insuranceList.addAll(insuranceBean.records);
            insuranceAdapter.notifyDataSetChanged();

        }else {
            ToastUtil.show(this,"没有更多数据!");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==0){
            pageNumber=1;
            initData();
        }
    }
}
