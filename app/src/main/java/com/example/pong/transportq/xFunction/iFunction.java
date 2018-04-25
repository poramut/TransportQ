package com.example.pong.transportq.xFunction;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by User on 3/7/2560. */

public class iFunction {
    //private String xIp = "http://fai01.dyndns.biz:8181";
    //private String xIp = "http://192.168.43.141:8765";
    //private String xIp = "http://192.168.0.142:8765";
    //private String xIp = "http://192.168.1.205:8765";
    private String xIp = "http://phc.dyndns.biz:8181";
//    private String xIp = "http:///172.20.10.12:8765";
//    private String xIp = "http://192.168.0.100";

    private String TAG_RESULTS = "result";
    // private String xIp = "http://172.16.1.2:8181";

    private String xPath = "/transporterq";

    private String xUrl = xIp + xPath;

    public String getTAG_RESULTS() {
        return TAG_RESULTS;
    }

    public String chklogin() { return xUrl + "/chklogin.php"; }
    public String issend() { return xUrl + "/issend.php"; }
    public String nosend() { return xUrl + "/nosend.php"; }
    public String getdetail() { return xUrl + "/getdetail.php"; }
    public String getdoc_tsq() { return xUrl + "/getdoc_tsq.php"; }


    public String getTodayLong() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(Calendar.getInstance().getTime());
    }

    public static String justifyLeft( String str,final int width,char padWithChar) {
        // Trim the leading and trailing whitespace ...
        str = str != null ? str.trim() : "";

        int addChars = str.length();
        // android.util.Log.d("AAA",addChars+""+width);

        if (addChars < 0) {
            // truncate
            return str.subSequence(0, width).toString();
        }
        // Write the content ...
        int prependNumber = 0;
        final StringBuilder sb = new StringBuilder();

        // Add the actual content
        sb.append(str);
        // Prepend the pad character(s) ...
        for (prependNumber=addChars;prependNumber<width;prependNumber++) {
            sb.append(padWithChar);
        }
        return sb.toString();
    }

    public static String justifyRight( String str,final int width,char padWithChar) {
        // Trim the leading and trailing whitespace ...
        str = str != null ? str.trim() : "";

        int addChars = str.length();
        // android.util.Log.d("AAA",addChars+""+width);

        if (addChars < 0) {
            // truncate
            return str.subSequence(0, width).toString();
        }
        // Write the content ...
        int prependNumber = 0;
        final StringBuilder sb = new StringBuilder();
        // Prepend the pad character(s) ...
        for (prependNumber=addChars;prependNumber<width;prependNumber++) {
            sb.append(padWithChar);
        }
        // Add the actual content
        sb.append(str);
        return sb.toString();
    }

    public static String justifyCenter( String str,final int width,char padWithChar) {
        // Trim the leading and trailing whitespace ...
        str = str != null ? str.trim() : "";

        int addChars = width - str.length();
        //android.util.Log.d("AAA",addChars+"");

        if (addChars < 0) {
            // truncate
            return str.subSequence(0, width).toString();
        }
        // Write the content ...
        int prependNumber = addChars/2;

        int appendNumber = prependNumber;
        if ((prependNumber + appendNumber) != addChars) {
            ++prependNumber;
        }

        final StringBuilder sb = new StringBuilder();

        // Prepend the pad character(s) ...
        while (prependNumber > 0) {
            sb.append(padWithChar);
            --prependNumber;
        }

        // Add the actual content
        sb.append(str);

        // Append the pad character(s) ...
        while (appendNumber > 0) {
            sb.append(padWithChar);
            --appendNumber;
        }

        return sb.toString();
    }


    public String tableFormat(String a, String b)
    {
        return  a +""+ b;
        //return String.format("%3s", a) +""+ String.format("%37s", b);
    }

    public String getLine(final int width,String padWithChar){
        String sLine = "";
        for (int i=0;i<width;i++) {
            sLine += padWithChar;
        }
        return sLine+"\n";
    }


}


class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}