package cn.piesat.sanitation.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.piesat.sanitation.R;
import cn.piesat.sanitation.data.MyAttendanceLBean;
import cn.piesat.sanitation.util.DateUtils;
import cn.piesat.sanitation.util.TimeUtils;

/**
 * Created by sen.luo on 2019/10/24.
 */

public class AttendanceAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<MyAttendanceLBean.DataBean> attendanceList;

    public AttendanceAdapter(Context context, ArrayList<MyAttendanceLBean.DataBean> attendanceList) {
        this.context = context;
        this.attendanceList = attendanceList;
    }

    @Override
    public int getCount() {
        return attendanceList.size();
    }

    @Override
    public Object getItem(int position) {
        return attendanceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AttendanceViewHolder holder;

        if (convertView==null){
            holder=new AttendanceViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_attendance_new,null);
            holder.tvGoWork=convertView.findViewById(R.id.tvGoWork);
            holder.tvGoTime=convertView.findViewById(R.id.tvGoTime);
            holder.tvOffWork=convertView.findViewById(R.id.tvOffWork);
            holder.tvOffTime=convertView.findViewById(R.id.tvOffTime);
            holder.tvYear=convertView.findViewById(R.id.tvYear);
            holder.tvMonthDay=convertView.findViewById(R.id.tvMonthDay);

            convertView.setTag(holder);
        }else {
            holder= (AttendanceViewHolder) convertView.getTag();
        }

        MyAttendanceLBean.DataBean dataBean=attendanceList.get(0);

        if (TextUtils.isEmpty(dataBean.startTime)){
            holder.tvGoTime.setText("--:--");
            holder.tvGoWork.setBackground(ContextCompat.getDrawable(context,R.drawable.orange_frame));
            holder.tvGoWork.setTextColor(ContextCompat.getColor(context,R.color.them_tip_orange));
        }else {
            holder.tvGoTime.setText(dataBean.startTime);
            holder.tvGoWork.setBackground(ContextCompat.getDrawable(context,R.drawable.gay_frame));
            holder.tvGoWork.setTextColor(ContextCompat.getColor(context,R.color.color_888888));
        }


        if (TextUtils.isEmpty(dataBean.endTime)){
            holder.tvOffTime.setText("--:--");
            holder.tvOffWork.setBackground(ContextCompat.getDrawable(context,R.drawable.orange_frame));
            holder.tvOffWork.setTextColor(ContextCompat.getColor(context,R.color.them_tip_orange));
        }else {
            holder.tvOffTime.setText(dataBean.endTime);
            holder.tvOffWork.setBackground(ContextCompat.getDrawable(context,R.drawable.gay_frame));
            holder.tvOffWork.setTextColor(ContextCompat.getColor(context,R.color.color_888888));
        }

        if (!TextUtils.isEmpty(dataBean.createDate)){

            try {
                String [] spiltDate=dataBean.createDate.split("-");
                holder.tvYear.setText(spiltDate[0]);
                holder.tvMonthDay.setText(spiltDate[1]+"/"+spiltDate[2]);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }else {
            holder.tvYear.setText("--");
            holder.tvMonthDay.setText("--/--");
        }



        return convertView;
    }

    class AttendanceViewHolder{
//        TextView tvDate,tvGoWorkTime,tvOffWorkTime;
        TextView tvGoWork,tvGoTime,tvOffWork,tvOffTime,tvYear,tvMonthDay;

    }
}
