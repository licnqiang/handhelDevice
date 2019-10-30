package cn.piesat.sanitation.ui.activity;

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
import cn.piesat.sanitation.ui.adapter.StationUserAdapter;

public class StationAllUserActivity extends BaseActivity implements StationUserAdapter.OnRecyclerViewItemClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.RecylerView)
    RecyclerView RecylerView;
    private StationUserAdapter stationUserAdapter;
    private ArrayList<String> rowsBeanList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_station_all_user;
    }

    @Override
    protected void initView() {
        tvTitle.setText("站点人员");
        setCompressStationAdapter();
    }

    @Override
    protected void initData() {

    }

    /**
     * 人员列表
     */
    private void setCompressStationAdapter() {
        rowsBeanList = new ArrayList<>();
        stationUserAdapter = new StationUserAdapter(this, rowsBeanList);
        stationUserAdapter.setOnItemClickListener(this);
        RecylerView.setLayoutManager(new LinearLayoutManager(this));
        RecylerView.setAdapter(stationUserAdapter);
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onItemClick(View view, int position) {
        toActivity(EventListActivity.class);
    }
}
