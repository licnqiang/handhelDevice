package cn.piesat.sanitation.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.ui.adapter.EventListAdapter;

public class EventListActivity extends BaseActivity implements EventListAdapter.OnRecyclerViewItemClickListener{

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.RecylerView)
    RecyclerView RecylerView;
    private EventListAdapter eventListAdapter;
    private ArrayList<String> rowsBeanList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_event_list;
    }

    @Override
    protected void initView() {
        tvTitle.setText("罗森");
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
        eventListAdapter = new EventListAdapter(this, rowsBeanList);
        eventListAdapter.setOnItemClickListener(this);
        RecylerView.setLayoutManager(new LinearLayoutManager(this));
        RecylerView.setAdapter(eventListAdapter);
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
