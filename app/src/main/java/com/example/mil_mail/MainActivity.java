package com.example.mil_mail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void buttonTapped(View view) {
        EditText editTextUser = findViewById(R.id.editTextTextPersonName);
        EditText editTextPass = findViewById(R.id.editTextTextPassword);
        String username = editTextUser.getText().toString();
        String password = editTextPass.getEditableText().toString();
        Gonnect.getData("http://spneshaei.com/mil/getEmails.php?username="
                + username + "&password=" + password, this, new Gonnect.ResponseSuccessListener() {
            @Override
            public void responseRecieved(final String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.equals("invalid-user")) {
                            Toast.makeText(MainActivity.this, "invalid user", Toast.LENGTH_LONG).show();
                        }
                        else {
                            ArrayList<Email> newEmails = new Gson().fromJson(response,new TypeToken<ArrayList<Email>>() {

                            }.getType());
                            Email.getAllEmails().clear();
                            Email.getAllEmails().addAll(newEmails);
                            Intent intent = new Intent(MainActivity.this,EmailListActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        }, new Gonnect.ResponseFailureListener() {
            @Override
            public void responseFailed(IOException exception) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Error in Loading", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    public void hideByClick(View view) {
        EditText username = findViewById(R.id.editTextTextPersonName);
        username.setText("");
        username.setTextColor(Color.parseColor("#000000"));
    }

    public void composeButtonTapped(View view) {
        Intent intent = new Intent(this,ComposeActivity.class);
        startActivity(intent);
    }
}