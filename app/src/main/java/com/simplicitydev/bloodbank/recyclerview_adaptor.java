package com.simplicitydev.bloodbank;



import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class recyclerview_adaptor extends RecyclerView.Adapter<recyclerview_adaptor.MyViewHolder>{

    Activity activity;
    ArrayList<String> name;
    ArrayList<String> address;
    ArrayList<String> phone;
    onBloodBankClick bloodBankClick;


    public recyclerview_adaptor(Activity activity, ArrayList<String> name,ArrayList<String> address,ArrayList<String> phone,onBloodBankClick bloodBankClick){

        this.activity=activity;
        this.name=name;
        this.address=address;
        this.phone=phone;
        this.bloodBankClick=bloodBankClick;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater obj=activity.getLayoutInflater();
        View view=obj.inflate(R.layout.blood_bank_list,null);
        return new MyViewHolder(view, bloodBankClick);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.n.setText(name.get(position));
        holder.a.setText("Address: "+address.get(position));
        holder.p.setText("Phone: "+phone.get(position));
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView n;
        TextView a;
        TextView p;
        onBloodBankClick bloodBankClick;
        public MyViewHolder(View itemView, onBloodBankClick bloodBankClick) {
            super(itemView);
            n= (TextView) itemView.findViewById(R.id.name);
            a= (TextView) itemView.findViewById(R.id.address);
            p= (TextView) itemView.findViewById(R.id.phone);
            this.bloodBankClick=bloodBankClick;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            bloodBankClick.onClick(getAdapterPosition());
        }
    }

    public interface onBloodBankClick{
        void onClick(int position);
    }
}
