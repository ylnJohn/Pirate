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
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.android.pirate.R;
import org.android.pirate.SystemConfig;

public class StartActivity extends BaseActivity implements View.OnClickListener {

    private InterstitialAd ad;
    private Button mGameBtn, mImageBtn, mDataBtn;
    private boolean isPause = false;

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
                Log.i("yaolinnan", "onAdLoaded");
                if (!isPause) {
                    ad.show();
                }
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                Log.i("yaolinnan", "onAdFailedToLoad:" + i);
            }
        });
        ad.loadAd(new AdRequest.Builder().build());
        mGameBtn = (Button) findViewById(R.id.start_game);
        mGameBtn.setOnClickListener(this);
        mImageBtn = (Button) findViewById(R.id.start_image);
        mImageBtn.setOnClickListener(this);
        mDataBtn = (Button) findViewById(R.id.start_data);
        mDataBtn.setOnClickListener(this);
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
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPause = false;
    }
}
