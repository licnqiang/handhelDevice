package cn.piesat.sanitation.ui.activity;

import android.widget.ListView;
import android.widget.TextView;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;
import cn.piesat.sanitation.common.BaseApplication;
import cn.piesat.sanitation.constant.SysContant;
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
    @BindView(R.id.springView)
    SpringView springView;

    private MinePresenter minePresenter;
    private UserListAdapter userListAdapter;
    private  ArrayList<UserListBean.RowsBean> userListBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_list;
    }

    @Override
    protected void initView() {
        findViewById(R.id.img_back).setOnClickListener(v -> finish());
        tv_title.setText("人员列表");
        minePresenter=new MinePresenter(this);

        userListBean=new ArrayList<>();
        userListAdapter=new UserListAdapter(this,userListBean);
        lvUser.setAdapter(userListAdapter);


        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNum=1;
                initData();
            }

            @Override
            public void onLoadmore() {
                pageNum++;
                initData();
            }
        });

    }

    private int pageNum=1;
    @Override
    protected void initData() {
        showLoadingDialog();
        Map<String,String> map =new HashMap<>();
        map.put("pageNum", String.valueOf(pageNum));
        map.put("pageSize", SysContant.CommentTag.pageSize);//-1为查询全部
        map.put("idSysdept", BaseApplication.getUserInfo().idSysdept);
        minePresenter.getUserList(map);
    }

    @Override
    public void Error(String msg) {
        if (springView!=null){
            springView.onFinishFreshAndLoad();
        }
        dismiss();
        ToastUtil.show(this,msg);
    }

    @Override
    public void SuccessOnUserList(UserListBean userList) {
        if (springView!=null){
            springView.onFinishFreshAndLoad();
        }
        dismiss();
        if (userList.rows.size()>0){
            if (pageNum==1){
                userListBean.clear();
            }
            userListBean.addAll(userList.rows);
            userListAdapter.notifyDataSetChanged();

        }else {
            ToastUtil.show(this,"空数据");
        }

    }
}
