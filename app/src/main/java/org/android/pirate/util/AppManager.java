package org.android.pirate.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class AppManager extends Application{
	
	private static AppManager instance=null;
	
	private List<Activity> list=new ArrayList<Activity>();

	public static AppManager getInstance(){
		if(instance==null)
			instance=new AppManager();
		return instance;
	}
	
	public void addActivity(Activity activity){
		list.add(activity);
	}
	
	public void exit(){
		for(Activity a:list){
			if(a==null)
				continue;
			else
				a.finish();
		}
		System.exit(0);
	}
}
