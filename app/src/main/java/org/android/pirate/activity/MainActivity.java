package org.android.pirate.activity;



import org.android.pirate.R;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        new Thread(){
        	public void run(){
        		try {
					Thread.sleep(3000);
					Intent intent=new Intent(MainActivity.this,GameActivity.class);
					startActivity(intent);
					MainActivity.this.finish();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	}
        }.start();
    }
}