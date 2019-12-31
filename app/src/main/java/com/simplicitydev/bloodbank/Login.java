package com.simplicitydev.bloodbank;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.simplicitydev.bloodbank.R.drawable.error;
import static com.simplicitydev.bloodbank.R.id.success;

public class Login extends AppCompatActivity implements View.OnClickListener{


    EditText textInputUsername;
   // TextInputEditText textInputPassword;
    EditText textInputPassword;
    Button btn_login;
    TextView showpass;
    Button register;

    SharedPreferences pref;
    SharedPreferences.Editor edit;

    RequestQueue requestQueue;


    String loginUrl="https://simplicitydev.000webhostapp.com/login.php";

    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref=getSharedPreferences("login", Context.MODE_PRIVATE);
        edit=pref.edit();

        String u=pref.getString("user",null);
        if(u!=null){
            Intent i=new Intent(Login.this,Dashboard.class);
            startActivity(i);
            finish();
        }


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);


        requestQueue= Volley.newRequestQueue(this);

        textInputUsername = (EditText) findViewById(R.id.tect_input_userName);
     //   textInputPassword = (TextInputEditText) findViewById(R.id.tect_input_pass);
        textInputPassword= (EditText) findViewById(R.id.tect_input_pass);
        btn_login = (Button) findViewById(R.id.btn_Login);
        register = (Button) findViewById(R.id.btn_Register);
        showpass= (TextView) findViewById(R.id.showpass);

        showpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(flag==1){
                    flag=0;
                    showpass.setText("HIDE");

                    textInputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    textInputPassword.setSelection(textInputPassword.getText().length());
                }

                else{
                    flag=1;
                    showpass.setText("SHOW");

                    textInputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    textInputPassword.setSelection(textInputPassword.getText().length());
                }
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Login.this, com.simplicitydev.bloodbank.Registration.class);
                startActivity(i);
                finish();
            }
        });

        btn_login.setOnClickListener(this);


    }

    private boolean validUsername()
    {
        String username = textInputUsername.getText().toString().trim();

        if (username.isEmpty()){

            textInputUsername.setError("Field Can't be Empty");
            return false;
        }else if(username.length()>15){

            textInputUsername.setError("Username Too Long");
            return false;


        }else {

            textInputUsername.setError(null);
            return true;
        }

    }
    private boolean validatePassword()
    {

        String passwordInput = textInputPassword.getText().toString().trim();

        if (passwordInput.isEmpty())
        {

            textInputPassword.setError("Field can't be empty");
            return false;

        }
        else if(passwordInput.length()>12) {

            Toast.makeText(this, "Password Max. 12 characters!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            textInputPassword.setError(null);
            return true;
        }
    }

    @Override
    public void onClick(View v) {

        final String user=textInputUsername.getText().toString();
        final String pass=textInputPassword.getText().toString();

        if (!validUsername() | !validatePassword())
        {
            return;
        }

      /*  else if(Login(user,pass)){
            edit.putString("user",user);
            edit.putString("pass",pass);
            edit.commit();
            Intent i=new Intent(Login.this,Dashboard.class);
            startActivity(i);
            finish();
        }*/
        else{
            final ProgressDialog pd=new ProgressDialog(this);
            pd.setMessage("Please Wait...");
            pd.setCanceledOnTouchOutside(false);
            pd.show();
            final StringRequest serverRequest=new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    /*try{
                        JSONObject jobj=new JSONObject(response);
                        JSONArray jsonArray=jobj.getJSONArray("result");

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject ob=jsonArray.getJSONObject(i);

                            String u=ob.getString("username");
                            String p=ob.getString("password");

                            if((u.equals(user))&&(p.equals(pass))){
                                loginflag=1;
                                break;
                            }
                        }*/

                       /* if(loginflag==1){
                            pd.dismiss();
                            edit.putString("user",user);
                            edit.putString("pass",pass);
                            edit.commit();
                            Intent i=new Intent(Login.this,Dashboard.class);
                            startActivity(i);
                            finish();
                        }*/

                       if(response.equals("success")){
                           pd.dismiss();
                           edit.putString("user",user);
                           edit.putString("pass",pass);
                           edit.commit();
                           Intent i=new Intent(Login.this,Dashboard.class);
                           startActivity(i);
                           finish();
                       }

                        else{
                            pd.dismiss();
                            Toast.makeText(Login.this, "Invalid username or password!", Toast.LENGTH_SHORT).show();
                        }
                   /* }
                    catch (Exception e){

                    }*/
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pd.dismiss();
                    Toast.makeText(Login.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> data=new HashMap<>();
                    data.put("username",user);
                    data.put("password",pass);

                    return data;
                }
            };
            requestQueue.add(serverRequest);
        }

       /* String input = "Username: " + textInputUsername.getText().toString();
        input += "\n";
        input += "Password: " +textInputPassword.getText().toString();

        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
*/
    }

}

