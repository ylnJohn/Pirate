package org.android.pirate.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.android.pirate.R;
import org.android.pirate.SystemConfig;

public class StartActivity extends BaseActivity implements View.OnClickListener {

    private InterstitialAd ad;
    private Button mGameBtn, mImageBtn, mDataBtn, mAnswerBtn;
    private boolean isPause = false;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        MobileAds.initialize(getApplicationContext(), SystemConfig.AD_ID);
        ad = new InterstitialAd(getApplicationContext());
        ad.setAdUnitId(SystemConfig.START_AD_ID);
        ad.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!isPause) {
                            ad.show();
                        }
                    }
                }, 2000);
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }
        });
        ad.loadAd(new AdRequest.Builder().build());
        mGameBtn = (Button) findViewById(R.id.start_game);
        mGameBtn.setOnClickListener(this);
        mImageBtn = (Button) findViewById(R.id.start_image);
        mImageBtn.setOnClickListener(this);
        mDataBtn = (Button) findViewById(R.id.start_data);
        mDataBtn.setOnClickListener(this);
        mAnswerBtn = (Button) findViewById(R.id.start_answer);
        mAnswerBtn.setOnClickListener(this);
        // 获取广告条
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_game:
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);
                break;
            case R.id.start_image:
                Intent i = new Intent(this, ImageActivity.class);
                startActivity(i);
                break;
            case R.id.start_data:
                Intent in = new Intent(this, DataActivity.class);
                startActivity(in);
                break;
            case R.id.start_answer:
                Intent inte = new Intent(this, AnswerActivity.class);
                startActivity(inte);
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
        if (mAdView != null) {
            mAdView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPause = false;
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        BannerManager.getInstance(getApplicationContext()).onDestroy();
        if (mAdView != null) {
            mAdView.destroy();
        }
    }

}
