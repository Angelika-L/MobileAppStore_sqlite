package com.example.shopapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context mContext;
    private List<ListaItem> itemList;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = mAuth.getCurrentUser();
    private FirebaseDatabase databaseFA = FirebaseDatabase.getInstance();
    private DatabaseReference string_dr, ref;

    public MyAdapter(Context context, List<ListaItem> itemList) {
        this.itemList = itemList;
        this.mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nameText;
        public TextView cenaText;
        public TextView IloscText;
        public TextView ID;
        public CheckBox checked;

        public ViewHolder(final View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.textViewNameProduct);
            cenaText = itemView.findViewById(R.id.textViewPrice);
            IloscText = itemView.findViewById(R.id.textViewQuantity);
            ID = itemView.findViewById(R.id.textView6);
            checked = itemView.findViewById(R.id.CzyKupioneChackbox);
            itemView.setOnClickListener(this);

            checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        //Toast.makeText(itemView.getContext(), "Produkt : " + nameText.getText() + ", został kupiony!", Toast.LENGTH_SHORT).show();
                        //checked.setText("Kupiony!");
                        updateChbSale(nameText.getText().toString(), true,Integer.valueOf(cenaText.getText().toString()),Integer.valueOf(IloscText.getText().toString()));
                    }
                    else {
                        //checked.setText("NIE kupiony!");
                        updateChbSale(nameText.getText().toString(), false,Integer.valueOf(cenaText.getText().toString()),Integer.valueOf(IloscText.getText().toString()));
                    }
                }
            });
        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            String nameId = v.getTag().toString();
            editProduct(nameId, position);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListaItem li = itemList.get(position);
        holder.nameText.setText(li.getName());
        holder.cenaText.setText(String.valueOf(li.getPrice()));
        holder.IloscText.setText(String.valueOf(li.getQuantity()));
        holder.checked.setChecked(li.getChecked_bool());
        holder.ID.setText(li.getId());
        holder.itemView.setTag(li.getName());
    }

    private void editProduct(final String id, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View subView = inflater.inflate(R.layout.edit_product_layout, null);

        ListaItem li = itemList.get(position);
        final EditText productName = subView.findViewById(R.id.NazwaProduktuET_edit);
        final EditText price = subView.findViewById(R.id.priceProductEditText_edit);
        final EditText quanrity = subView.findViewById(R.id.quantityProductEditText_edit);

        productName.setText(String.valueOf(li.getName()));
        price.setText(String.valueOf(li.getPrice()));
        quanrity.setText(String.valueOf(li.getQuantity()));
        final boolean valueSale = li.getChecked_bool();
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Edycja produktu z listy zakupów");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("ZAPISZ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String nameZ = productName.getText().toString();
                final int priceZ = Integer.valueOf(price.getText().toString());
                final int qualityZ = Integer.valueOf(quanrity.getText().toString());
                if (TextUtils.isEmpty((nameZ))) {
                    Toast.makeText(mContext, "Nazwa produktu jest wymagana", Toast.LENGTH_LONG).show();
                } else {
                    updateAllIsue(id,nameZ, priceZ, qualityZ,valueSale);
                    ((Activity) mContext).finish();
                    mContext.startActivity(((Activity) mContext).getIntent());
                }
            }
        });
        builder.setNegativeButton("WYJŚCIE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(mContext,"Wyjście",Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public void swapCouror(List<ListaItem> itemList) {
        int size = itemList.size();
        if (size > 0) {
            notifyDataSetChanged();
        }
    }

    public void updateChbSale(String nameProduct, boolean value, int price, int quantity) {
        ref = databaseFA.getReference("ListaItem");
        String user = currentUser.getUid();
        string_dr = ref.child(user).child(nameProduct);
        Map<String, Object> updateBoolean = new HashMap<>();
        updateBoolean.put("czy kupiony?", value);
        updateBoolean.put("Cena", price);
        updateBoolean.put("Ilość", quantity);
        updateBoolean.put("Id", ref.push().getKey());
        string_dr.setValue(updateBoolean);
    }

    public void updateAllIsue(String nameID, String name, int price, int quantity, boolean valueSale) {
        ref = databaseFA.getReference("ListaItem");
        String user = currentUser.getUid();
        string_dr = ref.child(user).child(nameID);
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("Cena", price);
        updateMap.put("Ilość", quantity);
        updateMap.put("czy kupiony?", valueSale);
        updateMap.put("Id", ref.push().getKey());
        ref.child(user).child(nameID).push().setValue(updateMap);
        string_dr.setValue(updateMap);


        /*
        @SuppressLint("RestrictedApi") String path = String.valueOf(string_dr.getPath());
        databaseFA.getReference(path).removeValue();
        HashMap<String, Object> ls = new HashMap<>();
        if (!name.isEmpty() && currentUser != null) {
            string_dr = ref.child(user).child(name);
            String id = ref.push().getKey();
            ls.put("Cena", price);
            ls.put("Ilość", quantity);
            ls.put("czy kupiony?", false);
            ls.put("Id", id);
            string_dr.setValue(ls);
        //NIE ROBIC PATOLOGII
        }*/

    }

}