package com.example.pong.transportq.Adapter;

/**
 * Created by Pong on 4/20/2018.
 */

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.example.pong.transportq.Checktransport_Activity;
import com.example.pong.transportq.R;
import com.example.pong.transportq.propoties.qTransporter;
import com.example.pong.transportq.xFunction.RegisterUserClass;
import com.example.pong.transportq.xFunction.Session;
import com.example.pong.transportq.xFunction.iFunction;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 20/7/2560.
 */

public class adepter_detail extends ArrayAdapter {
    private Session session;
    private ArrayList<qTransporter> listData;
    private AppCompatActivity aActivity;
    private iFunction iFt = new iFunction();
    private String User="";
    //int num = 1;
    ArrayList<qTransporter> results = new ArrayList<qTransporter>();

    private String REGISTER_URL;

    public adepter_detail(AppCompatActivity aActivity, ArrayList<qTransporter> listData) {
        super(aActivity, 0, listData);
        this.aActivity= aActivity;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        session = new Session(this.aActivity);
        LayoutInflater inflater = (LayoutInflater) aActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = inflater.inflate(R.layout.list_detail, parent, false);
        final qTransporter pCus = listData.get(position);
        final int xps = position;
        final String issend;
        final String xDocNo;
        TextView txtNameTH = (TextView)v.findViewById(R.id.FName);
        TextView numNo = (TextView)v.findViewById(R.id.nub);
        EditText note = (EditText) v.findViewById(R.id.note);
        String txtdetail;
        User = listData.get(position).getTqDI();
        xDocNo = listData.get(position).getDocNo();
        issend = listData.get(position).getIsSend();
        txtNameTH.setText(listData.get(position).getFName());
        note.setText(listData.get(position).getDetail());
        numNo.setText(listData.get(position).getIndex());
        note.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    final EditText txt = (EditText) v;
                    // Log.d("LoginCode",LoginCode );
                    listData.get( xps ).setDetail( txt.getText().toString() );
                     //txtdetail = listData.get(xps).getDetail();
                }
            }
        });
        note.setOnKeyListener(new EditText.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Log.d("OnKeyListener", keyCode + " character(code) to send");
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    final EditText txt = (EditText) v;
                    // Log.d("LoginCode",LoginCode );
                    listData.get( xps ).setDetail( txt.getText().toString() );
                    return true;
                }
                return false;
            }
        });



       ToggleButton bts2 = (ToggleButton) v.findViewById(R.id.bts2);
        if (issend.equals("0")){
            bts2.setChecked(false);
            bts2.setBackgroundColor(Color.RED);
            //issend(xDocNo);
            //nosend(xDocNo);
        }else if(issend.equals("1")){
            bts2.setBackgroundColor(Color.GREEN);
            bts2.setChecked(true);
            //issend(xDocNo);
            //nosend(xDocNo);
        }
        bts2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked) {
                    buttonView.setBackgroundColor(Color.GREEN);
                    String test;
                    ( (Checktransport_Activity)aActivity ).getLocation();
                    String latti = session.getLatti();
                    String longti = session.getLongti();
                    String location = session.getLocation();
                    Log.d("Latlong", latti);
                    Log.d("Latlong", longti);
                    Log.d("Latlong", location);
                    issend(xDocNo,latti,longti,location);
                }else{
                    buttonView.setBackgroundColor(Color.RED);
                    nosend(xDocNo);
                }
            }
        });



        Button save = (Button) v.findViewById(R.id.savetq);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("บันทึกหมายเหตุ");
                builder.setMessage("ต้องการบันทึกหมายเหตุนี้หรือไม่");
                builder.setPositiveButton("ใช่",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("BBBBBB", listData.get(xps).getDetail());
                                getdetail(xDocNo,listData.get(xps).getDetail());
                                //( (Checktransport_Activity)aActivity ).getlistdata( listData.get(xps).getDetail() );
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //( (Checktransport_Activity)aActivity ).getlistdata(User);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        return v;
    }

    public void nosend(String xDocNo) {
        class nosend extends AsyncTask<String, Void, String> {
            // variable

            iFunction iFt = new iFunction();
            String REGISTER_URL = iFt.nosend();
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
                        Log.d("AA",c.getString("Scc"));
                        Scc = c.getString("Scc");

                    }

                    if(Scc.equals("true")){
                        Toast.makeText(( (Checktransport_Activity)aActivity ), "ยังไม่ได้ส่ง", Toast.LENGTH_SHORT).show();
                        ( (Checktransport_Activity)aActivity ).clearlist();
                        ( (Checktransport_Activity)aActivity ).getlistdata(User);

                        //notifyDataSetChanged();
                    }else{
                        Toast.makeText(( (Checktransport_Activity)aActivity ), "", Toast.LENGTH_SHORT).show();
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
                data.put("DocNo",params[0]);
                Log.d("ACCTT22", params[0]);
                String result = ruc.sendPostRequest(REGISTER_URL,data);
                return result;
            }
        }
        nosend ru = new nosend();
        ru.execute(xDocNo);
    }
    public void issend(final String xDocNo,String lati,String longti,String location) {
        class issend extends AsyncTask<String, Void, String> {
            // variable

            iFunction iFt = new iFunction();
            String REGISTER_URL = iFt.issend();
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
                        Log.d("AA",c.getString("Scc"));
                        Scc = c.getString("Scc");

                    }
                    if(Scc.equals("true")){
                        Toast.makeText(( (Checktransport_Activity)aActivity ), "ส่งเสร็จแล้ว", Toast.LENGTH_SHORT).show();

                        ( (Checktransport_Activity)aActivity ).clearlist();
                        ( (Checktransport_Activity)aActivity ).getlistdata(User);
                    }else{
                        Toast.makeText(( (Checktransport_Activity)aActivity ), "", Toast.LENGTH_SHORT).show();
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
                data.put("DocNo",params[0]);
                data.put("Lati",params[1]);
                data.put("Longti",params[2]);
                data.put("Location",params[3]);
                Log.d("ACCTT22", params[0]);
                String result = ruc.sendPostRequest(REGISTER_URL,data);
                return result;
            }
        }
        issend ru = new issend();
        ru.execute(xDocNo,lati,longti,location);
    }
    public void getdetail(String xDocNo,String detail) {
        class nosend extends AsyncTask<String, Void, String> {
            // variable

            iFunction iFt = new iFunction();
            String REGISTER_URL = iFt.getdetail();
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
                        Log.d("AA",c.getString("Scc"));
                        Scc = c.getString("Scc");

                    }
                    if(Scc.equals("true")){
                        Toast.makeText(( (Checktransport_Activity)aActivity ), "บันทึกหมายเหตุแล้ว", Toast.LENGTH_SHORT).show();
                        ( (Checktransport_Activity)aActivity ).clearlist();
                        ( (Checktransport_Activity)aActivity ).getlistdata(User);
                    }else{
                        Toast.makeText(( (Checktransport_Activity)aActivity ), "", Toast.LENGTH_SHORT).show();
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
                data.put("DocNo",params[0]);
                data.put("detail",params[1]);
                Log.d("ACCTT22", params[0]);
                String result = ruc.sendPostRequest(REGISTER_URL,data);
                return result;
            }
        }
        nosend ru = new nosend();
        ru.execute(xDocNo,detail);
    }



}