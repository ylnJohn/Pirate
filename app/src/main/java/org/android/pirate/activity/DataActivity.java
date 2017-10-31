package org.android.pirate.activity;

import org.android.pirate.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class DataActivity extends ListActivity{
	private ListView listView=null;
	String data[]=null;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.data);
		listView=this.getListView();
		data=getResources().getStringArray(R.array.data);
		ArrayAdapter<?> adapter=new ArrayAdapter(this,R.layout.list_item,R.id.TextView01,data);
		listView.setAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent=new Intent(DataActivity.this,TextActivity.class);
		intent.putExtra("data", data[position]);
		startActivity(intent);
	}
	
	
}
