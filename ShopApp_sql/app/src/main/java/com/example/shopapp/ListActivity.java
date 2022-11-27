package com.example.shopapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {


    private RecyclerView irv;
    private EditText strNameProdact;
    private EditText strNamePrice;
    private EditText NameQuantity;

    List<ListaItem> productList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        strNameProdact = findViewById(R.id.NazwaProduktuEditeText);
        strNamePrice = findViewById(R.id.CenaEditeText);
        NameQuantity = findViewById(R.id.IloscEditeText);


        productList = new ArrayList<>();    //tworzy tab na liste zakupów
        productList.add(new ListaItem("Item1", 10, 12, true));

        //moje
        irv = findViewById(R.id.recyclerView);

        irv.setHasFixedSize(true);
        LinearLayoutManager rlm = new LinearLayoutManager(this);
        irv.setLayoutManager(rlm);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(irv.getContext(), rlm.getOrientation());
        irv.addItemDecoration(dividerItemDecoration);
        getItems();
    }

    //metoda zwracająca itemy z listy w RecyclerView
    public void getItems() {
        MyAdapter rva = new MyAdapter(productList, this);
        irv.setAdapter(rva);
    }


    //dodawanie nowego itema po wciśnięciu buttona dodaj:
    public void clickSave(View view) {
        //dodanie nowego po kliku save
        productList.add(new ListaItem(
                strNameProdact.getText().toString(),
                Integer.valueOf(strNamePrice.getText().toString()),
                Integer.valueOf(NameQuantity.getText().toString()),
                false));
        //informacja o dodaniu -> dymek
        String tekxt = "Produkt został dodany do listy.";
        Toast msg = Toast.makeText(getBaseContext(), tekxt, Toast.LENGTH_LONG);
        msg.show();
        getItems();
    }

    //button czyszczenia danych z EditText
    public void clickClean(View view) {
        //wyczysci okienka
        strNameProdact.setText("");
        strNamePrice.setText("");
        NameQuantity.setText("");
        String tekxt = "Wyczyszczono dane.";
        Toast msgcz = Toast.makeText(getBaseContext(), tekxt, Toast.LENGTH_LONG);
        msgcz.show();
        getItems();
    }



    //zapis w pamięci wewnętrznej
    //String str = getString(R.string.text1);
    // FileOutputStream fos = null;
    // try {
    // fos = openFileOutput(getString(R.string.filelocation), Context.MODE_PRIVATE);
    // fos.write(str.getBytes());
    // fos.close();
    // } catch (FileNotFoundException e) {
    // e.printStackTrace();
    // } catch (IOException e) {
    // e.printStackTrace();
    // }


}


