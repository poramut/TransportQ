package com.example.pong.transportq;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pong.transportq.xFunction.RegisterUserClass;
import com.example.pong.transportq.xFunction.iFunction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class Login_Activity extends Activity {

    EditText uname;
    EditText pword;
    String Login_Code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        uname = (EditText) findViewById(R.id.uname);
        pword = (EditText) findViewById(R.id.pword);
        //  callService();
        Button bt1 = (Button) findViewById(R.id.bt1);
        bt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                //loginUser();
                setUser(uname.getText().toString(),pword.getText().toString());

            }
        });
        //getUser();

    }


    //setUsernamePassword
    public void setUser(String uname,String pword) {
        class setUser extends AsyncTask<String, Void, String> {
            // variable

            iFunction iFt = new iFunction();
            String REGISTER_URL = iFt.chklogin();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObj = new JSONObject(s);
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                    int cnt = 0;
                    String Scc = "";
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        Login_Code = c.getString("User");
                        Log.d("AAAAA",c.getString("User"));
                        Log.d("AA",c.getString("Scc"));
                        Scc = c.getString("Scc");

                    }
                    if(Scc.equals("true")){
                        Toast.makeText(Login_Activity.this, "เข้าสู่ระบบสำเร็จ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login_Activity.this,Checktransport_Activity.class);
                        //Intent intent = new Intent(MainActivity.this, second.class);
                        Log.d("logincode M1 == ", Login_Code);
                        intent.putExtra("Scc",Scc);
                        intent.putExtra("Login_Code",Login_Code);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(Login_Activity.this, "กรุณาตรวจสอบ Username/Password ใหม่อีกครั้ง", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                RegisterUserClass ruc = new RegisterUserClass();
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("uname", params[0]);
                data.put("pword", params[1]);
                Log.d("AA", params[0]);
                Log.d("AA", params[1]);
                String result = ruc.sendPostRequest(REGISTER_URL,data);
                Log.d("resultLogIn == ",result);
                return result;
            }
        }
        setUser ru = new setUser();
        ru.execute(uname,pword);
    }

}