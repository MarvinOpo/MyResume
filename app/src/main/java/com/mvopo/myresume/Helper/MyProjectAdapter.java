package com.mvopo.myresume.Helper;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mvopo.myresume.R;

import java.util.ArrayList;

/**
 * Created by mvopo on 7/18/2018.
 */

public class MyProjectAdapter extends PagerAdapter {

    private int[] images;
    private LayoutInflater inflater;
    private Context context;

    public MyProjectAdapter(Context context, int[] images) {
        this.context = context;
        this.images=images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {

        View myImageLayout = inflater.inflate(R.layout.image_container_layout, view, false);
        ImageView myImage = myImageLayout.findViewById(R.id.viewpager_image);

        myImage.setImageResource(images[position]);
        view.addView(myImageLayout, 0);

        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}