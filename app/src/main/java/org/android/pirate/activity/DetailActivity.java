package org.android.pirate.activity;

import java.lang.reflect.Field;

import org.android.pirate.R;
import org.android.pirate.util.AppManager;
import org.android.pirate.util.CoutName;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends BaseActivity {
	private ImageView image = null;
	private TextView text;
	private ImageView backBtn;
	private String name = "";
	private int number = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		backBtn=(ImageView) findViewById(R.id.back_button);
		
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		image = (ImageView) findViewById(R.id.detail_image);

		image.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				AnimatorSet set=new AnimatorSet();
				ObjectAnimator alpha=ObjectAnimator.ofFloat(image,View.ALPHA,0f,1f);
				ObjectAnimator rotate=ObjectAnimator.ofFloat(image,View.ROTATION,0,360);
				set.playTogether(alpha,rotate);
				set.setDuration(800);
				set.setInterpolator(new DecelerateInterpolator());
				set.start();
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
//		Toast.makeText(getApplicationContext(), R.string.toast, Toast.LENGTH_SHORT)
//				.show();
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

	}

}
