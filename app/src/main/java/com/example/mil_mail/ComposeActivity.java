package com.example.mil_mail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ComposeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
    }

    public void sendButtonTapped(View view) {
        EditText subjectText = findViewById(R.id.editTextTextPersonName3);
        EditText receivers = findViewById(R.id.editTextTextPersonName2);
        EditText mailBody = findViewById(R.id.editTextTextMultiLine);

        String[] address = new String[1];
        address[0] = receivers.getText().toString();
        if (subjectText.getText().toString().equals("")) {
            Toast.makeText(this,"subject field can not be empty",Toast.LENGTH_LONG).show();
        }
        else {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.putExtra(Intent.EXTRA_EMAIL, address);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subjectText.getText().toString());
            emailIntent.putExtra(Intent.EXTRA_TEXT, mailBody.getText().toString());
            if (emailIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(emailIntent);
            }
            else {
                Toast.makeText(this,"An error",Toast.LENGTH_LONG).show();
            }
        }
    }

}