package com.example.shopapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends Activity {

    TextView wyloguj1, LoginZalogowanego;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
//        currentUser = mAuth.getCurrentUser();

//        String email = currentUser.getEmail();
//        if (currentUser != null) {
//            LoginZalogowanego = findViewById(R.id.LoginZalogowanego);
//            LoginZalogowanego.setText(email);
//        }
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.button:
                Intent intentL = new Intent(this, ListActivity.class);    //przechodzenie do "listy" Activity
                startActivity(intentL);
                break;
            case R.id.button2:
                Intent intentS = new Intent(this, SetActivity.class);     ///przechodzenie do "opcji" Activity
                startActivity(intentS);
                break;
            case R.id.button3:
                Intent intentLogin = new Intent(this, LoginActivity.class);    //przechodzenie do "user" Activity
                startActivity(intentLogin);
                break;
        }
    }

    public void wylogujClick(View view){
        wyloguj1 = findViewById(R.id.wyloguj1);
        mAuth.signOut();
        Intent intentLog = new Intent(this, LoginActivity.class);    //przechodzenie do "listy" Activity
        startActivity(intentLog);
        Toast.makeText(MainActivity.this,"Zotałeś wylogowany", Toast.LENGTH_SHORT).show();
    }
}