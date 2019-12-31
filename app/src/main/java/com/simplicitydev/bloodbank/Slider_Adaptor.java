package com.simplicitydev.bloodbank;


import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Slider_Adaptor extends PagerAdapter{

    Activity activity;
    int slide_images[];
    String slide_headings[];

    public Slider_Adaptor(Activity activity,int slide_images[],String slide_headings[]){
        this.activity=activity;
        this.slide_images=slide_images;
        this.slide_headings=slide_headings;
    }


    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==(RelativeLayout)object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater obj=activity.getLayoutInflater();
        View view=obj.inflate(R.layout.slide_layout,container,false);

        ImageView slideImageView= (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading= (TextView) view.findViewById(R.id.slide_heading);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
