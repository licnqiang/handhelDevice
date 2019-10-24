package cn.piesat.sanitation.ui.fragment;



import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;

import butterknife.BindView;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseFragment;
import cn.piesat.sanitation.data.HomeNewsBean;
import cn.piesat.sanitation.model.contract.HomeContract;
import cn.piesat.sanitation.model.presenter.HomePresenter;
import cn.piesat.sanitation.ui.adapter.HomeMenuAdapter;
import cn.piesat.sanitation.util.LogUtil;
import cn.piesat.sanitation.util.ToastUtil;


/**
 * 首页新闻
 */
public class HomeFragment extends BaseFragment implements HomeContract.HomeNewsView{

    @BindView(R.id.springView)
    SpringView springView;
    @BindView(R.id.RecylerView)
    RecyclerView RecylerView;

    private HomeMenuAdapter adapter;
    private HomePresenter homePresenter;
    private ArrayList<HomeNewsBean.NewsList>homeBeanList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        setSwitch();
    }

    private void setSwitch() {
        homePresenter=new HomePresenter(this);
        homeBeanList=new ArrayList<>();
        adapter=new HomeMenuAdapter(getActivity(),homeBeanList);
        RecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecylerView.setAdapter(adapter);

        springView.setHeader(new DefaultHeader(getActivity()));
//        springView.setFooter(new DefaultFooter(getActivity()));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }

            @Override
            public void onLoadmore() {
//                new Handler().postDelayed(() -> springView.onFinishFreshAndLoad(), 1000);
            }
        });
    }

    @Override
    protected void initData() {
        showLoadingDialog();
        homePresenter.GetNewsList();


    }

    @Override
    public void Error(String msg) {
        dismiss();
        ToastUtil.show(getActivity(),msg);
        if (springView!=null){
            springView.onFinishFreshAndLoad();
        }
    }

    @Override
    public void SuccessOnNewsList(HomeNewsBean homeNewsBean) {
        dismiss();
        if (homeNewsBean.list!=null&&homeNewsBean.list.size()>0){
            homeBeanList.addAll(homeNewsBean.list);
            adapter.notifyDataSetChanged();


        }else {
            ToastUtil.show(getActivity(),"数据为空");
        }

        if (springView!=null){
            springView.onFinishFreshAndLoad();
        }
    }
}
