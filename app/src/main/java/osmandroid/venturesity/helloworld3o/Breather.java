package osmandroid.venturesity.helloworld3o;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Breather extends AppCompatActivity {


    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breather);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)actionBar.setTitle("Meditation");

        webView = findViewById(R.id.web_view);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.calm.com/breathe");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }
}
