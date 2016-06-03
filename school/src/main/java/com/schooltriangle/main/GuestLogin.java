package com.schooltriangle.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.schooltriangle.R;
import com.schooltriangle.library.main.CirclePageIndicator;
import com.schooltriangle.library.main.refresh;
import com.schooltriangle.mylibrary.adapter.CustomViewPagerAdapter;
import com.schooltriangle.mylibrary.dialog.GlobalAlert;
import com.schooltriangle.mylibrary.dialog.ProgressDialogFragment;
import com.schooltriangle.mylibrary.pojo.MembershipAuthenticateResponse;
import com.schooltriangle.mylibrary.service.interfaces.MemberShipAuthenticate;
import com.schooltriangle.mylibrary.session.JeevomSession;
import com.schooltriangle.mylibrary.util.Connectivity;
import com.schooltriangle.mylibrary.util.JeevOMUtil;
import com.schooltriangle.mylibrary.util.MyUrlConnectionClient;

import java.net.SocketTimeoutException;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.mime.TypedByteArray;

/**
 * Created by chandan on 24/5/16.
 */




/**
 * Created by Chandan kumar on 26/11/15.
 */
public class GuestLogin extends AppCompatActivity implements CirclePageIndicator.RefreshCalledListener {
    Toolbar toolbar;
    private JeevomSession session;
    private WebView wv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_login);
        session = new JeevomSession(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar_view_profilee);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Explore as a Guest");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        wv1=(WebView)findViewById(R.id.webview_goole);
        wv1.setWebViewClient(new MyBrowser());

                wv1.getSettings().setLoadsImagesAutomatically(true);
                wv1.getSettings().setJavaScriptEnabled(true);
                wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                wv1.loadUrl(" https://forms.zohopublic.com/virtualoffice4996/form/ContactForm/formperma/BFD363C60f3659_j5GADEd66g");

         }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in,
                R.anim.trans_right_exit);
    }


    @Override
    public boolean onRefreshcalled() {
        return false;
    }

}


