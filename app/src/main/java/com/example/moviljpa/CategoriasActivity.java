package com.example.moviljpa;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.moviljpa.models.Categoria;
import com.example.moviljpa.network.ApiClient;
import com.example.moviljpa.network.ApiService;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

import android.widget.Button;
import android.widget.EditText;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriasActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> nombresCategorias;
    private ApiService apiService;
    private EditText etNuevaCategoria;
    private Button btnAgregarCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        listView = findViewById(R.id.listViewCategorias);
        etNuevaCategoria = findViewById(R.id.etNuevaCategoria);
        btnAgregarCategoria = findViewById(R.id.btnAgregarCategoria);

        nombresCategorias = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nombresCategorias);
        listView.setAdapter(adapter);

        apiService = ApiClient.getClient().create(ApiService.class);

        cargarCategorias();

        btnAgregarCategoria.setOnClickListener(v -> {
            String nombre = etNuevaCategoria.getText().toString();
            if (!nombre.isEmpty()) {
                Categoria nueva = new Categoria(nombre);
                apiService.crearCategoria(nueva).enqueue(new Callback<Categoria>() {
                    @Override
                    public void onResponse(Call<Categoria> call, Response<Categoria> response) {
                        if (response.isSuccessful()) {
                            nombresCategorias.add(response.body().getNombre());
                            adapter.notifyDataSetChanged();
                            etNuevaCategoria.setText("");
                        }
                    }

                    @Override
                    public void onFailure(Call<Categoria> call, Throwable t) {}
                });
            }
        });
    }

    private void cargarCategorias() {
        apiService.getCategorias().enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (Categoria c : response.body()) {
                        nombresCategorias.add(c.getNombre());
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {}
        });
    }
}


