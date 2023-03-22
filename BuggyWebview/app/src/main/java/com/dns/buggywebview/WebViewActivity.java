package com.dns.buggywebview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {
    Uri uri;
    Intent theIntent;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView = findViewById(R.id.webview);
        initializeWebView(webView);
        theIntent = getIntent();
        if (((uri = theIntent.getData())!= null) && (theIntent!=null) && (Intent.ACTION_VIEW.equals(theIntent.getAction()))) {
            parseCustomDeeplink(uri);
        }else if (((uri = theIntent.getData())!= null) && (theIntent!=null) && ("com.dns.buggywebview.action.WEBVIEW".equals(theIntent.getAction()))) {
            parseHTTPSDeeplink(uri);
        }
        String urlFromIntent = getIntent().getStringExtra("url");
        webView.loadUrl(urlFromIntent);

    }

    private void parseHTTPSDeeplink(Uri uri) {
        String urlPath = uri.getQueryParameter("url");
        Intent i = new Intent(this, WebViewActivity.class);
        i.putExtra("url", urlPath);
        startActivity(i);
    }

    private void parseCustomDeeplink(Uri uri) {
        String urlPath = "";
        if(("buggylink".equals(uri.getScheme())) && ("com.dns.buggywebview".equals(uri.getHost())))
        {
            String uriPath = uri.getPath();
            if("/webview".equals(uriPath)) {
                urlPath = uri.getQueryParameter("url");
                if(urlPath!= null) {
                        Intent i = new Intent(this, WebViewActivity.class);
                        i.putExtra("url", urlPath);
                        startActivity(i);
                }
            }
            else if("/validwebview".equals(uriPath)) {
                urlPath = uri.getQueryParameter("url");
                if((urlPath!=null) && (urlPath.endsWith("8ksec.io"))) {
                        Intent i = new Intent(this, WebViewActivity.class);
                        i.putExtra("url", urlPath);
                        startActivity(i);
                    }
                }
        }
    }

    private void initializeWebView(WebView webView) {
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
    }
}