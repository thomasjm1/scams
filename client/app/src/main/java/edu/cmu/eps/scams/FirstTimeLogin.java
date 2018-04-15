package edu.cmu.eps.scams;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Random;


public class FirstTimeLogin extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_login);

        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button2);


        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        SessionManagement firstLogin = new SessionManagement(this);

        // Generate a random user ID for the first time login
        String SALTCHARS1 = "1234567890";
        StringBuilder salt1 = new StringBuilder();
        Random rnd = new Random();
        while (salt1.length() < 12) {
            int index = (int) (rnd.nextFloat() * SALTCHARS1.length());
            salt1.append(SALTCHARS1.charAt(index));
        }
        String userid = salt1.toString();

        // Generate a random qrCode string for the first time login
        String SALTCHARS2 = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder salt2 = new StringBuilder();
        rnd = new Random();
        while (salt2.length() < 18) {
            int index = (int) (rnd.nextFloat() * SALTCHARS2.length());
            salt2.append(SALTCHARS2.charAt(index));
        }
        String qr = salt2.toString();

        String usertype="";


        switch (view.getId()) {
            case R.id.button1:
                usertype = "Reviewer";

                firstLogin.createLoginSession(userid, usertype, qr);
                Intent intent1 = new Intent(FirstTimeLogin.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.button2:
                usertype = "Primary User";

                firstLogin.createLoginSession(userid, usertype, qr);
                // Change it to another UI activity interface designed for the other type of user
                Intent intent2 = new Intent(FirstTimeLogin.this, MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.button3:
                firstLogin.clearLoginSession();
                break;
        }

    }

}
