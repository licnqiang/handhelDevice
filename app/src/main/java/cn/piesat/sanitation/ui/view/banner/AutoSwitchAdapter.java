package cn.piesat.sanitation.ui.view.banner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseApplication;

/**
 * @author ryze
 * @since 1.0  2016/07/17
 */
public class AutoSwitchAdapter extends AutoLoopSwitchBaseAdapter {

  private Context mContext;

  private List<String> mDatas;

  public AutoSwitchAdapter() {
    super();
  }

  public AutoSwitchAdapter(Context mContext, List<String> mDatas) {
    this.mContext = mContext;
    this.mDatas = mDatas;
  }

  @Override
  public int getDataCount() {
    return mDatas == null ? 0 : mDatas.size();
  }

  @Override
  public View getView(int position) {
    ImageView imageView = new ImageView(BaseApplication.ApplicationContext);
    imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    Glide.with(mContext)
            .load(mDatas.get(position))
            .into(imageView);

    return imageView;
  }

  @Override
  public Object getItem(int position) {
    if (position >= 0 && position < getDataCount()) {

      return mDatas.get(position);
    }
    return null;
  }

  @Override
  public View getEmptyView() {
    return null;
  }

  @Override
  public void updateView(View view, int position) {
  }
  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    super.destroyItem(container, position, object);
  }
}
