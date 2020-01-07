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
import cn.piesat.sanitation.data.AccidentReportBean;
import cn.piesat.sanitation.data.ViolateReportBean;

public class AccidetReportAdapter extends BaseAdapter {

    private Context context;
    private List<AccidentReportBean.AccidentListBean> accidentListBeans;

    private ReportOperateMore reportOperateMore;

    public AccidetReportAdapter(Context context, List<AccidentReportBean.AccidentListBean> beans) {
        this.context = context;
        this.accidentListBeans = beans;
    }

    public void setReportOperateMore(ReportOperateMore operateMore){
        this.reportOperateMore=operateMore;
    }



    @Override
    public int getCount() {
        return accidentListBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return accidentListBeans.get(position);
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
//            holder.imgDelete=convertView.findViewById(R.id.imgDelete);
            convertView.setTag(holder);
        }else {
            holder= (ViolateViewHolder) convertView.getTag();
        }

        AccidentReportBean.AccidentListBean  bean =accidentListBeans.get(position);

        holder.tvPerson.setText(bean.accidentPeople==null?"空":bean.accidentPeople);
        holder.tvDate.setText(bean.accidentTime==null?"空":bean.accidentTime);
        holder.tvCarNumber.setText(bean.carNumber==null?"空":bean.carNumber);
        holder.tvStation.setText(bean.proportionalAmount==null?"责任划分：空":"责任划分："+bean.proportionalAmount);

       /* holder.imgDelete.setOnClickListener(v -> {

            reportOperateMore.getReportDelete(position,bean.id);
        });*/

        return convertView;
    }


    class ViolateViewHolder{

        TextView tvPerson,tvDate,tvCarNumber,tvStation;
//        ImageView imgDelete;
    }

   public interface ReportOperateMore{
        void getReportDelete(int position, String id);
    }
}
