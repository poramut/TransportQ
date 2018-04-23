package com.example.pong.transportq;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pong.transportq.Adapter.adepter_detail;
import com.example.pong.transportq.propoties.qTransporter;
import com.example.pong.transportq.xFunction.RegisterUserClass;
import com.example.pong.transportq.xFunction.iFunction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Checktransport_Activity extends AppCompatActivity {


    TextView txtName ;
    //TextView txtdetail ;
    String xName;
    String Login_Code;
    ArrayList<qTransporter> results = new ArrayList<qTransporter>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checktransport_);
        Bundle bd = getIntent().getExtras();
        if(bd !=null)
        {
            Login_Code = bd.getString("Login_Code");

        }
        txtName= (TextView) findViewById(R.id.tName);
        //txtdetail= (TextView) findViewById(R.id.note);
        Log.d("BBBB", Login_Code );
        getlistdata(Login_Code);
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("กลับไปหน้าล็อคอิน")
                .setMessage("กลับไปหน้าล็อคอิน?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                        Checktransport_Activity.super.onBackPressed();
                        Intent intent = new Intent(Checktransport_Activity.this,Login_Activity.class);
                        //intent.putExtra("Login_Code",Login_Code);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }

    public void getlistdata(final String Login_Code) {
        class getlistdata extends AsyncTask<String, Void, String> {
            // variable

            iFunction iFt = new iFunction();
            String REGISTER_URL = iFt.getdoc_tsq();
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try{
                    JSONObject jsonObj = new JSONObject(s);
                    JSONArray setRs = jsonObj.getJSONArray(iFt.getTAG_RESULTS());
                        String scc;
                        String nub ;
                    Log.d("BBBBYO!", Login_Code );
                    for(int i=0;i<setRs.length();i++) {
                        JSONObject c = setRs.getJSONObject(i);
                        qTransporter tq = new qTransporter();
                        scc = c.getString("Scc");
                        nub=i+1+"";
                        tq.setIndex(nub);
                        tq.setDocNo(c.getString("DocNo"));
                        tq.setDueDate(c.getString("DueDate"));
                        tq.setFName(c.getString("FName"));
                        tq.setIsSend(c.getString("IsSend"));
                        tq.setxName(c.getString("xName"));
                        tq.setDetail(c.getString("detail"));
                        tq.setTqDI(c.getString("transportID"));
                        xName = c.getString("xName");
                        Log.d("FName ==",c.getString("FName") );
                        Log.d("FName ==",c.getString("DocNo") );
                        Log.d("FName ==",c.getString("IsSend") );
                        Log.d("FName ==",c.getString("DueDate") );
                        Log.d("FName ==",c.getString("xName") );
                        Log.d("FName ==",c.getString("detail") );

                        results.add(tq);
                    }

                    ListView lv = (ListView) findViewById(R.id.transport_list);
                    lv.setAdapter(new adepter_detail(Checktransport_Activity.this,results));
                    lv.setItemsCanFocus(true);
                    txtName.setText(xName);


                }catch (JSONException e){
                    Log.d("BBBB", "Hello-*-845" );
                }


            }

            //class connect php RegisterUserClass important !!!!!!!
            @Override
            protected String doInBackground(String... params) {
                RegisterUserClass ruc = new RegisterUserClass();
                HashMap<String, String> data = new HashMap<String,String>();
                //data.put("flag",flag);
                data.put("Login_Code",params[0]);
                Log.d("Login_Code = 22 -- ", params[0]);
                //Log.d("AACCTT", flag);
                String result = ruc.sendPostRequest(REGISTER_URL,data);
                Log.d("result List Doc = ", result );
                return result;
            }
        }
        getlistdata ru = new getlistdata();
        ru.execute(Login_Code);
    }

    public void clearlist() {
        results.clear();
    }
}
