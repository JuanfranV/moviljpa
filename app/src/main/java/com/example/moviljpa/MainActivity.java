package com.example.moviljpa;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviljpa.caracters.ContactoAdapter;
import com.example.moviljpa.models.Contacto;
import com.example.moviljpa.network.ApiClient;
import com.example.moviljpa.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContactoAdapter adapter;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerContactos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiService = ApiClient.getClient().create(ApiService.class);

        cargarContactos();
    }

    private void cargarContactos() {
        Call<List<Contacto>> call = apiService.getContactos();
        call.enqueue(new Callback<List<Contacto>>() {
            @Override
            public void onResponse(Call<List<Contacto>> call, Response<List<Contacto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new ContactoAdapter(MainActivity.this ,response.body());
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(MainActivity.this, "Error al cargar contactos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Contacto>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
