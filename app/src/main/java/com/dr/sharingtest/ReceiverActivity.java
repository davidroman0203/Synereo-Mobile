package com.dr.sharingtest;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dr.sharingtest.utils.MyConstants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReceiverActivity extends AppCompatActivity {

    String TAG = "Receiver";

    TextView tvUrl;
    TextView tvText;
    RelativeLayout rlBefore, rlAfter;
    Button btnAmplify, btnCopy, btnShare;
    RadioGroup rgAmp;

    SharedPreferences mSharedPref;
    SharedPreferences.Editor mEditor;

    String userName;

    String shortLink = ""; //link returned from snip.ly api

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        initView();
        initGlobal();

        Intent intent = getIntent();
        if (savedInstanceState == null && intent != null) {
            Log.d(TAG, "intent != null");

            if (intent.getAction().equals(Intent.ACTION_SEND)) {
                Log.d(TAG, "intent.getAction().equals(Intent.ACTION_SEND)");
                final String message = intent.getStringExtra(Intent.EXTRA_TEXT);



                btnAmplify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int selectedId = rgAmp.getCheckedRadioButtonId();
                        RadioButton rbSelected = (RadioButton) findViewById(selectedId);
                        String strAmp = "";
                        switch (rbSelected.getText().toString()){
                            case "1 amp":
                                strAmp = "1";
                                break;
                            case "5 amp":
                                strAmp = "5";
                                break;
                            case "10 amp":
                                strAmp = "10";
                                break;
                            default:
                        }

                        progressDialog = new ProgressDialog(ReceiverActivity.this);
                        progressDialog.setMessage("Requesting ...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        callRequestBin(message, strAmp);
                        callSniply(message, strAmp);
                    }
                });
                btnCopy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText(null, shortLink);
                        clipboard.setPrimaryClip(clip);

                        Toast.makeText(ReceiverActivity.this, "Copied to Clipboard!", Toast.LENGTH_LONG).show();
                    }
                });
                btnShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = shortLink;
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share Link");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share via"));
                    }
                });

            }
        }
    }

    void initView(){
        tvUrl = (TextView)findViewById(R.id.url);
        tvText = (TextView)findViewById(R.id.text);

        tvText.setText("");

        rlBefore = (RelativeLayout)findViewById(R.id.rlBefore);
        rlAfter = (RelativeLayout)findViewById(R.id.rlAfter);
        rlAfter.setVisibility(View.GONE);

        rgAmp = (RadioGroup)findViewById(R.id.radioAmp);

        btnAmplify = (Button)findViewById(R.id.btnAmplify);
        btnCopy = (Button)findViewById(R.id.btnCopy);
        btnShare = (Button)findViewById(R.id.btnShare);

    }
    void initGlobal(){
        mSharedPref = getSharedPreferences(MyConstants.KEY_SHARED_PREF, Context.MODE_PRIVATE);
        mEditor = mSharedPref.edit();
        userName = mSharedPref.getString(MyConstants.KEY_USERNAME, "");
    }

     void showResult(final boolean success){
        Handler mainHandler = new Handler(this.getMainLooper());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {

                if(progressDialog.isShowing()){
                    progressDialog.cancel();
                }

                rlAfter.setVisibility(View.VISIBLE);
                rlBefore.setVisibility(View.GONE);
                tvText.setText("Thank you for Amplifying! Your share link is: \n" + shortLink);

                if (success){
                    tvUrl.setText("Upload success!");
                }else{
                    tvUrl.setText("Upload failed!");
                }
            } // This is your code
        };
        mainHandler.post(myRunnable);
    }
    String getVersion(){
        /*
        String version = "";
        PackageManager manager = getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(
                    getPackageName(), 0);
            version = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
        */

        return BuildConfig.VERSION_NAME;
    }
    String getIp(){
        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        return ip;
    }

    void callRequestBin(final String message, final String amp){

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String url = MyConstants.REQUESTBIN_URL;

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(url);

                try {

                    // Add your data
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("userName", userName));
                    nameValuePairs.add(new BasicNameValuePair("targetURL", message));
                    nameValuePairs.add(new BasicNameValuePair("userIP", getIp()));
                    nameValuePairs.add(new BasicNameValuePair("value", amp));
                    nameValuePairs.add(new BasicNameValuePair("referrer", "TBD"));
                    nameValuePairs.add(new BasicNameValuePair("shareLink", "TBD"));
                    nameValuePairs.add(new BasicNameValuePair("version", getVersion()));

                    HttpEntity entity = new UrlEncodedFormEntity(nameValuePairs);
                    httppost.setEntity(entity);

                    HttpResponse result = httpclient.execute(httppost);
//                    Log.i(TAG, getIp());

//                    final String strResponse = EntityUtils.toString(result.getEntity());
//
//                    try{
//                        JSONArray json = new JSONArray(strResponse);
//                    Log.i(TAG, result.toString());
//                    if (result.toString().equals("ok")) {
                    if(!shortLink.equals(""))  showResult(true);
//                    }

//                    }
//                    catch (JSONException e)
//                    {
//                        Log.e("error", e.toString());
//                        tvUrl.setText("Upload failed! Reason: " + e.toString());
//                    }

//                } catch (ClientProtocolException e) {
//                    Log.e("Response", e.toString());

//                    showResult(false);



                } catch (IOException e) {
                    Log.e("IOException", e.toString());

                    showResult(false);


                }

            }
        });
    }
    void callSniply(final String message, final String amp){

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String url = MyConstants.SNIPLY_URL;

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(url);


                httppost.addHeader("Authorization", "Bearer " + MyConstants.SNIPLY_KEY);
                httppost.addHeader("Content-Type", "application/json");
                httppost.addHeader("HOST", "snip.ly");


                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.accumulate("url", message);
                    jsonObject.accumulate("campaign", MyConstants.SNIPLY_CAMPAIGN);
                    String json = jsonObject.toString();
                    httppost.setEntity(new StringEntity(json, "UTF-8"));
                    HttpResponse result = httpclient.execute(httppost);

                    String strResponse = EntityUtils.toString(result.getEntity());
                    JSONObject joResponse = new JSONObject(strResponse);
                    shortLink = joResponse.getString("href");


                    if(!shortLink.equals(""))  showResult(true);

                } catch (Exception e) {
                    e.printStackTrace();
                    showResult(false);
                }


            }
        });
    }
}
