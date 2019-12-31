package com.simplicitydev.bloodbank;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    Animation fade;

    ImageView logo,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        logo= (ImageView) findViewById(R.id.logo);
        title= (ImageView) findViewById(R.id.title);
        fade= AnimationUtils.loadAnimation(this,R.anim.fadein);

        logo.startAnimation(fade);
       title.startAnimation(fade);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashScreen.this, Walkthrough.class);
                startActivity(i);
                finish();
            }
        },3000);
    }
}
