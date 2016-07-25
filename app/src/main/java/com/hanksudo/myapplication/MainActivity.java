package com.hanksudo.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private VideoEnabledWebView webView;
    private VideoEnabledWebChromeClient webChromeClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        View nonVideoLayout = findViewById(R.id.nonVideoLayout); // Your own view, read class comments
        ViewGroup videoLayout = (ViewGroup)findViewById(R.id.videoLayout); // Your own view, read class comments
        View loadingView = getLayoutInflater().inflate(R.layout.view_loading_video, null); // Your own view, read class comments

        webView = (VideoEnabledWebView)findViewById(R.id.webView);
        webChromeClient = new VideoEnabledWebChromeClient(nonVideoLayout, videoLayout, loadingView, webView) // See all available constructors...
        {
            // Subscribe to standard events, such as onProgressChanged()...
            @Override
            public void onProgressChanged(WebView view, int progress)
            {
//                View decorView = getWindow().getDecorView();
//                decorView.setSystemUiVisibility(
//                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//                                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
//                                | View.SYSTEM_UI_FLAG_IMMERSIVE);

            }
        };
        webChromeClient.setOnToggledFullscreen(new VideoEnabledWebChromeClient.ToggledFullscreenCallback()
        {
            @Override
            public void toggledFullscreen(boolean fullscreen)
            {
                // Your code to handle the full-screen change, for example showing and hiding the title bar. Example:
                if (fullscreen)
                {
                    WindowManager.LayoutParams attrs = getWindow().getAttributes();
                    attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getWindow().setAttributes(attrs);
                    if (android.os.Build.VERSION.SDK_INT >= 14)
                    {
                        //noinspection all
                        getWindow().getDecorView().setSystemUiVisibility(
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
                    }
                }
                else
                {
                    WindowManager.LayoutParams attrs = getWindow().getAttributes();
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getWindow().setAttributes(attrs);
                    if (android.os.Build.VERSION.SDK_INT >= 14)
                    {
                        //noinspection all
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
//
                    }
                }

            }
        });
        webView.setWebChromeClient(webChromeClient);
        // Call private class InsideWebViewClient
        webView.setWebViewClient(new InsideWebViewClient());

        // Navigate anywhere you want, but consider that this classes have only been tested on YouTube's mobile site
//        webView.loadUrl("http://m.youtube.com");

        String html = "<html><head><meta charset=\"utf-8\"><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"><style>body{padding:15px;font-size:200%}img{width:100%}.videoWrapper{position:relative;padding-bottom:56.25%;padding-top:25px;height:0}.videoWrapper iframe{position:absolute;top:0;left:0;width:100%;height:100%}@media screen and (-webkit-min-device-pixel-ratio: 0){body{word-break:break-word}}</style></head><body><p></p><p></p><p></p><p></p><p></p><p></p><p><div class=\"videoWrapper\"><iframe src=\"https://www.youtube.com/embed/h_UQ6o0Ti0w\" allowfullscreen frameborder=\"0\"></iframe></div></p><p><br></p><h2>Title 1 &nbsp;</h2><p>美麗的生命在於能勇於更新，且願意努力學習。像螃蟹脫殼是為了讓自己長得更健壯；而毛<br>毛蟲蛻變為蝴蝶，或是蝌蚪蛻變為青蛙，才使生命煥然一新。e人的一生，也需要蛻變，才能成長。<br>每一次蛻變都使生命走進人生的新領域、新境界，使我們獲得新的接觸、 新的感受、新的驚喜。<br>周大觀小朋友從小是父母的心肝寶貝，生活過得快樂充實，七歲就會寫詩，到了九歲得到癌<br>症，他開始跟病魔戰鬥，接受截肢手術，雖然少了一隻腿，他仍然努力活下去，並且寫好幾首詩，<br>表達自己不向困境低頭的心意。<br>後來醫師幫助他做化學治療，使得他全身有刺骨之痛，頭髮掉光、身體軟弱無力，可是大觀<br>仍然勇敢地參加為自己而開的醫療會議，聆聽一生命運無望的判決，還表示能夠接受這樣的結論，<br>並向多位醫生叔叔、伯伯道謝，感謝這些日子來辛勞的照顧。<br>這位生命的勇者在十歲時離開人世。可是他並沒有離開我們，因為他留下了另一種生命，就<br>是他的精神和他的詩： ...<span id=\"selectionBoundary_1468824201030_4881703862896598\" class=\"rangySelectionBoundary\">\uFEFF</span><span id=\"selectionBoundary_1468824200207_32529137540397257\" class=\"rangySelectionBoundary\">\uFEFF</span><br></p></body></html>\n";
        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
    }

    private class InsideWebViewClient extends WebViewClient {
        @Override
        // Force links to be opened inside WebView and not in Default Browser
        // Thanks http://stackoverflow.com/a/33681975/1815624
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onBackPressed()
    {
        // Notify the VideoEnabledWebChromeClient, and handle it ourselves if it doesn't handle it
        if (!webChromeClient.onBackPressed())
        {
            if (webView.canGoBack())
            {
                webView.goBack();
            }
            else
            {
                // Standard back button implementation (for example this could close the app)
                super.onBackPressed();
            }
        }
    }
}
