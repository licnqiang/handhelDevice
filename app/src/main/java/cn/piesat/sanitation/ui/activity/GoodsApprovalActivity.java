package cn.piesat.sanitation.ui.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;

import butterknife.BindView;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.ui.adapter.GoodsApprovalAdapter;


/**
 * 易耗件 审批
 * Created by sen.luo on 2020-02-17.
 */
public class GoodsApprovalActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.springView)
    SpringView springView;
    @BindView(R.id.lvApproval)
    ListView lvApproval;
    @BindView(R.id.iv_right)
    ImageView iv_right;

    private GoodsApprovalAdapter goodsApprovalAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_approval_goods;
    }

    @Override
    protected void initView() {
        tv_title.setText("耗材审批");
        findViewById(R.id.img_back).setOnClickListener(v -> finish());
        iv_right.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.icon_add));
        iv_right.setVisibility(3!= BaseApplication.getUserInfo().userType? View.VISIBLE:View.GONE); //判断用户类型是否是集团人员  目前集团人员仅仅开放查看功能
        iv_right.setOnClickListener(v -> startActivityForResult(new Intent(this,AddGoodsApprovalActivity.class).putExtra("isEdit",true),0));
        lvApproval.setOnItemClickListener((adapterView, view, i, l) ->
                startActivity(new Intent(this,AddGoodsApprovalActivity.class)
                        .putExtra("isEdit",false)));
        goodsApprovalAdapter =new GoodsApprovalAdapter(this);
        lvApproval.setAdapter(goodsApprovalAdapter);

    }

    @Override
    protected void initData() {

    }
}
