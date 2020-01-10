package cn.piesat.sanitation.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.piesat.sanitation.R;
import cn.piesat.sanitation.data.AccidentReportBean;
import cn.piesat.sanitation.data.InsuranceBean;

public class InsuranceAdapter extends BaseAdapter {

    private Context context;

    private List<InsuranceBean.InsuranceListBean>insuranceListBeans;
    private ReportOperateMore reportOperateMore;


    public InsuranceAdapter(Context context, List<InsuranceBean.InsuranceListBean> insuranceListBeans) {
        this.context = context;
        this.insuranceListBeans = insuranceListBeans;
    }

    public void setReportOperateMore(ReportOperateMore operateMore){
        this.reportOperateMore=operateMore;
    }



    @Override
    public int getCount() {
        return insuranceListBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return insuranceListBeans.get(position);
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
            holder.tv_tag=convertView.findViewById(R.id.tv_tag);
//            holder.imgDelete=convertView.findViewById(R.id.imgDelete);
            convertView.setTag(holder);
        }else {
            holder= (ViolateViewHolder) convertView.getTag();
        }

        InsuranceBean.InsuranceListBean bean =insuranceListBeans.get(position);

        holder.tvPerson.setText(bean.applicant==null?"空":bean.applicant);
        holder.tvDate.setText(bean.appFlowInst.sendDate==null?"空":bean.appFlowInst.sendDate);
        holder.tvStation.setText(bean.coverage==null?"险种：空":"险种："+bean.coverage);

        if (bean.approvalstatus!=null){
            switch (bean.approvalstatus){

                //审核中
                case "01":
                    holder.tvCarNumber.setText("审核中");
                    holder.tvCarNumber.setTextColor(ContextCompat.getColor(context,R.color.color_2A83E9));
                    holder.tv_tag.setBackground(ContextCompat.getDrawable(context,R.mipmap.ing));
                    break;
                //驳回
                case "02":
                    holder.tvCarNumber.setText("驳回");
                    holder.tvCarNumber.setTextColor(ContextCompat.getColor(context,R.color.color_919191));
                    holder.tv_tag.setBackground(ContextCompat.getDrawable(context,R.mipmap.end));

                    break;
                //审核完成
                case "03":
                    holder.tvCarNumber.setText("审核完成");
                    holder.tvCarNumber.setTextColor(ContextCompat.getColor(context,R.color.color_919191));
                    holder.tv_tag.setBackground(ContextCompat.getDrawable(context,R.mipmap.end));
                    break;
            }
        }

       /* holder.imgDelete.setOnClickListener(v -> {

            reportOperateMore.getReportDelete(position,bean.id);
        });*/

        return convertView;
    }


    class ViolateViewHolder{

        TextView tvPerson,tvDate,tvCarNumber,tvStation,tv_tag;
//        ImageView imgDelete;
    }

   public interface ReportOperateMore{
        void getReportDelete(int position, String id);
    }
}
