package com.dr.sharingtest.activities;

import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dr.sharingtest.R;
import com.dr.sharingtest.adaptors.AmplificationListAdapter;
import com.dr.sharingtest.model.Amplification;
import com.dr.sharingtest.model.DataAccess;
import com.dr.sharingtest.utils.MyConstants;

import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

//This is a test line to check sync.

public class MainActivity extends AppCompatActivity {

    EditText etUserName;
    Button btnApply;
    RelativeLayout rlUserName, rlListView;
    ListView lvAmplifications;


    SharedPreferences mSharedPref;
    SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //hide status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        initGlobal();
        initView();

//        Intent intent = new Intent(MainActivity.this, ReceiverActivity.class);
//        startActivity(intent);
//        MainActivity.this.finish();
    }

    void initGlobal(){
        mSharedPref = getSharedPreferences(MyConstants.KEY_SHARED_PREF, Context.MODE_PRIVATE);
        mEditor = mSharedPref.edit();
    }

    void initView(){

        etUserName = (EditText) findViewById(R.id.etUserName);
        btnApply = (Button) findViewById(R.id.btnApply);
        rlListView = (RelativeLayout)findViewById(R.id.rlListView);
        rlUserName = (RelativeLayout)findViewById(R.id.rlUserName);
        lvAmplifications = (ListView)findViewById(R.id.lvAmplifications);
        lvAmplifications.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                News news = arrNews.get(position);
//                Intent intent = new Intent(NewsActivity.this, DetailNewsActivity.class);
//                DetailNewsActivity.news = news;
//                startActivity(intent);
                String str = String.format(Locale.US, "Amplification %d selected", position);
                Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
        setListView();

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etUserName.getText().toString().trim();
                mEditor.putString(MyConstants.KEY_USERNAME, userName);
                mEditor.commit();

                Toast.makeText(MainActivity.this, "User name saved!", Toast.LENGTH_LONG).show();

                rlListView.setVisibility(View.VISIBLE);
                rlUserName.setVisibility(View.GONE);
            }
        });

        String userName = mSharedPref.getString(MyConstants.KEY_USERNAME, "");

        if (userName.equals("")){ //shows user name layout
            rlListView.setVisibility(View.GONE);
            rlUserName.setVisibility(View.VISIBLE);
        }else{ //shows list view layout
            rlListView.setVisibility(View.VISIBLE);
            rlUserName.setVisibility(View.GONE);
        }
        etUserName.setText(userName);


    }

    void setListView(){

        Realm realm = Realm.getInstance(this);
        RealmResults<Amplification> results =
                realm.where(Amplification.class)
                .findAll();

        DataAccess dataAccess = new DataAccess();
        for (Amplification amplification:results){
            DataAccess.amplifications.add(amplification);
        }

        AmplificationListAdapter listAdapter = new AmplificationListAdapter(MainActivity.this, DataAccess.amplifications);
        lvAmplifications.setAdapter(listAdapter);
    }

}
