package com.simplicitydev.bloodbank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class AboutApp extends AppCompatActivity {

    TextView v,l,cv,rv,bb,hosp,st,server;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_about_app);

        v= (TextView) findViewById(R.id.volley);
        v.setClickable(true);
        v.setMovementMethod(LinkMovementMethod.getInstance());
        String t1="<a href='https://github.com/google/volley'>Volley</a>";
        v.setText(Html.fromHtml(t1));

        l= (TextView) findViewById(R.id.lottie);
        l.setClickable(true);
        l.setMovementMethod(LinkMovementMethod.getInstance());
        String t2="<a href='https://github.com/airbnb/lottie'>Lottie Library</a>";
        l.setText(Html.fromHtml(t2));

        cv= (TextView) findViewById(R.id.cardview);
        cv.setClickable(true);
        cv.setMovementMethod(LinkMovementMethod.getInstance());
        String t3="<a href='https://developer.android.com/reference/android/support/v7/widget/CardView'>Card View</a>";
        cv.setText(Html.fromHtml(t3));

        rv= (TextView) findViewById(R.id.recyclerview);
        rv.setClickable(true);
        rv.setMovementMethod(LinkMovementMethod.getInstance());
        String t4="<a href='https://developer.android.com/guide/topics/ui/layout/recyclerview'>Recycler View</a>";
        rv.setText(Html.fromHtml(t4));

        bb= (TextView) findViewById(R.id.bb_api);
        bb.setClickable(true);
        bb.setMovementMethod(LinkMovementMethod.getInstance());
        String t5="<a href='https://data.gov.in/resources/blood-bank-directory-updated-till-last-month/api'>Blood Bank Directory API</a>";
        bb.setText(Html.fromHtml(t5));

        hosp= (TextView) findViewById(R.id.hosp_api);
        hosp.setClickable(true);
        hosp.setMovementMethod(LinkMovementMethod.getInstance());
        String t6="<a href='https://data.gov.in/resources/hospital-directory-geo-code-september-2015/api'>Hospital Directory API</a>";
        hosp.setText(Html.fromHtml(t6));

        st= (TextView) findViewById(R.id.st_json);
        st.setClickable(true);
        st.setMovementMethod(LinkMovementMethod.getInstance());
        String t7="<a href='https://github.com/sab99r/Indian-States-And-Districts'>States and Districts Directory JSON</a>";
        st.setText(Html.fromHtml(t7));

        server= (TextView) findViewById(R.id.webhost);
        server.setClickable(true);
        server.setMovementMethod(LinkMovementMethod.getInstance());
        String t8="<a href='https://www.000webhost.com'>000Webhost Server</a>";
        server.setText(Html.fromHtml(t8));
    }
}
