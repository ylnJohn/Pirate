package org.android.pirate;

import android.app.Application;
import android.graphics.Typeface;

import java.lang.reflect.Field;

/**
 * Created by linnan.yao on 2017/10/16.
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //设置全局字体
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "Roboto-Regular.ttf");
        try {
            Field field = Typeface.class.getDeclaredField("SERIF");
            field.setAccessible(true);
            field.set(null, tf);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
//        SpotManager.getInstance(this).onAppExit();
    }
}
