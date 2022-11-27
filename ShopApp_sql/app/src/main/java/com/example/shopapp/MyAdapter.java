package com.example.shopapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Queue;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<ListaItem> list;
    //private Context context; pir

    public MyAdapter(List<ListaItem> lil) {
        list = lil;
        // context = ctx; pir
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ListaItem li = list.get(position);
        holder.name.setText(li.getName());
        holder.price.setText("" + li.getPrice());
        holder.quantity.setText("" + li.getQuantity());
        holder.identyfikator.setText(""+li.getIdentyfikator());

        if (li.getChecked()) {
            holder.checked.setChecked(true);
            holder.checked.setText("Kupiony!");
        } else {
            holder.checked.setChecked(false);
            holder.checked.setText("NIE kupiony!!");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView price;
        public TextView quantity;
        public CheckBox checked;
        public TextView identyfikator;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewNameProduct);         //zmieniłam na id pól z leyout: list_layout
            price = itemView.findViewById(R.id.textViewPrice);
            quantity = itemView.findViewById(R.id.textViewQuantity);
            checked = itemView.findViewById(R.id.CzyKupioneChackbox);
            identyfikator = itemView.findViewById(R.id.textView6);

            itemView.setOnClickListener(this);

            checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        Toast.makeText(itemView.getContext(), name.getText()+" został kupiony!",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(),name.getText(),Toast.LENGTH_SHORT).show();
        }
    }
}
