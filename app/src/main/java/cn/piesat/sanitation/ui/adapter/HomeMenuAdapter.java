package cn.piesat.sanitation.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.data.HomeNewsBean;
import cn.piesat.sanitation.ui.activity.NewsWebViewActivity;
import cn.piesat.sanitation.ui.view.banner.AutoSwitchAdapter;
import cn.piesat.sanitation.ui.view.banner.AutoSwitchView;


/**
 *
 */

public class HomeMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<HomeNewsBean.NewsList> homeNewsList;

    public HomeMenuAdapter(Context context, ArrayList<HomeNewsBean.NewsList> homeNewsList) {
        this.context = context;
        this.homeNewsList = homeNewsList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View bannerView = LayoutInflater.from(context).inflate(R.layout.item_banner, parent,false);
        View noticeView = LayoutInflater.from(context).inflate(R.layout.item_notice, parent,false);
        View newsView = LayoutInflater.from(context).inflate(R.layout.item_news, parent,false);

        if (viewType == TYPE_BANNER) return new BannerHolder(bannerView);
        if (viewType == TYPE_NOTICE) return new NoticeHolder(noticeView);
        return new NewsHolder(newsView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case TYPE_BANNER:
                bindBannerHolder((BannerHolder) holder, position);
                break;
            case TYPE_NOTICE:
                bindNoticeHolder((NoticeHolder) holder, position);
                break;
            case TYPE_NEWS:
                bindNewsHolder((NewsHolder) holder, position);
                break;
        }

    }

    private void bindBannerHolder(BannerHolder holder, int position) {
        ArrayList<String> im = new ArrayList<String>();
        im.add("http://www.xixianxinqu.gov.cn/images/zt/yjhj/banner1440.png");
        im.add("http://www.xixianxinqu.gov.cn/images/zt/bwcx.files/banner.png");
        im.add("http://www.xixianxinqu.gov.cn/images/zt/zl70zn/banner.png");
        AutoSwitchAdapter mAdapter = new AutoSwitchAdapter(context, im);
        holder.loopswitch.setAdapter(mAdapter);
    }

    private void bindNoticeHolder(NoticeHolder holder, int position) {
        holder.tvTwoTitle.setText("咸阳垃圾一站获得领导表扬");
        holder.tvTwoType.setText("公告");


    }

    private void bindNewsHolder(NewsHolder holder, int position) {
        HomeNewsBean.NewsList homeBean=homeNewsList.get(position-2);
        RequestOptions requestOptions =RequestOptions.bitmapTransform(new RoundedCorners(10));

        Glide.with(context).load(homeBean.cover).apply(requestOptions).into(holder.imgPic);
        holder.tvContent.setText(homeBean.title);
        holder.tvDate.setText(homeBean.createTime);
        holder.tvReadCount.setText("已读 "+homeBean.readCount);

        holder.itemView.setOnClickListener(view -> {
            context.startActivity(new Intent(context, NewsWebViewActivity.class)
                    .putExtra("url",homeBean.context)
                    .putExtra("title",homeBean.title));
        });

    }

    @Override
    public int getItemCount() {
        return homeNewsList.size()+2;
    }


    public static final int TYPE_BANNER = 0;
    public static final int TYPE_NOTICE = 1;
    public static final int TYPE_NEWS = 2;

    @Override
    public int getItemViewType(int position) {
        if (position == TYPE_BANNER) return TYPE_BANNER;
        if (position == TYPE_NOTICE) return TYPE_NOTICE;
        return TYPE_NEWS;
    }

    class BannerHolder extends RecyclerView.ViewHolder {
        AutoSwitchView loopswitch;
        public BannerHolder(View itemView) {
            super(itemView);
            loopswitch=itemView.findViewById(R.id.loopswitch);
        }
    }

    class NoticeHolder extends RecyclerView.ViewHolder {

        TextView tvTwoType,tvTwoTitle;
        public NoticeHolder(View itemView) {
            super(itemView);
            tvTwoTitle=itemView.findViewById(R.id.tvTwoTitle);
            tvTwoType=itemView.findViewById(R.id.tvTwoType);

        }
    }

    class NewsHolder extends RecyclerView.ViewHolder {
        TextView tvReadCount,tvDate,tvContent;
        ImageView imgPic;
        public NewsHolder(View itemView) {
            super(itemView);

             tvReadCount =itemView.findViewById(R.id.tvReadCount);
             tvDate =itemView.findViewById(R.id.tvDate);
             tvContent =itemView.findViewById(R.id.tvContent);
            imgPic=itemView.findViewById(R.id.imgPic);
        }
    }
    private RoundedBitmapDrawable rectRoundBitmap(Bitmap bitmap){
        //创建RoundedBitmapDrawable对象
        RoundedBitmapDrawable roundImg = RoundedBitmapDrawableFactory.create(context.getResources(), bitmap);
        //抗锯齿
        roundImg.setAntiAlias(true);
        //设置圆角半径
        roundImg.setCornerRadius(15);
        return roundImg;
    }

}
