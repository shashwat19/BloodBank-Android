package com.simplicitydev.bloodbank;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Registration extends AppCompatActivity {

    EditText e2,e3,e4,e6,e7;

    Spinner sp;

    Button btn2,check_email;
    ImageView btn;

    RadioButton r1,r2;

    int flag=0,i;

    String bloodgrp[];
    String name,phoneno,email,gender,bg,address,city;
    LocationManager lm;

    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference reference2;

    ProgressDialog pd;

    RequestQueue requestQueue;

    ArrayList<Donors> f;

   /* SharedPreferences pref;
    SharedPreferences.Editor edit;*/

    String setUrl="https://simplicitydev.000webhostapp.com/setdata2.php";
    //String getUrl="https://simplicitydev.000webhostapp.com/getdata.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        lm= (LocationManager) getSystemService(LOCATION_SERVICE);

        database=FirebaseDatabase.getInstance();
        reference= database.getReference("donors");
        reference2= database.getReference("donors");

        requestQueue= Volley.newRequestQueue(this);

       /* pref=getSharedPreferences("login", Context.MODE_PRIVATE);
        edit=pref.edit();*/

       // e1= (EditText) findViewById(R.id.editText);
        e2= (EditText) findViewById(R.id.editText2);
        e3= (EditText) findViewById(R.id.editText3);
        e4= (EditText) findViewById(R.id.editText4);
       // e5= (EditText) findViewById(R.id.editText5);
        e6= (EditText) findViewById(R.id.editText6);
        e7= (EditText) findViewById(R.id.editText7);

        r1= (RadioButton) findViewById(R.id.radioButton1);
        r2= (RadioButton) findViewById(R.id.radioButton2);

        pd=new ProgressDialog(this);
        sp= (Spinner) findViewById(R.id.spinner);

        btn= (ImageView) findViewById(R.id.imageView);
        btn2= (Button) findViewById(R.id.button2);
        check_email= (Button) findViewById(R.id.chk_email);

        bloodgrp=getResources().getStringArray(R.array.bloodgroup);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,bloodgrp);
        sp.setAdapter(adapter);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(Registration.this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Registration.this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Registration.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},0);
                    return;
                }


                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        double lat=location.getLatitude();
                        double lol=location.getLongitude();

                        Geocoder data=new Geocoder(Registration.this);

                        try{
                            List<Address> list=data.getFromLocation(lat,lol,1);
                            address=list.get(0).getAddressLine(0);
                        }
                        catch (Exception e){}
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        Toast.makeText(Registration.this, "Turn on Location!", Toast.LENGTH_LONG).show();
                    }
                });
                e4.setText(address);

            }
        });
        check_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=e3.getText().toString();
                f=new ArrayList<>();
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot d:dataSnapshot.getChildren()){
                            Donors dn=d.getValue(Donors.class);
                            f.add(dn);

                        }

                        for(i=0;i<f.size();i++){
                            String b=f.get(i).getEmail();
                            if(email.equals(b)){
                                flag=1;
                                break;
                            }
                        }

                        if(flag==1){
                            Toast.makeText(Registration.this, "Choose a different Email!", Toast.LENGTH_LONG).show();
                            flag=0;
                        }

                        else{
                            check_email.setEnabled(false);
                            check_email.setVisibility(View.INVISIBLE);
                            btn2.setVisibility(View.VISIBLE);
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Registration.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // username=e1.getText().toString();
                name=e6.getText().toString();
                city=e7.getText().toString();
                phoneno=e2.getText().toString();
                email=e3.getText().toString();
                gender="";
                if(r1.isChecked()){
                    gender=r1.getText().toString();
                }
                else if(r2.isChecked())
                {
                    gender=r2.getText().toString();
                }

                bg=sp.getSelectedItem().toString();
                address=e4.getText().toString();
               // password=e5.getText().toString();



                if(phoneno.isEmpty()||(email.isEmpty()||address.isEmpty())||(bg==bloodgrp[0])||(!r1.isChecked()&&!r2.isChecked())||name.isEmpty()||city.isEmpty()){
                    check_email.setEnabled(true);
                    check_email.setVisibility(View.VISIBLE);
                    btn2.setVisibility(View.INVISIBLE);
                    Toast.makeText(Registration.this, "Please fill out all the Details!", Toast.LENGTH_LONG).show();
                }
                /*else if((username.length()<2)||(username.length()>15)){
                    e1.setError("Min. 3 Max. 15 Characters!");
                }*/
                else if((address.length()<10)||(address.length()>200)){
                    e4.setError("Min. 10 Max. 200 Characters!");
                }

               /* else if((password.length()<6)||(password.length()>12)){

                    AlertDialog.Builder ab=new AlertDialog.Builder(Registration.this);
                    ab.setIcon(error);
                    ab.setTitle("Password Limit!");
                    ab.setMessage("Password should be Min. 6 Max. 12 Characters!");
                    ab.setPositiveButton("ok",null);
                    ab.show();
                    *//*Toast.makeText(Registration.this, "Password should be \nMin. 6 Max. 12 Characters!", Toast.LENGTH_LONG).show();*//*
                }*/

                else {

                    pd.setMessage("Please Wait...");
                    pd.setCanceledOnTouchOutside(false);
                    pd.show();


                    /*StringRequest serverRequest=new StringRequest(Request.Method.POST, setUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("Query Problem")){
                                pd.dismiss();
                                e3.setError("Choose a different Email!");
                            }
                            else{
                                pd.dismiss();
                                Dialog d=new Dialog(Registration.this);
                                d.setContentView(R.layout.custom_dialog);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i=new Intent(Registration.this,Dashboard.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }, 3000);
                                d.show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pd.dismiss();
                            Toast.makeText(Registration.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> data=new HashMap<String, String>();
                            //data.put("username",username);
                            data.put("name",name);
                            data.put("phoneno",phoneno);
                            data.put("email",email);
                            data.put("gender",gender);
                            data.put("blood_group",bg);
                            data.put("address",address);
                            data.put("city",city);
                            //data.put("password",password);

                            return data;
                        }
                    };
                    requestQueue.add(serverRequest);*/
                    Donors obj=new Donors();
                    obj.setAddress(address);
                    obj.setBlood_group(bg);
                    obj.setCity(city);
                    obj.setEmail(email);
                    obj.setGender(gender);
                    obj.setName(name);
                    obj.setPhoneno(phoneno);

                    String cName=reference.push().getKey();

                    reference.child(cName).setValue(obj);
                    pd.dismiss();
                    flag=0;
                    Dialog d=new Dialog(Registration.this);
                    d.setContentView(R.layout.custom_dialog);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i=new Intent(Registration.this,Dashboard.class);
                            startActivity(i);
                            finish();
                        }
                    }, 3000);
                    d.show();
                }

            }
        });
    }

}
