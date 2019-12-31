package com.simplicitydev.bloodbank;

import android.app.ProgressDialog;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;




public class Emergency extends AppCompatActivity {

    EditText name1,hospital,city1,phno;

    Button sub,rst;

    Spinner sp;

    String bg[];

    RequestQueue requestQueue;

    FirebaseDatabase database;
    DatabaseReference reference;

   // String url="https://simplicitydev.000webhostapp.com/getph.php";
   // String sendmsg="https://api.textlocal.in/send/?";
    String apik = "F2ceRYhEiQ0-RoyVTdX8tOEXnUgwXDG63ZytdfwrB2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_emergency);

        database=FirebaseDatabase.getInstance();
        reference=database.getReference("donors");

        bg = getResources().getStringArray(R.array.bloodgroup);

        requestQueue = Volley.newRequestQueue(this);

        name1 = (EditText) findViewById(R.id.name);
        hospital = (EditText) findViewById(R.id.hospname);
        city1 = (EditText) findViewById(R.id.city);
        phno = (EditText) findViewById(R.id.ph);

        sub = (Button) findViewById(R.id.submit);
        rst= (Button) findViewById(R.id.rs);

        sp = (Spinner) findViewById(R.id.bloodgrp);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bg);
        sp.setAdapter(adapter);

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameofpatient = name1.getText().toString();
                String hospitalresiding = hospital.getText().toString();
                final String cityresiding = city1.getText().toString();
                final String bloodg = sp.getSelectedItem().toString();
                String p = phno.getText().toString();

                if(nameofpatient.isEmpty()||hospitalresiding.isEmpty()||cityresiding.isEmpty()||p.isEmpty()||bloodg.equals("Select Blood Group")){
                    Toast.makeText(Emergency.this, "Please fill all the details!", Toast.LENGTH_SHORT).show();
                }

                else {

                    final String msg = "Urgent blood required:" + "\nPatient Name:" + nameofpatient + "\nHospital:" + hospitalresiding + "\nBlood Group:" +
                            bloodg + "\nPhone No.:" + p + "\nContact Immediately";
                    /*final ArrayList<String> candidates = new ArrayList<String>();
                    final ArrayList<String> cities = new ArrayList<String>();
                    final ArrayList<String> blood = new ArrayList<String>();
*/
                    final ArrayList<Donors> f=new ArrayList<>();

                    final ProgressDialog pd = new ProgressDialog(Emergency.this);
                    pd.setMessage("Please Wait...");

                    /*StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jobj = new JSONObject(response);
                                JSONArray jarray = jobj.getJSONArray("result");

                                for (int i = 0; i < jarray.length(); i++) {
                                    JSONObject obj = jarray.getJSONObject(i);

                                    String ph = obj.getString("phoneno");
                                    String ct=obj.getString("city");
                                    String b=obj.getString("blood_group");

                                    candidates.add(ph);
                                    cities.add(ct);
                                    blood.add(b);
                                }
                            } catch (Exception e) {
                            }

                            for (int i = 0; i < candidates.size(); i++) {
                                if(blood.get(i).equals(bloodg)&&cities.get(i).equals(cityresiding)) {
                                    String d = candidates.get(i);
                                    SendSMS(d, msg);
                                    //Toast.makeText(Home.this, "" + d, Toast.LENGTH_SHORT).show();
                                }
                            }
                            pd.dismiss();
                            Toast.makeText(Emergency.this, "SMS Sent to prospective donors", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pd.dismiss();
                            Toast.makeText(Emergency.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    pd.show();
                    requestQueue.add(stringRequest);*/


                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot d:dataSnapshot.getChildren()){
                                Donors obj=d.getValue(Donors.class);

                                f.add(obj);

                                /*candidates.add(obj.getPhoneno());
                                cities.add(obj.getCity());
                                blood.add(obj.getBlood_group());*/
                            }

                            /*for (int i = 0; i < candidates.size(); i++) {
                                if(blood.get(i).equals(bloodg)&&cities.get(i).equals(cityresiding)) {
                                    String d = candidates.get(i);
                                    SendSMS(d, msg);
                                    //Toast.makeText(Home.this, "" + d, Toast.LENGTH_SHORT).show();
                                }
                            }*/
                            String d="";
                            for (int i = 0; i < f.size(); i++) {
                                if(f.get(i).getBlood_group().equals(bloodg)&&f.get(i).getCity().equals(cityresiding)) {
                                    d = f.get(i).getPhoneno();
                                    SendSMS(d, msg);
                                    //Toast.makeText(Home.this, "" + d, Toast.LENGTH_SHORT).show();
                                }
                            }
                            pd.dismiss();
                            Toast.makeText(Emergency.this, "SMS Sent to prospective donors"+d, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(Emergency.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        rst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name1.setText("");
                city1.setText("");
                hospital.setText("");
                phno.setText("");
            }
        });

    }
    public void SendSMS(String donors, final String msg){
        try {
            // Send data

            String apiKey = "apikey=" + apik;
            String message = "&message=" + msg;
            String sender = "&sender=" + "TXTLCL";
            String numbers = "&numbers=" + donors;

            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String params=apiKey+numbers+message+sender;

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(params.length()));
            conn.getOutputStream().write(params.getBytes(StandardCharsets.UTF_8));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            //final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                // stringBuffer.append(line);
                Toast.makeText(getApplicationContext(),"the message is"+line,Toast.LENGTH_SHORT).show();
            }
            rd.close();

            // return stringBuffer.toString();
        } catch (Exception e) {
            // System.out.println("Error SMS " + e);
            Toast.makeText(getApplicationContext(),"Message Not Sent"+e,Toast.LENGTH_SHORT).show();
            // return "Error "+e;
        }
    }

}

