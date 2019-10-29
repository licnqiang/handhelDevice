package cn.piesat.sanitation.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.data.CarInfo;
import cn.piesat.sanitation.data.CompressStations;


public class CarAdapter extends RecyclerView.Adapter<CarAdapter.SelectedPicViewHolder> {
    private Context mContext;
    private List<CarInfo.RowsBean> mData;
    private LayoutInflater mInflater;
    private OnRecyclerViewItemClickListener listener;

    public interface OnRecyclerViewItemClickListener {
        void onCarItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public CarAdapter(Context mContext, List<CarInfo.RowsBean> data) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mData = data;
    }

    public void refreshData(List<CarInfo.RowsBean> data) {
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
            CarInfo.RowsBean rowsBean = mData.get(position);

            carNum.setText(rowsBean.licensePlate);     //地址
            if ("0".equals(carState)) {
                carState.setText("空闲"); //站名
            }
            tvStation.setText(rowsBean.deptNameCount); //站名
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onCarItemClick(v, clickPosition);
            }
        }
    }
}