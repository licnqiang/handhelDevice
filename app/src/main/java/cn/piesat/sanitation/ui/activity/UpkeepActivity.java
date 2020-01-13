package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.data.UpKeepList;
import cn.piesat.sanitation.model.contract.UpKeepReportContract;
import cn.piesat.sanitation.model.presenter.UpkeepReportPresenter;
import cn.piesat.sanitation.ui.adapter.TabPagerAdapter;
import cn.piesat.sanitation.ui.adapter.UpKeepOrderAdapter;
import cn.piesat.sanitation.ui.fragment.FragmentUpkeepIs;
import cn.piesat.sanitation.ui.fragment.FragmentUpkeepNo;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 保养
 */
public class UpkeepActivity extends BaseActivity implements UpKeepOrderAdapter.OnRecyclerViewItemClickListener,UpKeepReportContract.getUpkeepReportSIView{
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_right)
    ImageView iv_right;

    @BindView(R.id.RecylerView)
    RecyclerView RecylerView;
    @BindView(R.id.springView)
    SpringView springView;

    private UpKeepOrderAdapter adapter;
    List<UpKeepList.RecordsBean> rowsBeans;
    UpkeepReportPresenter upkeepReportPresenter;
    private int pageNum=1;

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

        rowsBeans = new ArrayList<>();
        adapter = new UpKeepOrderAdapter(this, rowsBeans);
        adapter.setOnItemClickListener(this);
        RecylerView.setLayoutManager(new LinearLayoutManager(this));
        RecylerView.setAdapter(adapter);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNum=1;
                upkeepReportPresenter.getUpkeepReportList(pageNum);
            }

            @Override
            public void onLoadmore() {
                pageNum++;
                upkeepReportPresenter.getUpkeepReportList(pageNum);
            }
        });
    }

    @Override
    protected void initData() {
        upkeepReportPresenter=new UpkeepReportPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
//        showLoadingDialog("加载中", false);
        pageNum=1;
        upkeepReportPresenter.getUpkeepReportList(pageNum);
    }

    @Override
    public void onItemClick(View view, int position) {
        UpKeepList.RecordsBean rowsBean = rowsBeans.get(position);
        startActivity(new Intent(this, UpKeepReportDetailActivity.class).putExtra(SysContant.CommentTag.comment_key, rowsBean));
    }

    @Override
    public void SuccessOnReportList(UpKeepList gasonLines) {
        if (null!=springView){
            springView.onFinishFreshAndLoad();
        }
        dismiss();
        if (null!=gasonLines.records&&gasonLines.records.size()>0){

            if (pageNum==1){
                adapter.refreshData(gasonLines.records);
            }else {
                adapter.addAll(gasonLines.records);
            }
        }else {
            ToastUtil.show(this,"没有更多数据咯!");
        }
    }

    @Override
    public void Error(String msg) {
        if (null!=springView){
            springView.onFinishFreshAndLoad();
        }
        dismiss();
        ToastUtil.show(this, msg);
    }
}
