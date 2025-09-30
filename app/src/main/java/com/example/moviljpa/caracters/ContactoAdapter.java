package com.example.moviljpa.caracters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviljpa.R;
import com.example.moviljpa.models.Contacto;

import java.util.List;

public class ContactoAdapter extends RecyclerView.Adapter<ContactoAdapter.ViewHolder> {
    private Context context;
    private List<Contacto> contactos;

    public ContactoAdapter(Context context, List<Contacto> contactos) {
        this.context = context;
        this.contactos = contactos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contacto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contacto contacto = contactos.get(position);
        holder.txtNombre.setText(contacto.getNombre() + " " + contacto.getApellido());
        holder.txtTelefono.setText(contacto.getTelefono());
        holder.txtCategoria.setText(contacto.getCategoria().getNombre());
    }

    @Override
    public int getItemCount() {
        return contactos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtTelefono, txtCategoria;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtTelefono = itemView.findViewById(R.id.txtTelefono);
            txtCategoria = itemView.findViewById(R.id.txtCategoria);
        }
    }
}



