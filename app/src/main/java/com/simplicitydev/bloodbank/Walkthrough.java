package com.simplicitydev.bloodbank;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Walkthrough extends AppCompatActivity {

    ViewPager mSlideViewPager;
    LinearLayout mDotLayout;

    TextView[] mDots;

    Button next,back;

    int mCurrentPage;

    int slide_images[]={R.drawable.findbb,R.drawable.findh,R.drawable.callnlocate};
    String slide_headings[]={"Search Blood Banks","Search Hospitals","Call or navigate to any \nblood bank/hospital anywhere in India"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        com.simplicitydev.bloodbank.PrefManager prefManager = new com.simplicitydev.bloodbank.PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            Intent i=new Intent(Walkthrough.this, com.simplicitydev.bloodbank.Dashboard.class);
            startActivity(i);
            finish();
        }

        prefManager.setFirstTimeLaunch(false);

        setContentView(R.layout.activity_walkthrough);

        mSlideViewPager= (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout= (LinearLayout) findViewById(R.id.dotsLayout);

        next= (Button) findViewById(R.id.nextBtn);
        back= (Button) findViewById(R.id.prevBtn);

        com.simplicitydev.bloodbank.Slider_Adaptor adaptor=new com.simplicitydev.bloodbank.Slider_Adaptor(this,slide_images,slide_headings);
        mSlideViewPager.setAdapter(adaptor);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentPage==2){
                    Intent i=new Intent(Walkthrough.this, com.simplicitydev.bloodbank.Dashboard.class);
                    startActivity(i);
                    finish();
                }
                mSlideViewPager.setCurrentItem(mCurrentPage+1);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlideViewPager.setCurrentItem(mCurrentPage-1);
            }
        });
    }

    public void addDotsIndicator(int position){
        mDots=new TextView[3];
        mDotLayout.removeAllViews();

        for(int i=0;i<mDots.length;i++){
            mDots[i]=new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));

            mDotLayout.addView(mDots[i]);
        }
        if (mDots.length>0){
            mDots[position].setTextColor(Color.WHITE);
        }
    }

    ViewPager.OnPageChangeListener viewListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            mCurrentPage=position;

            if(position==0){
                back.setEnabled(false);
                next.setEnabled(true);
                back.setVisibility(View.INVISIBLE);
            }
            else if(position==mDots.length-1){
                back.setEnabled(true);
                next.setEnabled(true);
                back.setVisibility(View.VISIBLE);

                next.setText("Finish");
            }

            else{
                back.setEnabled(true);
                next.setEnabled(true);
                back.setVisibility(View.VISIBLE);

                next.setText("Next");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
