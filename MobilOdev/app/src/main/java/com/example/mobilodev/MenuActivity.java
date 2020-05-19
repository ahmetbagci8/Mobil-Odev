package com.example.mobilodev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        final Button emailButton = (Button) findViewById(R.id.email_button);
        final Button userListButton = (Button) findViewById(R.id.userList_button);
        final Button settingsButton = (Button) findViewById(R.id.userSettings_button);
        final Button notsButton = (Button) findViewById(R.id.notes_button);
        final Button sensorsButton = (Button) findViewById(R.id.sensors_button);
        final Button asyncButton = (Button) findViewById(R.id.asyncTask_button);
        final Button alarmButton = (Button) findViewById(R.id.alarm_button);
        final Button locationButton = (Button) findViewById(R.id.location_button);
        final TextView welcomeMessage=findViewById(R.id.welcome_text);
        final String username=getIntent().getStringExtra("username");
        String temp="Merhaba "+username+" uygulamama hoşgeldin";
        welcomeMessage.setText(temp);
        emailButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent emailIntent =new Intent(MenuActivity.this,EmailActivity.class);
                startActivity(emailIntent);
            }
        });
        userListButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent listIntent =new Intent(MenuActivity.this,ListActivity.class);
                startActivity(listIntent);
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent settingsIntent =new Intent(MenuActivity.this,SettingsActivity.class);
                settingsIntent.putExtra("username",username);
                startActivity(settingsIntent);
            }
        });

        notsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent notsIntent =new Intent(MenuActivity.this,NotsActivity.class);
                notsIntent.putExtra("username",username);
                startActivity(notsIntent);
            }
        });
        sensorsButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent sensorsIntent =new Intent(MenuActivity.this,SensorActivity.class);
                startActivity(sensorsIntent);
            }
        });
        asyncButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent asyncIntent =new Intent(MenuActivity.this,AsyncActivity.class);
                startActivity(asyncIntent);
            }
        });
        alarmButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent alarmIntent =new Intent(MenuActivity.this,AlarmActivity.class);
                startActivity(alarmIntent);
            }
        });
        locationButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent locationIntent =new Intent(MenuActivity.this,LocationActivity.class);
                startActivity(locationIntent);
            }
        });

    }

}
