package cn.piesat.sanitation.ui.fragment;



import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import butterknife.BindView;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseFragment;
import cn.piesat.sanitation.ui.adapter.HomeMenuAdapter;


/**
 * 首页新闻
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.springView)
    SpringView springView;
    @BindView(R.id.RecylerView)
    RecyclerView RecylerView;
    HomeMenuAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        setSwitch();
    }

    private void setSwitch() {

        adapter=new HomeMenuAdapter(getActivity(),null);
        RecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecylerView.setAdapter(adapter);


        springView.setHeader(new DefaultHeader(getActivity()));
        springView.setFooter(new DefaultFooter(getActivity()));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(() -> springView.onFinishFreshAndLoad(), 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(() -> springView.onFinishFreshAndLoad(), 1000);
            }
        });
    }

    @Override
    protected void initData() {
    }

}
