package com.simplicitydev.bloodbank;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import static android.R.id.list;

public class FindDonor extends AppCompatActivity implements com.simplicitydev.bloodbank.recyclerview_adaptor.onBloodBankClick {

    Spinner sp_st,sp_ds;

    Button submit;

  //  ListView listView;
    RecyclerView recyclerView;
    String ds[];


    TextView bb_list;

    String getUrl="https://api.data.gov.in/resource/fced6df9-a360-4e08-8ca0-f283fc74ce15?api-key=579b464db66ec23bdd00000102b7560567124ea5650210867e456e7a&format=json&offset=0&filters[__state]=Delhi";

    RequestQueue requestQueue;

    ArrayList<String> states,districts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_find_donor);

        sp_ds= (Spinner) findViewById(R.id.district);
        sp_st= (Spinner) findViewById(R.id.state);
        submit= (Button) findViewById(R.id.btn_submit);
        //listView= (ListView) findViewById(R.id.bbank);
        recyclerView= (RecyclerView) findViewById(R.id.bbank);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bb_list= (TextView) findViewById(R.id.bb_list);

        requestQueue= Volley.newRequestQueue(this);

        states=new ArrayList<>();
        states.add("Select State");
        try {
            JSONObject jobj=new JSONObject(loadJSON());
            JSONArray jarray=jobj.getJSONArray("states");

            for(int i=0;i<jarray.length();i++){
                JSONObject ob=jarray.getJSONObject(i);
                String st=ob.getString("state");

                states.add(st);
            }
        }
        catch (Exception e){}

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,states);
        sp_st.setAdapter(adapter);

        sp_st.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                districts=new ArrayList<>();
                districts.add("Select District");
                String st_selected=sp_st.getSelectedItem().toString();
                try {
                    JSONObject jobj=new JSONObject(loadJSON());
                    JSONArray jarray=jobj.getJSONArray("states");

                    for(int i=0;i<jarray.length();i++){
                        JSONObject ob=jarray.getJSONObject(i);
                        String st=ob.getString("state");

                        if(st.equals(st_selected)){
                            JSONArray ja=ob.getJSONArray("districts");

                            for(int j=0;j<ja.length();j++){
                                districts.add(ja.getString(j));
                            }
                        }

                    }
                }
                catch (Exception e){}

                ArrayAdapter<String> adapter2=new ArrayAdapter<String>(FindDonor.this,android.R.layout.simple_list_item_1,districts);
                sp_ds.setAdapter(adapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String di=sp_ds.getSelectedItem().toString();
                String st=sp_st.getSelectedItem().toString();

                if(di.equals("Select District")||st.equals("Select State")){
                    Toast.makeText(FindDonor.this, "Select a State and District!", Toast.LENGTH_SHORT).show();
                }
                else{
                    String di2=di.toUpperCase();
                    final ProgressDialog pd=new ProgressDialog(FindDonor.this);
                    pd.setMessage("Please Wait...");
                    pd.show();
                    getUrl="https://api.data.gov.in/resource/fced6df9-a360-4e08-8ca0-f283fc74ce15?api-key=579b464db66ec23bdd00000102b7560567124ea5650210867e456e7a&format=json&offset=0&filters[__district]="+di2;
                    bb_list.setVisibility(View.VISIBLE);
                    StringRequest serverRequest=new StringRequest(Request.Method.GET, getUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            ArrayList<String> list_name=new ArrayList<>();
                            ArrayList<String> list_address=new ArrayList<>();
                            ArrayList<String> list_phone=new ArrayList<>();
                            try {
                                JSONObject jobj=new JSONObject(response);
                                JSONArray jarray=jobj.getJSONArray("records");

                                for(int i=0;i<jarray.length();i++){
                                    JSONObject ob=jarray.getJSONObject(i);

                                    String name=ob.getString("_blood_bank_name");
                                    String address=ob.getString("__address");
                                    String phone=ob.getString("__contact_no");


                                    list_name.add(name);
                                    list_address.add(address);
                                    list_phone.add(phone);
                                }

                            }
                            catch (Exception e){}

                           /* Custom_Adaptor adaptor2=new Custom_Adaptor(FindDonor.this,list_name,list_address,list_phone);
                            listView.setAdapter(adaptor2);*/
                            com.simplicitydev.bloodbank.recyclerview_adaptor adaptor2=new com.simplicitydev.bloodbank.recyclerview_adaptor(FindDonor.this,list_name,list_address,list_phone,FindDonor.this);
                            recyclerView.setAdapter(adaptor2);
                            pd.dismiss();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(FindDonor.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    requestQueue.add(serverRequest);
                }
            }
        });

        /*recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final Dialog d=new Dialog(FindDonor.this);
                d.setContentView(R.layout.blood_bank_action);

                ImageView call= (ImageView) d.findViewById(R.id.call);
                ImageView direction= (ImageView) d.findViewById(R.id.direction);

                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StringRequest serverRequest=new StringRequest(Request.Method.GET, getUrl, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject jobj=new JSONObject(response);
                                    JSONArray jarray=jobj.getJSONArray("records");

                                    JSONObject ob=jarray.getJSONObject(position);

                                    String contact=ob.getString("__contact_no");

                                    d.dismiss();

                                    Intent i=new Intent(Intent.ACTION_DIAL);
                                    i.setData(Uri.parse("tel:"+contact));
                                    startActivity(i);
                                }
                                catch (Exception e){}
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(FindDonor.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        requestQueue.add(serverRequest);
                    }
                });

                direction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StringRequest serverRequest=new StringRequest(Request.Method.GET, getUrl, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try{
                                    JSONObject jobj=new JSONObject(response);
                                    JSONArray jarray=jobj.getJSONArray("records");

                                    JSONObject ob=jarray.getJSONObject(position);

                                    String lat=ob.getString("__latitude");
                                    String lol=ob.getString("__longitude");

                                    d.dismiss();

                                    Intent i=new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse("google.navigation:q="+lat+","+lol));
                                    i.setPackage("com.google.android.apps.maps");
                                    startActivity(i);
                                }
                                catch (Exception e){}
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(FindDonor.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        requestQueue.add(serverRequest);
                    }
                });

                d.show();
            }
        });*/


    }
    public String loadJSON(){
        String json=null;
        try{
            InputStream is=this.getAssets().open("states_and_districts.json");
            int size=is.available();
            byte[] buffer=new byte[size];
            is.read(buffer);
            is.close();
            json=new String(buffer,"UTF-8");
        }
        catch (Exception e){}
        return json;
    }

    @Override
    public void onClick(final int position) {
        final int pos=position;
        final Dialog d=new Dialog(FindDonor.this);
        d.setContentView(R.layout.blood_bank_action);

        ImageView call= (ImageView) d.findViewById(R.id.call);
        ImageView direction= (ImageView) d.findViewById(R.id.direction);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest serverRequest=new StringRequest(Request.Method.GET, getUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jobj=new JSONObject(response);
                            JSONArray jarray=jobj.getJSONArray("records");

                            JSONObject ob=jarray.getJSONObject(pos);

                            String contact=ob.getString("__contact_no");

                            d.dismiss();

                            Intent i=new Intent(Intent.ACTION_DIAL);
                            i.setData(Uri.parse("tel:"+contact));
                            startActivity(i);
                        }
                        catch (Exception e){}
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FindDonor.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(serverRequest);
            }
        });

        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest serverRequest=new StringRequest(Request.Method.GET, getUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jobj=new JSONObject(response);
                            JSONArray jarray=jobj.getJSONArray("records");

                            JSONObject ob=jarray.getJSONObject(pos);

                            String lat=ob.getString("__latitude");
                            String lol=ob.getString("__longitude");

                            d.dismiss();

                            Intent i=new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse("google.navigation:q="+lat+","+lol));
                            i.setPackage("com.google.android.apps.maps");
                            startActivity(i);
                        }
                        catch (Exception e){}
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FindDonor.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(serverRequest);
            }
        });

        d.show();
    }
}
