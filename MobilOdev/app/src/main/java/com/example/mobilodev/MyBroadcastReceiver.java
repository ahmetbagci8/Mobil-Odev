package com.example.mobilodev;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;

public class MyBroadcastReceiver extends BroadcastReceiver {
    ArrayList<String> data = new ArrayList<>();
    String number,content;
    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar datetime = Calendar.getInstance();
        int hour=datetime.get(Calendar.HOUR_OF_DAY);
        int minute=datetime.get(Calendar.MINUTE);
        int day=datetime.get(Calendar.DAY_OF_MONTH);
        int month=datetime.get(Calendar.MONTH);
        month+=1;
        int year=datetime.get(Calendar.YEAR);
        String date=String.format("%02d/%02d/%04d",day,month,year);
        String time=String.format("%02d:%02d", hour,minute);
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        try {
            FileInputStream fi=context.openFileInput("logs.txt");
            InputStreamReader isr= new InputStreamReader(fi);
            BufferedReader reader= new BufferedReader(isr);
            String line= "";
            //data.add(line);
            while((line=reader.readLine()) != null){
                data.add(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            if(state != null && state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Toast.makeText(context, number  + " " + date + " " +time, Toast.LENGTH_LONG).show();
              try {
                    FileOutputStream fo=context.openFileOutput("logs.txt", context.MODE_PRIVATE);
                    OutputStreamWriter writer=new OutputStreamWriter(fo);
                    StringBuilder sb = new StringBuilder();
                    data.add(number + " " + date + " " +time);
                    for (String s : data)
                    {
                        if(!"".equals(s)){
                            sb.append(s);
                            sb.append("\n");
                        }
                    }
                    writer.write(sb.toString());
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(intent.getAction() == "android.provider.Telephony.SMS_RECEIVED"){
                Bundle dataBundle = intent.getExtras();
                if(dataBundle != null){
                    Object[] pdu = (Object[])dataBundle.get("pdus");
                    final SmsMessage[] message = new SmsMessage[pdu.length];
                    for(int i = 0;i <pdu.length;i++){
                            String format = dataBundle.getString("format");
                            message[i] = SmsMessage.createFromPdu((byte[])pdu[i], format);
                        content = message[i].getMessageBody();
                        number = message[i].getOriginatingAddress();
                    }
                    try {
                        FileOutputStream fo=context.openFileOutput("logs.txt", context.MODE_PRIVATE);
                        OutputStreamWriter writer=new OutputStreamWriter(fo);
                        StringBuilder sb = new StringBuilder();
                        data.add(number + " " + content + " " + date + " " +time);
                        for (String s : data)
                        {
                            if(!"".equals(s)){
                                sb.append(s);
                                sb.append("\n");
                            }
                        }
                        writer.write(sb.toString());
                        Toast.makeText(context, number + " " + content + " " + date + " " +time, Toast.LENGTH_LONG).show();
                        writer.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        catch (Exception e) {

            e.printStackTrace();
        }

    }
}
