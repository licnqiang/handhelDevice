package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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
import cn.piesat.sanitation.data.GasonLines;
import cn.piesat.sanitation.data.OrderList;
import cn.piesat.sanitation.model.contract.GasonLineReportContract;
import cn.piesat.sanitation.model.presenter.GasonLineReportPresenter;
import cn.piesat.sanitation.model.presenter.OrderPresenter;
import cn.piesat.sanitation.ui.adapter.GasonLineOrderAdapter;
import cn.piesat.sanitation.ui.adapter.OrderAdapter;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 站长  加油上报
 * Created by sen.luo on 2020/1/2.
 */
public class GasoLineReportActivity extends BaseActivity implements GasonLineOrderAdapter.OnRecyclerViewItemClickListener,GasonLineReportContract.getGasonLineReportSIView{

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_right)
    ImageView iv_right;
    @BindView(R.id.RecylerView)
    RecyclerView RecylerView;
    @BindView(R.id.springView)
    SpringView springView;
    private GasonLineOrderAdapter adapter;

    List<GasonLines.RecordsBean> rowsBeans;

    GasonLineReportPresenter gasonLineReportPresenter;
    private int pageNum=1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gason_line_list;
    }

    @Override
    protected void initView() {
        tv_title.setText("加油上报");
        findViewById(R.id.img_back).setOnClickListener(v -> finish());
        iv_right.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.icon_add));
        iv_right.setVisibility(3!= BaseApplication.getUserInfo().userType?View.VISIBLE:View.GONE); //判断用户类型是否是集团人员  目前集团人员仅仅开放查看功能
        iv_right.setOnClickListener(v -> startActivity(new Intent(this,AddGasoLineReportActivity.class)));

        rowsBeans = new ArrayList<>();
        adapter = new GasonLineOrderAdapter(this, rowsBeans);
        adapter.setOnItemClickListener(this);
        RecylerView.setLayoutManager(new LinearLayoutManager(this));
        RecylerView.setAdapter(adapter);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNum=1;
                gasonLineReportPresenter.getGosolineReportList(pageNum);
            }

            @Override
            public void onLoadmore() {
                pageNum++;
                gasonLineReportPresenter.getGosolineReportList(pageNum);
            }
        });
    }

    @Override
    protected void initData() {
        gasonLineReportPresenter=new GasonLineReportPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLoadingDialog("加载中", false);
        pageNum=1;
        gasonLineReportPresenter.getGosolineReportList(pageNum);
    }

    @Override
    public void onItemClick(View view, int position) {
        GasonLines.RecordsBean rowsBean = rowsBeans.get(position);
        startActivity(new Intent(this, GasoLineReportDetailActivity.class).putExtra(SysContant.CommentTag.comment_key, rowsBean));
    }

    @Override
    public void SuccessOnReportList(GasonLines gasonLines) {
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
