package com.example.mobilodev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {
    Spinner langSpinner;
    ArrayList<String> languages = new ArrayList<>();
    ArrayAdapter<String> languageAdapter;
    RadioButton maleButton,femaleButton;
    Switch darkModeSwitch;
    EditText ageText,weightText,heightText;
    TextView userNameText;
    Button saveButton;
    View sensorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sensorView=findViewById(R.id.settings_background);
        String username=getIntent().getStringExtra("username");
        userNameText=findViewById(R.id.username_Text);
        userNameText.setText(username);
        final SharedPreferences sp = getSharedPreferences(username, MODE_PRIVATE);
        String gender = sp.getString("gender", "Male");
        String language = sp.getString("language", "Turkish");
        String app_theme = sp.getString("app-theme", "light");
        String age = sp.getString("age","20");
        String weight = sp.getString("weight","70");
        String height = sp.getString("height","170");

        langSpinner=findViewById(R.id.language_spinner);
        languages.add("Turkish");
        languages.add("English");
        languageAdapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1, languages);
        langSpinner.setAdapter(languageAdapter);
        if(language.equals("Turkish")){
            langSpinner.setSelection(0);
        }
        else{
            langSpinner.setSelection(1);
        }
        maleButton = findViewById(R.id.male_radioButton);
        femaleButton = findViewById(R.id.female_radioButton);
        if(gender.equals("Male")){
            maleButton.setChecked(true);
            femaleButton.setChecked(false);
        }
        else{
            maleButton.setChecked(false);
            femaleButton.setChecked(true);
        }
        darkModeSwitch=findViewById(R.id.darkModeSwitch);
        if(app_theme.equals("light")){
            darkModeSwitch.setChecked(false);
            sensorView.setBackgroundColor(Color.WHITE);
        }
        else{
            darkModeSwitch.setChecked(true);
            sensorView.setBackgroundColor(0xFF383131);
        }
        ageText=findViewById(R.id.age_Text);
        weightText=findViewById(R.id.weight_Text);
        heightText=findViewById(R.id.height_Text);
        ageText.setText(age);
        weightText.setText(weight);
        heightText.setText(height);
        saveButton=findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                SharedPreferences.Editor editor = sp.edit();

                if(maleButton.isChecked()==true){
                    editor.putString("gender", "Male");
                }
                if(femaleButton.isChecked()==true){
                    editor.putString("gender", "Female");
                }
                int selectedPosition = langSpinner.getSelectedItemPosition();
                if(selectedPosition==0){
                    editor.putString("language","Turkish");
                }
                else{
                    editor.putString("language","English");
                }
                if(darkModeSwitch.isChecked()==true){
                    editor.putString("app-theme","dark");
                    sensorView.setBackgroundColor(0xFF383131);
                }
                else{
                    editor.putString("app-theme","light");
                    sensorView.setBackgroundColor(Color.WHITE);
                }
                editor.putString("age",ageText.getText().toString());
                editor.putString("weight",weightText.getText().toString());
                editor.putString("height",heightText.getText().toString());
                editor.commit();
                Toast.makeText(getApplicationContext(), "Kaydedildi", Toast.LENGTH_LONG).show();
            }
        });
    }
}