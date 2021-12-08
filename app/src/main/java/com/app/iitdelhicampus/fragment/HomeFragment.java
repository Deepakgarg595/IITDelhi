package com.app.iitdelhicampus.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.utility.StringUtils;

public class HomeFragment extends BaseFragment {
    View itemView;
    WebView webView;
    private ProgressBar progressBar;

    private String userid;
    private String name;

    public String URL = Constants.WEB_URL+"application/index/my-bookings";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        itemView = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        return itemView;
    }


    private void init() {
        webView = (WebView) itemView.findViewById(R.id.webView);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        userid = StringUtils.getBase64Datanew(preferences.getUserId());


//        if (!StringUtils.isNullOrEmptyOrZero(name)) {
//            switch (name) {
//                case "Veterinarian":
//                    URL = "https://web.fullypet.com/application/index/veterinarian";
//                    break;
//
//                case "Trainers":
//                    URL = "https://web.fullypet.com/application/index/trainers";
//                    break;
//            }
//        }


        String url = URL + "/" + userid;
        webView.setWebViewClient(new myWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);

//        if (name != null) {
//            web.loadUrl(name/*"https://www.seearound.co/newPrivacyPolicy"*/);
//        } else {
        webView.loadUrl(url/*"https://www.seearound.co/newPrivacyPolicy"*/);
//        }
        webView.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
                return false;
            }
        });
//
    }
//
    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            progressBar.setVisibility(View.GONE);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            progressBar.setVisibility(View.GONE);
        }
    }


}
