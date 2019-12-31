package com.simplicitydev.bloodbank;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Custom_Adaptor extends ArrayAdapter<String>{

    Activity activity;
    ArrayList<String> name;
    ArrayList<String> address;
    ArrayList<String> phone;

    public Custom_Adaptor(Activity activity, ArrayList<String> name,ArrayList<String> address,ArrayList<String> phone){
        super(activity,android.R.layout.simple_list_item_1);
        this.activity=activity;
        this.name=name;
        this.address=address;
        this.phone=phone;
    }

    @Override
    public int getCount() {
        return name.size();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=activity.getLayoutInflater();
        View view=inflater.inflate(R.layout.blood_bank_list,null);

        TextView n= (TextView) view.findViewById(R.id.name);
        TextView a= (TextView) view.findViewById(R.id.address);
        TextView p= (TextView) view.findViewById(R.id.phone);


        n.setText(name.get(position));
        a.setText("Address: "+address.get(position));
        p.setText("Phone: "+phone.get(position));

        return view;
    }
}
