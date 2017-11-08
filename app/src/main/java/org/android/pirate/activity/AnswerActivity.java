package org.android.pirate.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.umeng.analytics.MobclickAgent;

import org.android.pirate.R;
import org.android.pirate.SystemConfig;
import org.android.pirate.util.Util;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * Created by linnan.yao on 2017/11/8.
 */

public class AnswerActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTitleTV, mScoreTV, mContentTV;
    private RadioGroup mItemsGroup;
    private RadioButton mItemBtn1, mItemBtn2, mItemBtn3, mItemBtn4;
    private Button mSendBtn;
    private boolean isSelected[];// 防止产生一样的随机数
    private Random random = new Random();
    //    private MediaPlayer player = null;
    private int length = 0, choice = 0, score = 0;
    private String[] questions, tips;
    private Context mContext;
//    private boolean isPause=false;
    private AdView mAdView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        mContext = this;
        mTitleTV = (TextView) findViewById(R.id.question_title);
        mScoreTV = (TextView) findViewById(R.id.question_score);
        mContentTV = (TextView) findViewById(R.id.question_content);
        mItemsGroup = (RadioGroup) findViewById(R.id.question_items_group);
        mItemBtn1 = (RadioButton) findViewById(R.id.question_item1);
        mItemBtn2 = (RadioButton) findViewById(R.id.question_item2);
        mItemBtn3 = (RadioButton) findViewById(R.id.question_item3);
        mItemBtn4 = (RadioButton) findViewById(R.id.question_item4);
        mSendBtn = (Button) findViewById(R.id.send_button);
        mSendBtn.setOnClickListener(this);
        questions = getResources().getStringArray(R.array.question);
        length = questions.length;
        isSelected = new boolean[length];

        refresh();
//        score=sp.getInt(SystemConfig.SHARE_SCORE,100);
        mScoreTV.setText(score + "");
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

    private void refresh() {
        if (choice >= length) {
            showDialog(true, getResources().getString(R.string.answer_success));
            return;
        }
        mItemsGroup.clearCheck();
        int index = 0;
        do {
            index = random.nextInt(length);
        } while (isSelected[index]);
        // 获得目前没随机出现的题目
        isSelected[index] = true;
        String field = questions[index];
        try {
            // 反射机制
            Field f = R.string.class.getField(field);
            String question = getResources()
                    .getString(f.getInt(R.string.class)).trim();
            tips = question.split("-");
        } catch (Exception e) {
            Log.e("guess", e.getMessage());
        }
        choice++;
        if (tips != null && tips.length > 0) {
            mTitleTV.setText(tips[0]);
            mContentTV.setText(choice + "." + tips[1]);
            mItemBtn1.setText(tips[2]);
            mItemBtn2.setText(tips[3]);
            mItemBtn3.setText(tips[4]);
            mItemBtn4.setText(tips[5]);
        }
    }

    private void showDialog(boolean success, String msg) {
        final Dialog dialog = new Dialog(mContext, R.style.dialog_bottom_full);
        View view = LayoutInflater.from(mContext).inflate(R.layout.answer_dialog, null);
        view.setFitsSystemWindows(true);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        TextView message = (TextView) view.findViewById(R.id.answer_message);
//        RelativeLayout content= (RelativeLayout) view.findViewById(R.id.answer_content);
//        ImageView adward= (ImageView) view.findViewById(R.id.adward_image);
        ImageView image = (ImageView) view.findViewById(R.id.answer_icon);
        Button confirm = (Button) view.findViewById(R.id.confirm_button);
        int height = 220;
        if (success) {
            image.setImageResource(R.drawable.icon_success);
//            content.setVisibility(View.VISIBLE);
        } else {
            image.setImageResource(R.drawable.icon_failtrue);
//            content.setVisibility(View.GONE);
        }
        message.setText(msg);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        Window win = dialog.getWindow();
        win.setGravity(Gravity.CENTER);
        win.setWindowAnimations(android.R.style.Animation_Dialog);
        win.setLayout(Util.dip2px(mContext, 280), Util.dip2px(mContext, height));
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_button:
                int radioId = mItemsGroup.getCheckedRadioButtonId();
                View view = findViewById(radioId);
                if (view == null) {
                    Toast.makeText(getApplicationContext(), R.string.send_answer_error, Toast.LENGTH_SHORT).show();
                    break;
                }
                int index = mItemsGroup.indexOfChild(view);
                int answer = Integer.parseInt(tips[6]);
                if (index == answer) {// 答对
                    score += 10;
                    mScoreTV.setText(score + "");
                    refresh();
                } else {// 答错
                    RadioButton rb = (RadioButton) mItemsGroup.getChildAt(answer);
                    String s = rb.getText().toString().trim();
                    String str = String.format(getResources().getString(R.string.answer_error), s);
                    showDialog(false, str);
                }
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
}
