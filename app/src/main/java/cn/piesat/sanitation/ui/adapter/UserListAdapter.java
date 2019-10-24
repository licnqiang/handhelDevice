package cn.piesat.sanitation.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cn.piesat.sanitation.R;
import cn.piesat.sanitation.data.UserListBean;

/**
 * Created by sen.luo on 2019/10/24.
 */

public class UserListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<UserListBean.RowsBean> userListBean;


    public UserListAdapter(Context context, ArrayList<UserListBean.RowsBean>  userBean) {
        this.context = context;
        this.userListBean = userBean;
    }

    @Override
    public int getCount() {
        return userListBean.size();
    }

    @Override
    public Object getItem(int position) {
        return userListBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserListViewHolder holder=null;

        if (convertView==null){
            holder=new UserListViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_user_list,null);
            holder.tvCompany=convertView.findViewById(R.id.tvCompany);
            holder.tvName=convertView.findViewById(R.id.tvName);
            holder.tvUserType=convertView.findViewById(R.id.tvUserType);
            holder.tvJob=convertView.findViewById(R.id.tvJob);

            convertView.setTag(holder);
        }else {
            holder= (UserListViewHolder) convertView.getTag();
        }

        UserListBean.RowsBean userBean =userListBean.get(position);

        holder.tvName.setText(TextUtils.isEmpty(userBean.name)? "姓名:无":"姓名:"+userBean.name);
        holder.tvCompany.setText(TextUtils.isEmpty(userBean.deptNameCount)? "组织机构：无":"组织机构："+userBean.deptNameCount);
        holder.tvJob.setText(TextUtils.isEmpty(userBean.job)? "职位:无":"职位:"+userBean.job);


                int type = Integer.parseInt(userBean.userType);
        switch (type){
            case 1:
                holder.tvUserType.setText("人员类型：管理员");
                break;
            case 2:
                holder.tvUserType.setText("人员类型：普通用户");
                break;
            case 3:
                holder.tvUserType.setText("人员类型：环卫集团员工");
                break;
            case 4:
                holder.tvUserType.setText("人员类型：站长");
                break;
            case 5:
                holder.tvUserType.setText("人员类型：操作工");
                break;
            case 6:
                holder.tvUserType.setText("人员类型：扫保人员");
                break;
            case 7:
                holder.tvUserType.setText("人员类型：司机");
                break;
            default:
                holder.tvUserType.setText("人员类型：普通用户");
                break;
        }


        return convertView;
    }

    class UserListViewHolder{
        TextView tvName, tvUserType,tvJob,tvCompany;
    }
}
