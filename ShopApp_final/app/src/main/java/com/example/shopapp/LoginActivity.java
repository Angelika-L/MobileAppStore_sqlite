package com.example.shopapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity {

    private EditText login, haslo_rejestracja, haslo_logowanie, login_logowanie;
    private Button buttonAddUser,buttonLogin ;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private ProgressDialog loading;
    private DatabaseReference myRef;
    List<ListaItem> lp = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //z wykładów:
        final ListView listView = findViewById(R.id.ListView_user);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        listView.setAdapter(adapter);
        final FirebaseDatabase firebaseDB = FirebaseDatabase.getInstance();
        myRef = firebaseDB.getReference("Person");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @NonNull String previousChildName) {
                adapter.add(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @NonNull String previousChildName) {
                adapter.remove(previousChildName);
                adapter.add(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                adapter.remove(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @NonNull String previousChildName) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("TAG", "Failed to read value.", databaseError.toException());
            }
        });


        login = findViewById(R.id.login_rejestracja);
        haslo_rejestracja = findViewById(R.id.haslo_rejestracja);
        buttonAddUser = findViewById(R.id.button_rejestracja);
        buttonAddUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatabaseReference childRef = myRef.push();
                childRef.setValue(login.getText().toString());
                createNewUser();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Query myQuery = myRef.orderByValue().equalTo((String) listView.getItemAtPosition(position));
                myQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                            firstChild.getRef().removeValue();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

        buttonLogin = findViewById(R.id.buttonLogin);
        login_logowanie = findViewById(R.id.login_logowanie);
        haslo_logowanie = findViewById(R.id.haslo_logowanie);
        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    //moje rejestracja uzytkowaników authentication:
    private void createNewUser() {
        loading = new ProgressDialog(this);
        String loginName = login.getText().toString();
        String password = haslo_rejestracja.getText().toString();
        if (TextUtils.isEmpty(loginName)) {
            Toast.makeText(this, "Nie podano nazwy loginu.", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Nie podano hasła.", Toast.LENGTH_SHORT).show();
        } else {
            loading.setTitle("Tworzenie nowego konta");
            loading.setMessage("Proszę czekać");
            loading.setCanceledOnTouchOutside(true);
            loading.show();
            mAuth.createUserWithEmailAndPassword(loginName, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String currentUserId = mAuth.getCurrentUser().getUid();
                        //myRef.child("User").child(currentUserId).setValue("");
                        Toast.makeText(LoginActivity.this, "Dodano nowego użytkownika", Toast.LENGTH_LONG).show();
                        loading.dismiss();
                        login.setText("");
                        haslo_rejestracja.setText("");
                    } else {
                        String message = task.getException().toString();
                        Toast.makeText(LoginActivity.this, "Błąd: " + message, Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }
                }
            });
        }
    }

    //moje logowanie uzytkowaników authentication:
    private void loginUser() {
        loading = new ProgressDialog(this);
        String loginName = login_logowanie.getText().toString();
        String password = haslo_logowanie.getText().toString();

        if(TextUtils.isEmpty(loginName)){
            Toast.makeText(this, "Nie podano LOGINU", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Nie podano HASLA", Toast.LENGTH_SHORT).show();
        }
        else {
            loading.setTitle("Trwa logowanie");
            loading.setMessage("Proszę czekać");
            loading.setCanceledOnTouchOutside(true);
            loading.show();
            mAuth.signInWithEmailAndPassword(loginName,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent loginMain = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(loginMain);
                        Toast.makeText(LoginActivity.this, "Logowanie zakończone sukcesem.", Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                        FirebaseUser user = mAuth.getCurrentUser();
                    }
                    else{
                        String message = task.getException().toString();
                        Toast.makeText(LoginActivity.this, "Błąd: " + message, Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }
                }
            });
        }
    }

}
