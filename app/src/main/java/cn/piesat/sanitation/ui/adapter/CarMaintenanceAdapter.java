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
import cn.piesat.sanitation.data.CarMaintenance;

/**
 * Created by sen.luo on 2019/10/24.
 */

public class CarMaintenanceAdapter extends RecyclerView.Adapter<CarMaintenanceAdapter.StationUserViewHolder> {
    private Context mContext;
    private List<CarMaintenance.RowsBean> mData;
    private LayoutInflater mInflater;
    private OnRecyclerViewItemClickListener listener;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public CarMaintenanceAdapter(Context mContext, List<CarMaintenance.RowsBean> data) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mData = data;
    }

    public void refreshData(List<CarMaintenance.RowsBean> data) {
        if (null != mData) {
            mData.clear();
        }
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public StationUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StationUserViewHolder(mInflater.inflate(R.layout.item_event, parent, false));
    }

    @Override
    public void onBindViewHolder(StationUserViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class StationUserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.car_num)
        TextView carNum;
        @BindView(R.id.tv_ispass)
        TextView tvIspass;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_user)
        TextView tvUser;
        private int clickPosition;

        public StationUserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(final int position) {
            clickPosition = position;
            CarMaintenance.RowsBean carInfo = mData.get(position);
             tvType.setText("1".equals(null!=carInfo.typeRepair?carInfo.typeRepair:"")?"维修":"保养");  //维保类型(1维修;2保养)
             carNum.setText(carInfo.licensePlate);
             tvIspass.setText(carInfo.name);
             tvTime.setText(carInfo.createtimeBizrepair);
             tvUser.setText("上报人："+null==carInfo.sbrRepair?"":carInfo.sbrRepair);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(v, clickPosition);
            }
        }
    }

}
