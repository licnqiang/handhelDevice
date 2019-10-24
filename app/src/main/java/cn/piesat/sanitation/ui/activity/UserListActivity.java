package cn.piesat.sanitation.ui.activity;

import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.data.UserListBean;
import cn.piesat.sanitation.model.contract.MineContract;
import cn.piesat.sanitation.model.presenter.MinePresenter;
import cn.piesat.sanitation.ui.adapter.UserListAdapter;
import cn.piesat.sanitation.util.ToastUtil;

/**
 * 人员列表
 * Created by sen.luo on 2019/10/24.
 */

public class UserListActivity extends BaseActivity implements MineContract.getUserListView{
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.lvUser)
    ListView lvUser;
    private MinePresenter minePresenter;
    private UserListAdapter userListAdapter;
    private  ArrayList<UserListBean.RowsBean> userListBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_list;
    }

    @Override
    protected void initView() {
        tv_title.setText("人员列表");
        minePresenter=new MinePresenter(this);

        userListBean=new ArrayList<>();
        userListAdapter=new UserListAdapter(this,userListBean);
        lvUser.setAdapter(userListAdapter);

    }

    @Override
    protected void initData() {
        showLoadingDialog();
        Map<String,String> map =new HashMap<>();
        map.put("pageNum","1");
        map.put("pageSize","20");//-1为查询全部
        map.put("idSysdept", BaseApplication.getUserInfo().idSysdept);
        minePresenter.getUserList(map);
    }

    @Override
    public void Error(String msg) {
        dismiss();
        ToastUtil.show(this,msg);
    }

    @Override
    public void SuccessOnUserList(UserListBean userList) {
        dismiss();
        userListBean.addAll(userList.rows);
        userListAdapter.notifyDataSetChanged();
    }
}
