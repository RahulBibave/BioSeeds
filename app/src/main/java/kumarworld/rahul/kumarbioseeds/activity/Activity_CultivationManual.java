package kumarworld.rahul.kumarbioseeds.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import kumarworld.rahul.kumarbioseeds.R;

public class Activity_CultivationManual extends AppCompatActivity {

    private WebView webview;
    private ProgressBar progressbar;
    private ImageView imgBack;
    private String manual;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cultivation);

        manual=getIntent().getStringExtra("manual");
        imgBack=findViewById(R.id.img_arrowback);
//WebView
        webview = (WebView)findViewById(R.id.webview);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        webview.getSettings().setJavaScriptEnabled(true);
       // String filename ="https://www.wifss.ucdavis.edu/wp-content/uploads/2016/10/Strawberries_PDF.pdf";
        //String filename ="http://mahaprisons.gov.in/Uploads/Dockets_Files/635259935664912504Banana_Cultivation_Guide_%C2%AB_Banana_Planters.pdf";
        webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + manual);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().supportZoom();

        webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                progressbar.setVisibility(View.GONE);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
