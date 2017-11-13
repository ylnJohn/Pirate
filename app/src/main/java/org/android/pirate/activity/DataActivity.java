package org.android.pirate.activity;

import org.android.pirate.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class DataActivity extends BaseActivity{
	private ListView listView=null;
	String data[]=null;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data);
		listView= (ListView) findViewById(R.id.data_list);
		data=getResources().getStringArray(R.array.data);
		ArrayAdapter<?> adapter=new ArrayAdapter(this,R.layout.list_item,R.id.data_title,data);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent=new Intent(DataActivity.this,TextActivity.class);
				intent.putExtra("data", data[position]);
				startActivity(intent);
			}
		});
		findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}


}
