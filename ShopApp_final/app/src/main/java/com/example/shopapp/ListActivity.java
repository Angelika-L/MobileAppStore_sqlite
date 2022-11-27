package com.example.shopapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.shopapp.ListaItem.ListaItemEntry;

public class ListActivity extends Activity {

    private RecyclerView irv;
    private EditText strNameProdact, strNamePrice, NameQuantity;
    private TextView LoginZalogowanego1, wyloguj1;
    private Button ButtonZapiszProdukt;
    private MyAdapter mAdapter;
    public DBHelper mDatabaseHelper;
    public SQLiteDatabase mDatabase;
    public List<ListaItem> lp = new ArrayList<>();

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase databaseFA;
    private DatabaseReference ref, ref_num, string_dr, string_dr_lista;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

//Firebase:
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

//kto jest zalogowany?:
        String email = currentUser.getEmail();
        if (currentUser != null) {
            LoginZalogowanego1 = findViewById(R.id.LoginZalogowanego1);
            LoginZalogowanego1.setText(email);
        }

// Pobranie referencji do bazy danych
        databaseFA = FirebaseDatabase.getInstance();
        ref = databaseFA.getReference("ListaItem");
        ref_num = databaseFA.getReference("objects/numbers");

//odczyt:
        DatabaseReference lpr = databaseFA.getReference("ListaItem/" + currentUser.getUid());
        lpr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.getKey();
                    for (int j = 0; j < lp.size(); j++) {
                        String x = lp.get(j).getName();
                        if (name.equals(x)) {
                            lp.remove(j);
                        }
                    }
                    Log.e("lista-app", ds.getKey());
                    lp.add(new ListaItem(ds.getKey(),
                            ds.child("Cena").getValue(Integer.class),
                            ds.child("Ilość").getValue(Integer.class),
                            ds.child("czy kupiony?").getValue(Boolean.class),
                            ds.child("Id").getValue(String.class)));
                }
                for (int i = 0; i < lp.size(); i++) {
                    Log.i("lista-app", lp.get(i).toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ListActivity.this, "błąd", Toast.LENGTH_SHORT).show();
            }
        });

        strNameProdact = findViewById(R.id.NazwaProduktuEditeText);
        strNamePrice = findViewById(R.id.CenaEditeText);
        NameQuantity = findViewById(R.id.IloscEditeText);
        mDatabaseHelper = new DBHelper(this);
        mDatabase = mDatabaseHelper.getWritableDatabase();

        irv = findViewById(R.id.recyclerView);
        irv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter(this, lp);
        irv.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((String) viewHolder.itemView.getTag());
                //mAdapter.swapCouror(lp);
            }
        }).attachToRecyclerView(irv);

        ButtonZapiszProdukt = findViewById(R.id.ButtonZapiszProdukt);
        ButtonZapiszProdukt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = strNameProdact.getText().toString();
                String price = strNamePrice.getText().toString();
                String quantity = NameQuantity.getText().toString();

                insertNewProduct(name, Integer.valueOf(price), Integer.valueOf(quantity));

                //addItem(name, Integer.valueOf(price), Integer.valueOf(quantity));
                mysendbroadcast(name, price, quantity);
                //mAdapter.swapCouror(lp);
            }
        });
//        mAdapter.swapCouror(getAllItems());
    }

    //wstawianie produktów do bazy Firebase dla zalogowanego user'a:
    private void insertNewProduct(String name, int price, int quantity) {
        String user = currentUser.getUid();
        HashMap<String, Object> ls = new HashMap<>();

        if (!name.isEmpty() && currentUser != null) {
            string_dr = ref.child(user).child(name);
            String id = ref.push().getKey();
            ls.put("Cena", price);
            ls.put("Ilość", quantity);
            ls.put("czy kupiony?", false);
            ls.put("Id", id);
            string_dr.setValue(ls);

            Toast.makeText(this, "Produkt dodany do bazy", Toast.LENGTH_LONG).show();
            strNameProdact.setText("");
            strNamePrice.setText("");
            NameQuantity.setText("");
        } else {
            Toast.makeText(this, "Nie podano nazwy produktu!", Toast.LENGTH_LONG).show();
        }
        mAdapter.swapCouror(lp);
    }

    //usuwanie produktow w firebase
    public void removeItem(String name) {
        String user = currentUser.getUid();
        string_dr = ref.child(user).child(name);
        @SuppressLint("RestrictedApi") String path = String.valueOf(string_dr.getPath());
        databaseFA.getReference(path).removeValue();
        DatabaseReference lpr = databaseFA.getReference("ListaItem/" + currentUser.getUid());


        for (int j = 0; j < lp.size(); j++) {
            String x = lp.get(j).getName();
            if (name.equals(x)) {
                lp.remove(j);
            }
        }
       // mAdapter.swapCouror(lp);
    }


    private void addItem(String name, int price, int quantity) {
        if (strNameProdact.getText().toString().trim().length() == 0) {
            return;
        }
        ContentValues cv = new ContentValues();
        cv.put(ListaItemEntry.COL_NAME, name);
        cv.put(ListaItemEntry.COL_PRICE, price);
        cv.put(ListaItemEntry.COL_QUANTITY, quantity);
        cv.put(ListaItem.ListaItemEntry.COL_CHB_SALE, 0);
        mDatabase.insert(ListaItemEntry.TABLE_NAME, null, cv);
        strNameProdact.getText().clear();
        strNamePrice.getText().clear();
        NameQuantity.getText().clear();
        //mAdapter.swapCouror(getAllItems());
    }

    public Cursor getAllItems() {
        return mDatabase.query(
                ListaItemEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ListaItemEntry.COL_TIMESTAMP + " DESC"
        );
    }

    public void toastMassage(String massage) {
        Toast.makeText(this, massage, Toast.LENGTH_SHORT).show();
    }

    public void clickClean(View view) {
        strNameProdact.setText("");
        strNamePrice.setText("");
        NameQuantity.setText("");
        toastMassage("Wyczyszczono dane.");
    }

    //intent rozgłoszeniowy
    public void mysendbroadcast(String name, String price, String quantity) {
        Intent intent = new Intent();
        intent.setAction("com.example.second_appshop.EXAMPLE");
        intent.putExtra("com.example.second_appshop.name", name);
        intent.putExtra("com.example.second_appshop.price", price);
        intent.putExtra("com.example.second_appshop.quantity", quantity);
        sendBroadcast(intent, "android.permission.permis1");
    }

    public void wylogujClick(View view){
        wyloguj1 = findViewById(R.id.wyloguj1);
        mAuth.signOut();
        Intent intentLog = new Intent(this, LoginActivity.class);    //przechodzenie do "listy" Activity
        startActivity(intentLog);
        Toast.makeText(ListActivity.this,"Zotałeś wylogowany", Toast.LENGTH_SHORT).show();
    }

}




