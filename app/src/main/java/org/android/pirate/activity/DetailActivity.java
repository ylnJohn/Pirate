package org.android.pirate.activity;

import java.lang.reflect.Field;

import org.android.pirate.R;
import org.android.pirate.util.AppManager;
import org.android.pirate.util.CoutName;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends BaseActivity {
	private ImageView image = null;
	private TextView text;
	private ImageButton backBtn;
	private String name = "";
	private int number = 0;
	private AnimationSet set = null;
	private RotateAnimation ratate = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		
		backBtn=(ImageButton) findViewById(R.id.back_btn);
		
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		image = (ImageView) findViewById(R.id.detail_image);

		image.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				set = new AnimationSet(true);
				ratate = new RotateAnimation(0, 360,
						Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0f);
				ratate.setDuration(1000);
				set.addAnimation(ratate);
				image.startAnimation(set);
			}
		});
		text = (TextView) findViewById(R.id.detail_text);

		Bundle bundle = this.getIntent().getExtras();
		name = bundle.getString("name");

		number = CoutName.getStrokeCount(name);
		if (number == 0) {
			char[] n = name.toCharArray();

			for (int i = 0; i < n.length; i++) {
				number += (int) n[i] - 30;

			}
		}
		Toast.makeText(DetailActivity.this, R.string.toast, Toast.LENGTH_SHORT)
				.show();
		String[] haizei=getResources().getStringArray(R.array.haizei);
		String index = haizei[number % (haizei.length)];
		try {
			Field field = R.drawable.class.getField(index);
			Field f = R.string.class.getField(index);

			image.setImageResource(field.getInt(R.drawable.class));
			// imageName=field.getName();
			// imageId=field.getInt(R.drawable.class);
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

		AppManager.getInstance().addActivity(this);
	}

}
