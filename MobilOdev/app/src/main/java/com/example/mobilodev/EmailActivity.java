package com.example.mobilodev;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EmailActivity extends AppCompatActivity {
    Uri URI = null;
    int columnIndex;
    String attachmentFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        final EditText to = (EditText) findViewById(R.id.to_edittext);
        final EditText subject = (EditText) findViewById(R.id.subject_edittext);
        final EditText content = (EditText) findViewById(R.id.content_edittext);
        final Button sendButton = (Button) findViewById(R.id.sendEmail_button);
        final Button attachmentButton = findViewById(R.id.attachment_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendEmail(to.getText().toString(), subject.getText().toString(), content.getText().toString());
            }
        });
        attachmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent attachmentIntent = new Intent(Intent.ACTION_GET_CONTENT);
                attachmentIntent.setType("*/*");
                attachmentIntent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(attachmentIntent, "Dosya Eki eklendi"), 100);
            }
        });
    }

    private void sendEmail(String mail, String subject, String content) {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mail});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        if (URI != null) {
            intent.putExtra(Intent.EXTRA_STREAM, URI);
        }
        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            URI = data.getData();
            Toast.makeText(getApplicationContext(), "Dosya Eki Eklendi:"+ URI.getLastPathSegment(), Toast.LENGTH_LONG).show();

        }


    }
}
