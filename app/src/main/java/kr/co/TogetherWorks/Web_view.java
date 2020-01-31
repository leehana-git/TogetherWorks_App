package kr.co.TogetherWorks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Web_view extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent(); /*데이터 수신*/
        String URL = intent.getExtras().getString("URL"); /*String형*/

        WebView webView = (WebView)findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient());  //액티비티 내부에서 웹브라우저가 띄워지도록 설정

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);  //자바스크립트가 사용가능 하도록 설정

        webView.loadUrl(URL);
    }
}
