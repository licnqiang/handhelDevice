package cn.piesat.sanitation.ui.fragment;

import android.widget.ListView;

import com.liaoinstan.springview.widget.SpringView;

import butterknife.BindView;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseFragment;

/**
 * 保险年检-未审核
 */
public class InsuranceReviewFragment extends BaseFragment {
    @BindView(R.id.lvInsurance)
    ListView lvInsurance;
    @BindView(R.id.springView)
    SpringView springView;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_two;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
