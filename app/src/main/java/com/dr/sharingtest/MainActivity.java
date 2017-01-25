package com.dr.sharingtest;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dr.sharingtest.utils.MyConstants;

import java.util.ArrayList;

//This is a test line to check sync.

public class MainActivity extends AppCompatActivity {

    EditText etUserName;
    Button btnApply;


    SharedPreferences mSharedPref;
    SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUserName = (EditText) findViewById(R.id.etUserName);
        btnApply = (Button) findViewById(R.id.btnApply);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etUserName.getText().toString().trim();
                mEditor.putString(MyConstants.KEY_USERNAME, userName);
                mEditor.commit();

                Toast.makeText(MainActivity.this, "User name saved!", Toast.LENGTH_LONG).show();
            }
        });

        mSharedPref = getSharedPreferences(MyConstants.KEY_SHARED_PREF, Context.MODE_PRIVATE);
        mEditor = mSharedPref.edit();
        String userName = mSharedPref.getString(MyConstants.KEY_USERNAME, "");
        etUserName.setText(userName);


//        Intent intent = new Intent(MainActivity.this, ReceiverActivity.class);
//        startActivity(intent);
//        MainActivity.this.finish();
    }

}
