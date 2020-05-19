package com.example.mobilodev;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class AsyncActivity extends AppCompatActivity {
    ProgressBar progressBar;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);
        progressBar = findViewById(R.id.progressBar);
        image = findViewById(R.id.imageAsync);
        image.setVisibility(View.INVISIBLE);
        progressBar.setIndeterminate(false);
        progressBar.setProgress(0);
        progressBar.setMax(1000);

    }
    public void start(View view){
        AsyncTask task = new AsyncTask();
        task.setProgressBar(progressBar);
        task.execute(1000);
    }
    public class AsyncTask extends android.os.AsyncTask<Integer, Integer, String> {
        ProgressBar pb;
        int status = 0;
        public void setProgressBar(ProgressBar progressBar) {
            this.pb = progressBar;
        }

        protected String doInBackground(Integer[] objects) {
            while(status<objects[0]){
                int random = new Random().nextInt(300);
                status+=random;
                try {
                    publishProgress(status);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            final Ringtone ringtone = RingtoneManager.getRingtone(getBaseContext(), alarmUri);
            ringtone.play();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    ringtone.stop();
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, 5000);
            return null;
        }

        protected void onProgressUpdate(Integer[] values) {
            pb.setProgress(values[0]);
            super.onProgressUpdate(values);
        }

        protected void onPreExecute() {
            super.onPreExecute();
            image.setVisibility(View.INVISIBLE);
        }

        protected void onPostExecute(String result) {
            image.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "İndirme tamamlandı", Toast.LENGTH_LONG).show();
            super.onPostExecute(result);
        }
    }
}
