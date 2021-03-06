package org.android.pirate.view;

import android.view.ViewGroup;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * @Description: 首页viewpager适配器
 * @author yaolinnan
 * @date 2014-6-19 下午2:59:59
 * @version V1.0
 */
public class HomeViewAdapter extends PagerAdapter{
    
    private List<View> list=null;
    
    public HomeViewAdapter(List<View> list){
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0==arg1;
    }
    
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position),0);
        return list.get(position);
    }
    
    public View getItem(int position){
        return list.get(position);
    }

}
