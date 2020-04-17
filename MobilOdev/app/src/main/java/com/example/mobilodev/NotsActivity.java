package com.example.mobilodev;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class NotsActivity extends AppCompatActivity {
    EditText notsText;
    Spinner notsSpinner;
    ArrayList<String> notsData = new ArrayList<>();
    ArrayAdapter<String> notsAdapter;
    Button saveButton,updateButton,deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nots);
        String username=getIntent().getStringExtra("username");
        final String filePath=username+".txt";
        notsText=findViewById(R.id.nots_Text);
        saveButton=findViewById(R.id.saveNots_button);
        updateButton=findViewById(R.id.updateNots_button);
        deleteButton=findViewById(R.id.deleteNots_button);
        notsSpinner=findViewById(R.id.nots_spinner);

        try {
            FileInputStream fi=openFileInput(filePath);
            InputStreamReader isr= new InputStreamReader(fi);
            BufferedReader reader= new BufferedReader(isr);
            String line= "";
            notsData.add(line);
            while((line=reader.readLine()) != null){
                notsData.add(line);
            }
            reader.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        notsAdapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1, notsData);
        notsSpinner.setAdapter(notsAdapter);
        notsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String choosenNot =notsData.get(position);
                notsText.setText(choosenNot);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                notsText.setText("");
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                notsData.add(notsText.getText().toString());
                try {
                    FileOutputStream fo=openFileOutput(filePath, MODE_PRIVATE);
                    OutputStreamWriter writer=new OutputStreamWriter(fo);
                    StringBuilder sb = new StringBuilder();
                    for (String s : notsData)
                    {
                        if(!"".equals(s)){
                            sb.append(s);
                            sb.append("\n");
                        }
                    }
                    writer.write(sb.toString());
                    notsText.setText("");
                    Toast.makeText(getApplicationContext(), "Yeni not eklendi", Toast.LENGTH_LONG).show();
                    writer.close();

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int selectedPosition = notsSpinner.getSelectedItemPosition();
                notsData.set(selectedPosition,notsText.getText().toString());
                try {
                    FileOutputStream fo=openFileOutput(filePath, MODE_PRIVATE);
                    OutputStreamWriter writer=new OutputStreamWriter(fo);
                    StringBuilder sb = new StringBuilder();
                    for (String s : notsData)
                    {
                        if(!"".equals(s)){
                            sb.append(s);
                            sb.append("\n");
                        }

                    }
                    writer.write(sb.toString());
                    notsText.setText("");
                    Toast.makeText(getApplicationContext(), "Not güncellendi", Toast.LENGTH_LONG).show();
                    writer.close();

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int selectedPosition = notsSpinner.getSelectedItemPosition();
                notsData.remove(selectedPosition);
                try {
                    FileOutputStream fo=openFileOutput(filePath, MODE_PRIVATE);
                    OutputStreamWriter writer=new OutputStreamWriter(fo);
                    StringBuilder sb = new StringBuilder();
                    for (String s : notsData)
                    {
                        if(!"".equals(s)){
                            sb.append(s);
                            sb.append("\n");
                        }
                    }
                    writer.write(sb.toString());
                    notsText.setText("");
                    Toast.makeText(getApplicationContext(), "Not silindi", Toast.LENGTH_LONG).show();

                    writer.close();

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });

    }



}
