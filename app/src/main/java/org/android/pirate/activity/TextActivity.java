package org.android.pirate.activity;

import java.lang.reflect.Field;

import org.android.pirate.R;
import org.android.pirate.util.AppManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TextActivity extends BaseActivity implements OnClickListener{
	private TextView text,backText;
	private LinearLayout layout;
	private EditText dialog_edit=null;
	private ImageButton backBtn;
	private Button shareBtn,sendBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.data_detail);
		text=(TextView)findViewById(R.id.text_detail);
		shareBtn=(Button) findViewById(R.id.share_btn);
		sendBtn=(Button) findViewById(R.id.send_btn);
		shareBtn.setOnClickListener(this);
		sendBtn.setOnClickListener(this);
		layout=new LinearLayout(this);
	    layout.setOrientation(LinearLayout.VERTICAL);	        
	    dialog_edit=new EditText(this);
	    dialog_edit.setId(0x123456);
	    layout.addView(dialog_edit);
		Bundle b=this.getIntent().getExtras();
		String string=b.getString("data");
		backText=(TextView) findViewById(R.id.back_text);
		backText.setText(string);
		String str=subString(string);		
		try {
			Field f = R.string.class.getField(str);
			text.setText(f.getInt(R.string.class));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		backBtn=(ImageButton) findViewById(R.id.back_btn);
		backBtn.setOnClickListener(this);
		AppManager.getInstance().addActivity(this);
	}
	
	
	
	private String subString(String s){
		String temp="";
		if(s.contains(".")){
			int index=s.indexOf(".");
			temp=s.substring(0, index);
		}
		temp="data"+temp;	
		return temp;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.share_btn:
				Intent intent=new Intent(Intent.ACTION_SEND);
				intent.setType("image/*");
				intent.putExtra(Intent.EXTRA_SUBJECT,  getResources().getString(R.string.app_name));
				intent.putExtra(Intent.EXTRA_TEXT, text.getText().toString()+getResources().getString(R.string.text_weibo));
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				this.startActivity(Intent.createChooser(intent, getResources().getString(R.string.share)));
				break;
			case R.id.send_btn:
				Dialog dialog=new AlertDialog.Builder(TextActivity.this)
				.setView(layout)
				.setTitle(R.string.dialog_title1)
				.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
				
					String n=dialog_edit.getText().toString();
					Uri uri=Uri.parse("smsto:"+n);
					Intent intent=new Intent(Intent.ACTION_SENDTO,uri);
					intent.putExtra("sms_body", getResources().getString(R.string.sms_to));
					startActivity(intent);
				}
					
				})
				.setNegativeButton(R.string.cansle,  new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.create();
				dialog.show();
				break;
			case R.id.back_btn:
				finish();
				break;
		}
	}

}
