package com.example.mobilodev;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {
    Switch alarmSwitch;
    Button saveButton;
    EditText timeText;
    Calendar alarmTime,datetime;
    int hour,minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        alarmSwitch=findViewById(R.id.alarmSwitch);
        saveButton=findViewById(R.id.alarmKaydet_Button);
        timeText=findViewById(R.id.timeText);
        datetime = Calendar.getInstance();
        hour=datetime.get(Calendar.HOUR_OF_DAY);
        minute=datetime.get(Calendar.MINUTE);
        String timeNow=String.format("%02d:%02d", hour,minute);
        String time;
        final SharedPreferences sp = getSharedPreferences("alarm", MODE_PRIVATE);
        String isWorking = sp.getString("isWorking", "false");
        if(isWorking.equals("true")){
            time = sp.getString("time", timeNow);
            alarmSwitch.setChecked(true);
        }
        else{
            time=timeNow;
            alarmSwitch.setChecked(false);
        }
        timeText.setText(time);

        timeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alarmTime= Calendar.getInstance();
                int hour = datetime.get(Calendar.HOUR_OF_DAY);
                int minute = datetime.get(Calendar.MINUTE);
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(AlarmActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeText.setText( String.format("%02d:%02d", selectedHour, selectedMinute));
                        alarmTime.set(Calendar.HOUR_OF_DAY, selectedHour);
                        alarmTime.set(Calendar.MINUTE, timePicker.getMinute());
                        alarmTime.set(Calendar.SECOND, 0);
                        alarmTime.set(Calendar.MILLISECOND, 0);

                    }
                }, hour, minute, true);
                Calendar timeNow= Calendar.getInstance();
                if(timeNow.getTimeInMillis()<alarmTime.getTimeInMillis()){
                    alarmTime.add(Calendar.DATE, 1);
                }
                timePicker.setTitle("Saat Seçiniz");
                timePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ayarla", timePicker);
                timePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", timePicker);
                timePicker.show();

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                SharedPreferences.Editor editor = sp.edit();

                if(alarmSwitch.isChecked()==true){
                    editor.putString("isWorking","true");
                    editor.putString("time",timeText.getText().toString());

                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(getBaseContext(), AlarmHandler.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 1, intent, 0);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP,alarmTime.getTimeInMillis(),pendingIntent);
                    Toast.makeText(getApplicationContext(), "Alarm kuruldu", Toast.LENGTH_LONG).show();
                    //Toast.makeText(getApplicationContext(), alarmTime.get(Calendar.DAY_OF_MONTH) + " " + alarmTime.get(Calendar.MONTH) + " " + alarmTime.get(Calendar.YEAR) + " " +alarmTime.get(Calendar.HOUR_OF_DAY) + " " + alarmTime.get(Calendar.MINUTE), Toast.LENGTH_LONG).show();
                }
                else{
                    editor.putString("isWorking","false");
                    editor.putString("time"," ");

                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(getBaseContext(), AlarmHandler.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 1, intent, 0);
                    alarmManager.cancel(pendingIntent);
                    Toast.makeText(getApplicationContext(), "Alarm iptal edildi", Toast.LENGTH_LONG).show();
                }

                editor.commit();
            }
        });



    }
}
