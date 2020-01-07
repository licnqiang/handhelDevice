package cn.piesat.sanitation.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import cn.piesat.sanitation.R;
import cn.piesat.sanitation.data.ViolateReportBean;

public class ViolateReportAdapter extends BaseAdapter {

    private Context context;
    private List<ViolateReportBean.ViolateListBean> violateListBeans;

    private ReportOperateMore reportOperateMore;

    public ViolateReportAdapter(Context context, List<ViolateReportBean.ViolateListBean> violateListBeans) {
        this.context = context;
        this.violateListBeans = violateListBeans;
    }

    public void setReportOperateMore(ReportOperateMore operateMore){
        this.reportOperateMore=operateMore;
    }



    @Override
    public int getCount() {
        return violateListBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return violateListBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViolateViewHolder holder=null;
        if (convertView==null){
            holder=new ViolateViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_violate_report,null);
            holder.tvCarNumber=convertView.findViewById(R.id.tvCarNumber);
            holder.tvPerson=convertView.findViewById(R.id.tvPerson);
            holder.tvDate=convertView.findViewById(R.id.tvDate);
            holder.tvStation=convertView.findViewById(R.id.tvStation);
            holder.imgDelete=convertView.findViewById(R.id.imgDelete);
            convertView.setTag(holder);
        }else {
            holder= (ViolateViewHolder) convertView.getTag();
        }

        ViolateReportBean.ViolateListBean bean =violateListBeans.get(position);

        holder.tvPerson.setText(bean.illegalPeople==null?"空":bean.illegalPeople);
        holder.tvDate.setText(bean.illegalTime==null?"空":bean.illegalTime);
        holder.tvCarNumber.setText(bean.carNumber==null?"空":bean.carNumber);
        holder.tvStation.setText(bean.illegalProject==null?"违章原因：空":"违章原因："+bean.illegalProject);

        holder.imgDelete.setOnClickListener(v -> {

            reportOperateMore.getReportDelete(position,bean.id);
        });

        return convertView;
    }


    class ViolateViewHolder{

        TextView tvPerson,tvDate,tvCarNumber,tvStation;
        ImageView imgDelete;
    }

   public interface ReportOperateMore{
        void getReportDelete(int position,String id);
    }
}
