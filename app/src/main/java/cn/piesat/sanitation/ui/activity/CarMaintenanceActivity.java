package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.constant.SysContant;
import cn.piesat.sanitation.data.CarMaintenance;
import cn.piesat.sanitation.model.contract.CarMaintenanceContract;
import cn.piesat.sanitation.model.presenter.CarMaintenancePresenter;
import cn.piesat.sanitation.ui.adapter.CarMaintenanceAdapter;
import cn.piesat.sanitation.ui.adapter.EventListAdapter;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 车辆维保
 */
public class CarMaintenanceActivity extends BaseActivity implements CarMaintenanceContract.CarMaintenanceView, CarMaintenanceAdapter.OnRecyclerViewItemClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.RecylerView)
    RecyclerView RecylerView;
    @BindView(R.id.springView)
    SpringView springView;
    private int pageNum=1;
    CarMaintenancePresenter carMaintenancePresenter;
    CarMaintenanceAdapter carMaintenanceAdapter;
    private ArrayList<CarMaintenance.RowsBean> rowsBeanList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_car_maintenance;
    }

    @Override
    protected void initView() {
        tvTitle.setText("维保信息");
        showLoadingDialog();
        carMaintenancePresenter = new CarMaintenancePresenter(this);
    }

    @Override
    protected void initData() {
        setCompressStationAdapter();
    }

    /**
     * 人员列表
     */
    private void setCompressStationAdapter() {
        rowsBeanList = new ArrayList<>();
        carMaintenanceAdapter = new CarMaintenanceAdapter(this, rowsBeanList);
        carMaintenanceAdapter.setOnItemClickListener(this);
        RecylerView.setLayoutManager(new LinearLayoutManager(this));
        RecylerView.setAdapter(carMaintenanceAdapter);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNum=1;
                carMaintenancePresenter.QueryCarMaintenance(pageNum, SysContant.CommentTag.pageSize);
            }

            @Override
            public void onLoadmore() {
                pageNum++;
                carMaintenancePresenter.QueryCarMaintenance(pageNum, SysContant.CommentTag.pageSize);
            }
        });
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carMaintenancePresenter.QueryCarMaintenance(pageNum, SysContant.CommentTag.pageSize);
    }

    @Override
    public void Error(String errorMsg) {
        if (null!=springView){
            springView.onFinishFreshAndLoad();
        }
        dismiss();
        ToastUtil.show(this, errorMsg);
    }

    @Override
    public void SuccessFinsh(CarMaintenance carMaintenance) {
        if (null!=springView){
            springView.onFinishFreshAndLoad();
        }
        dismiss();
        if (null!=carMaintenance.rows&&carMaintenance.rows.size()>0){

            if (pageNum==1){
                carMaintenanceAdapter.refreshData(carMaintenance.rows);
            }else {
                carMaintenanceAdapter.addAll(carMaintenance.rows);
            }
        }else {
            ToastUtil.show(this,"没有更多数据咯!");
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        startActivity(new Intent(this,CarSerViceDetailShowActivity.class).putExtra(SysContant.CommentTag.comment_key,rowsBeanList.get(position)));
    }
}
