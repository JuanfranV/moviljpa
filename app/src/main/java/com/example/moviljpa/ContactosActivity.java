package com.example.moviljpa;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviljpa.caracters.ContactoAdapter;
import com.example.moviljpa.models.Contacto;
import com.example.moviljpa.network.ApiClient;
import com.example.moviljpa.network.ApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactosActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ContactoAdapter adapter;
    List<Contacto> listaContactos = new ArrayList<>();
    ApiService apiService;
    FloatingActionButton fabAddContacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);

        recyclerView = findViewById(R.id.recyclerContactos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactoAdapter(this, listaContactos);
        recyclerView.setAdapter(adapter);

        fabAddContacto = findViewById(R.id.btnAddContacto);

        apiService = ApiClient.getClient().create(ApiService.class);
        cargarContactos();

        fabAddContacto.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormularioContactoActivity.class);
            startActivity(intent);
        });
    }

    private void cargarContactos() {
        apiService.getContactos().enqueue(new Callback<List<Contacto>>() {
            @Override
            public void onResponse(Call<List<Contacto>> call, Response<List<Contacto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaContactos.clear();
                    listaContactos.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Contacto>> call, Throwable t) {}
        });
    }
}

