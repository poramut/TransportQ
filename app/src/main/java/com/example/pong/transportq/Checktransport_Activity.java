package com.example.pong.transportq;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pong.transportq.Adapter.adepter_detail;
import com.example.pong.transportq.propoties.qTransporter;
import com.example.pong.transportq.xFunction.RegisterUserClass;
import com.example.pong.transportq.xFunction.Session;
import com.example.pong.transportq.xFunction.iFunction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Checktransport_Activity extends AppCompatActivity implements LocationListener {

    private Session session;
    TextView txtName ;
    //TextView txtdetail ;
    String xName;
    String Login_Code;
    ArrayList<qTransporter> results = new ArrayList<qTransporter>();
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checktransport_);
        Bundle bd = getIntent().getExtras();
        if(bd !=null)
        {
            Login_Code = bd.getString("Login_Code");

        }
        session = new Session(getApplicationContext());
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        getLocation();
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
                        String swap;
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
                        if (c.getString("detail").equals(null)||c.getString("detail").equals(" ")){
                            tq.setDetail(" ");
                        }else{
                            tq.setDetail(c.getString("detail"));
                        }
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

    public void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
//        locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());
        String strLati = String.valueOf(location.getLatitude());
        String strLongti = String.valueOf(location.getLongitude());
        session.setLatti(strLati);
        session.setLongti(strLongti);

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            session.setLocation(addresses.get(0).getAddressLine(0));
        }catch(Exception e)
        {

        }

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(Checktransport_Activity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }
}
