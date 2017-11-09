package org.android.pirate.activity;


import java.io.IOException;

import org.android.pirate.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class GameActivity extends BaseActivity {

    private EditText edit = null;

    private ImageButton button_ok = null;

    private String name = "";

//    private boolean isExit = false;

//    private MediaPlayer player = null;

    private AdView mAdView;

    //	@SuppressLint("HandlerLeak")
//	private Handler handler=new Handler(){
//
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			if(msg.what==0){
//				isExit=false;
//			}
//		}
//
//	};
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
//        player = new MediaPlayer();
//        player = MediaPlayer.create(this, R.raw.music);
//        try {
//            player.prepare();
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        player.start();
        edit = (EditText) findViewById(R.id.edit);
        button_ok = (ImageButton) findViewById(R.id.button_ok);
//        button_search = (Button) findViewById(R.id.button_search);
//        button_tips = (Button) findViewById(R.id.button_tips);
        button_ok.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                searchName();
            }
        });
        edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                    searchName();
                }
                return true;
            }
        });

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        button_search.setOnClickListener(new Button.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(GameActivity.this, ImageActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        button_tips.setOnClickListener(new Button.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(GameActivity.this, DataActivity.class);
//                startActivity(intent);
//            }
//        });

        // 获取广告条
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void searchName(){
        name = edit.getText().toString().trim();
        if (name.equals("")) {
//                    Dialog dialog = new AlertDialog.Builder(GameActivity.this)
//                            .setTitle(R.string.dialog_title)
//                            .setMessage(R.string.dialog_message)
//                            .setPositiveButton(R.string.ok, null)
//                            .create();
//                    dialog.show();
            Toast.makeText(getApplicationContext(),R.string.dialog_message,Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(GameActivity.this, DetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("name", name);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        edit.setText("");
        edit.requestFocus();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    //   @Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if(keyCode==KeyEvent.KEYCODE_BACK){
//			if(!isExit){
//				isExit=true;
//				Toast.makeText(GameActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//				handler.sendEmptyMessageDelayed(0, 2000);
//			}else{
//				AppManager.getInstance().exit();
//			}
//		}
//		return false;
//	}


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        player.stop();
//        player.release();
//        if (player != null) {
//            player = null;
//        }
        if (mAdView != null) {
            mAdView.destroy();
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
