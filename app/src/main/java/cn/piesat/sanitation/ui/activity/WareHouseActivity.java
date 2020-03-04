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
import cn.piesat.sanitation.ui.adapter.WareHouseAdapter;

/**
 * 耗材入库
 */
public class WareHouseActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.springView)
    SpringView springView;
    @BindView(R.id.lvWareHouse)
    ListView lvWareHouse;
    @BindView(R.id.iv_right)
    ImageView iv_right;

    private WareHouseAdapter wareHouseAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_ware_house;
    }

    @Override
    protected void initView() {
        tv_title.setText("耗材入库");
        findViewById(R.id.img_back).setOnClickListener(v -> finish());
        iv_right.setImageDrawable(ContextCompat.getDrawable(this,R.mipmap.icon_add));
        iv_right.setVisibility(3!= BaseApplication.getUserInfo().userType? View.VISIBLE:View.GONE); //判断用户类型是否是集团人员  目前集团人员仅仅开放查看功能
        iv_right.setOnClickListener(v -> startActivityForResult(new Intent(this,AddWareHouseActivity.class).putExtra("isEdit",true),0));
        lvWareHouse.setOnItemClickListener((adapterView, view, i, l) ->
                startActivity(new Intent(this,AddWareHouseActivity.class)
                .putExtra("isEdit",false)));

        wareHouseAdapter =new WareHouseAdapter(this);
        lvWareHouse.setAdapter(wareHouseAdapter);

    }

    @Override
    protected void initData() {

    }
}
