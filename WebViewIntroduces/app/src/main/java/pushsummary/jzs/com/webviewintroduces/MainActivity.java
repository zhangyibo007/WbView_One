package pushsummary.jzs.com.webviewintroduces;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WebView webview;
    private WebSettings mWebSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initData() {
        webview.getSettings().setDefaultTextEncodingName("UTF-8");//设置默认为utf-8
        mWebSetting = webview.getSettings();
        mWebSetting.setJavaScriptEnabled(true);
        webview.loadUrl("file:///android_asset/html/pay.html");


        webview.addJavascriptInterface(new NativeIn(), "test");

        webview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                //调用js方法
                webview.loadUrl("javascript:settitle(8)");


            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                //默认调用父类的方法，不做任何处理
                // handler.proceed();//信任所有网站的证书
                /// handler.handleMessage(Message.obtain()); //自定义处理

            }
        });

        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
                //网页图标
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                //网页标题
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return super.onConsoleMessage(consoleMessage);
                //浏览器控制台打印信息
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                //加载进度条
            }
        });


    }

    class NativeIn {

        @JavascriptInterface
        public void GETNAME() {
            Toast.makeText(MainActivity.this,"调用支付功能",Toast.LENGTH_SHORT).show();
        }

    }

    private void initView() {
        webview = (WebView) findViewById(R.id.webview);
    }
}
