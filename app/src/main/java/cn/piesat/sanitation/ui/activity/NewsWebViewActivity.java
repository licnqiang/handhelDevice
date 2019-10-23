package cn.piesat.sanitation.ui.activity;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import butterknife.BindView;
import cn.piesat.sanitation.R;
import cn.piesat.sanitation.common.BaseActivity;

/**
 * 新闻WebView
 * Created by sen.luo on 2019/10/23.
 */

public class NewsWebViewActivity extends BaseActivity{

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.tv_title)
    TextView tv_title;

    private String url="";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_webview;
    }

    @Override
    protected void initView() {
        findViewById(R.id.img_back).setOnClickListener(view -> finish());
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);


        if (getIntent().getStringExtra("url")!=null){
            url =getIntent().getStringExtra("url");
        }
        if (getIntent().getStringExtra("title")!=null){
            tv_title.setText(getIntent().getStringExtra("title"));
        }
    }

    @Override
    protected void initData() {
        webView.loadUrl(url);
    }
}
