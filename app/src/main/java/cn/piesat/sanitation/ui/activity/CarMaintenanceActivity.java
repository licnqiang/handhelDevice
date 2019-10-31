package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carMaintenancePresenter.QueryCarMaintenance(0, -1);
    }

    @Override
    public void Error(String errorMsg) {
        dismiss();
        ToastUtil.show(this, errorMsg);
    }

    @Override
    public void SuccessFinsh(CarMaintenance carMaintenance) {
        dismiss();
        carMaintenanceAdapter.refreshData(carMaintenance.rows);
    }

    @Override
    public void onItemClick(View view, int position) {
        startActivity(new Intent(this,CarSerViceDetailShowActivity.class).putExtra(SysContant.CommentTag.comment_key,rowsBeanList.get(position)));
    }
}
