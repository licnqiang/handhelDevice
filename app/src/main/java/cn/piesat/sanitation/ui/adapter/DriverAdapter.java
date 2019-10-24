package cn.piesat.sanitation.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.data.DriverInfo;
import cn.piesat.sanitation.data.DriverInfo;


public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.SelectedPicViewHolder> {
    private Context mContext;
    private List<DriverInfo.RowsBean> mData;
    private LayoutInflater mInflater;
    private OnRecyclerViewItemClickListener listener;

    public interface OnRecyclerViewItemClickListener {
        void onDriverItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public DriverAdapter(Context mContext, List<DriverInfo.RowsBean> data) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mData = data;
    }

    public void refreshData(List<DriverInfo.RowsBean> data) {
        if (null != mData) {
            mData.clear();
        }
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public SelectedPicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectedPicViewHolder(mInflater.inflate(R.layout.item_car, parent, false));
    }

    @Override
    public void onBindViewHolder(SelectedPicViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class SelectedPicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.car_num)
        TextView carNum;
        @BindView(R.id.car_state)
        TextView carState;
        @BindView(R.id.tv_station)
        TextView tvStation;
        private int clickPosition;

        public SelectedPicViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final int position) {
            clickPosition = position;
            //设置条目的点击事件
            itemView.setOnClickListener(this);
            DriverInfo.RowsBean rowsBean = mData.get(position);

            carNum.setText(rowsBean.name);     //自己名
            carState.setText(rowsBean.phone); //电话
            tvStation.setText(rowsBean.deptNameCount); //站点
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onDriverItemClick(v, clickPosition);
            }
        }
    }
}