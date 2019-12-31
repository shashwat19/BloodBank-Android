package com.simplicitydev.bloodbank;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;



public class Dashboard extends AppCompatActivity {

    CardView finddonor,findhosp,feedback_form,about;

   // Button logout;

    Button register;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_dashboard);


        pref=getSharedPreferences("login", Context.MODE_PRIVATE);
        editor=pref.edit();
        //logout= (Button) findViewById(R.id.logout);

        finddonor= (CardView) findViewById(R.id.finddonar);
        findhosp= (CardView) findViewById(R.id.findhosp);
        feedback_form= (CardView) findViewById(R.id.feedback);
        about= (CardView) findViewById(R.id.about);

        register= (Button) findViewById(R.id.register);


        findhosp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Dashboard.this, com.simplicitydev.bloodbank.FindHospital.class);
                startActivity(i);
            }
        });

        finddonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d=new Dialog(Dashboard.this);
                d.setContentView(R.layout.blood_bank_action2);


                LinearLayout search= (LinearLayout) d.findViewById(R.id.search_bb);
                LinearLayout request= (LinearLayout) d.findViewById(R.id.request_blood);

                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(Dashboard.this, com.simplicitydev.bloodbank.FindDonor.class);
                        startActivity(i);
                        d.dismiss();
                    }
                });

                request.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(Dashboard.this, com.simplicitydev.bloodbank.Emergency.class);
                        startActivity(i);
                        d.dismiss();
                    }
                });

                d.show();
            }
        });

        feedback_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog d=new Dialog(Dashboard.this);
                d.setContentView(R.layout.feedback_layout);
                final RatingBar ratingBar= (RatingBar) d.findViewById(R.id.rating);
                final EditText comment= (EditText) d.findViewById(R.id.comment);
                Button submit= (Button) d.findViewById(R.id.submit);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String com=comment.getText().toString();
                        float rat=ratingBar.getRating();

                        String to[]={"hi.simplicitydev@gmail.com"};
                        String sub="Feedback for app";

                        if(com.isEmpty()&&rat==0){
                            Toast.makeText(Dashboard.this, "Please rate the app!", Toast.LENGTH_SHORT).show();
                        }

                        else{
                            Intent i=new Intent();
                            i.setData(Uri.parse("mailto:"));
                            i.putExtra(Intent.EXTRA_EMAIL,to);
                            i.putExtra(Intent.EXTRA_TEXT,"Rating:"+rat+"\n"+"Comment:"+com);
                            i.putExtra(Intent.EXTRA_SUBJECT,sub);
                            startActivity(i.createChooser(i,"E-Mail"));
                            d.dismiss();
                        }
                    }
                });
                d.show();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Dashboard.this, com.simplicitydev.bloodbank.AboutApp.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

       /* logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();
                Intent i=new Intent(Dashboard.this,Login.class);
                startActivity(i);
                finish();
            }
        });*/

       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i=new Intent(Dashboard.this, com.simplicitydev.bloodbank.Registration.class);
               startActivity(i);
           }
       });

    }

}

