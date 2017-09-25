package com.hyundai.ioniqrun.activity;

import com.hyundai.ioniqrun.BuildConfig;
import com.hyundai.ioniqrun.R;
import com.hyundai.ioniqrun.application.IoniqApplication;
import com.hyundai.ioniqrun.base.BaseActivity;
import com.hyundai.ioniqrun.extend.HybridInterface;
import com.hyundai.ioniqrun.extend.WebChromeClientEx;
import com.hyundai.ioniqrun.extend.WebViewClientEx;

import android.os.Build;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import javax.inject.Inject;
import javax.inject.Named;

public class TermsActivity2 extends BaseActivity {

  @Inject
  @Named("web_terms_url")
  String url;

  @BindView(R.id.web_view)
  WebView webView;

  @BindView(R.id.txt_title)
  TextView txtTitle;

  @BindView(R.id.progressBar)
  ProgressBar progressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_terms);
    IoniqApplication.getInstance()
        .setScreenTracking(BuildConfig.APPLICATION_ID + "_" + "TermsActivity");

    IoniqApplication.getInstance().getComponent().inject(this);
    ButterKnife.bind(this);

    txtTitle.setText("이용약관");

    WebSettings set = webView.getSettings();
    set.setJavaScriptEnabled(true); // javascript를 실행할 수 있도록 설정

    set.setPluginState(WebSettings.PluginState.ON); // 플러그인을 사용할 수 있도록 설정
    set.setCacheMode(WebSettings.LOAD_NO_CACHE); // 웹뷰가 캐시를 사용하지 않도록 설정
    set.setDomStorageEnabled(true);
    set.setDatabaseEnabled(true);

    webView.setWebViewClient(new WebViewClientEx(this, progressBar));
    webView.setWebChromeClient(new WebChromeClientEx(this));
    webView.addJavascriptInterface(new HybridInterface(this, webView), "ioniqNative");

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      webView.setWebContentsDebuggingEnabled(true);
    }

    webView.loadUrl(url);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  @OnClick(R.id.btn_close)
  void onClickClose() {
    finish();
  }
}