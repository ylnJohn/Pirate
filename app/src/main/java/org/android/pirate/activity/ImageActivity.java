package org.android.pirate.activity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.android.pirate.R;
import org.android.pirate.util.AppManager;
import org.android.pirate.util.Util;
import org.android.pirate.view.HomeViewAdapter;
import org.android.pirate.view.MarqueeText;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ImageActivity extends BaseActivity {

//	private ViewPager viewPager;

    private ViewFlipper mViewFlipper;

    private MarqueeText titleText;

    private int length = 0;

//	private HomeViewAdapter adapter;

    private int currentItem = 0;

    private Random random = new Random();

    private ImageView mControlBtn;

    private AdView mAdView;

//	private ScheduledExecutorService scheduledExecutorService;

//	@SuppressLint("HandlerLeak")
//    private Handler handler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//            View v=adapter.getItem(currentItem);
//            Animation animation=null;
//            int index=random.nextInt(4);
//            switch(index){
//                case 0:
//                    animation=new AlphaAnimation(0.5f, 1.0f);
//                    break;
//                case 1:
//                    animation=new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//                    break;
//                case 2:
//                    animation=new TranslateAnimation(10, 100, 10, 100);
//                    break;
//                case 3:
//                    animation=new RotateAnimation(0.0f, +360.0f, Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//                    break;
//                default:
//                    break;
//            }
//            animation.setDuration(2000);
//            v.startAnimation(animation);
//            viewPager.setCurrentItem(currentItem);
//        }
//
//    };

    @SuppressLint("CutPasteId")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.image);

//	        viewPager=(ViewPager) findViewById(R.id.image);
        mViewFlipper = (ViewFlipper) findViewById(R.id.image_view);
        titleText = (MarqueeText) findViewById(R.id.title);
        String[] array=getResources().getStringArray(R.array.haizei);
        length = array.length;
        mControlBtn = (ImageView) findViewById(R.id.control_button);
        mControlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mViewFlipper.isFlipping()) {
                    mViewFlipper.startFlipping();
                    mControlBtn.setImageResource(R.drawable.icon_pause);
                } else {
                    mViewFlipper.stopFlipping();
                    mControlBtn.setImageResource(R.drawable.icon_start);
                }
            }
        });
//	        List<View> list=new ArrayList<View>();
        try {
            for (int i = 0; i < length; i++) {
                String name = array[i];
                ImageView image = new ImageView(this);
                Field field = R.drawable.class.getField(name);
                image.setImageResource(field.getInt(R.drawable.class));
                image.setScaleType(ImageView.ScaleType.CENTER_CROP);
//					list.add(image);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.haizei_image_width), getResources().getDimensionPixelOffset(R.dimen.haizei_image_height));
//                params.gravity= Gravity.CENTER_HORIZONTAL;
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                image.setLayoutParams(params);
                mViewFlipper.addView(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//	        adapter=new HomeViewAdapter(list);
//	        viewPager.setAdapter(adapter);
//
//	        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
//
//	            @Override
//	            public void onPageSelected(int position) {
//	                currentItem = position;
//	                viewPager.setCurrentItem(currentItem);
//	            }
//
//	            @Override
//	            public void onPageScrolled(int arg0, float arg1, int arg2) {
//	            }
//
//	            @Override
//	            public void onPageScrollStateChanged(int arg0) {
//	            }
//	        });
        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(3000);
        mViewFlipper.setInAnimation(this, R.anim.left_in);
        mViewFlipper.setOutAnimation(this, R.anim.left_out);
        if (!mViewFlipper.isFlipping()) {
            mViewFlipper.startFlipping();
        }
        titleText.setSpeed(1.0f);
        titleText.init();
        titleText.startScroll();

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 获取广告条
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    private void recycleBitmap() {
        if (mViewFlipper != null) {
            int count = mViewFlipper.getChildCount();
            for (int i = 0; i < count; i++) {
                View view = mViewFlipper.getChildAt(i);
                if (!(view instanceof ImageView))
                    return;
                ImageView img = (ImageView) view;
                if (img != null) {
                    Drawable drawable = img.getDrawable();
                    if (drawable != null) {
                        if (drawable instanceof BitmapDrawable) {
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                            Bitmap bitmap = bitmapDrawable.getBitmap();
                            if (bitmap != null)
                                bitmap.recycle();
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewFlipper.stopFlipping();
//        recycleBitmap();
        if (mAdView != null) {
            mAdView.destroy();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAdView != null) {
            mAdView.pause();
        }
    }

    //	 @Override
//	protected void onStop() {
//		scheduledExecutorService.shutdown();
//		super.onStop();
//	}
//
//	@Override
//	protected void onStart() {
//		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//        // 当Activity显示出来后，每两秒钟切换一次图片显示
//        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 3, 5,
//                TimeUnit.SECONDS);
//		super.onStart();
//	}

//	private class ScrollTask implements Runnable {
//
//        public void run() {
//            synchronized (viewPager) {
//                currentItem = (currentItem + 1) % length;
//                handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
//            }
//        }
//
//    }
}
