package com.example.moviljpa;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moviljpa.models.Categoria;
import com.example.moviljpa.models.Contacto;
import com.example.moviljpa.network.ApiClient;
import com.example.moviljpa.network.ApiService;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormularioContactoActivity extends AppCompatActivity {
    EditText etNombre, etApellido, etTelefono, etCorreo;
    Spinner spCategorias;
    Button btnGuardar;
    ApiService apiService;
    List<Categoria> listaCategorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_contacto);

        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etTelefono = findViewById(R.id.etTelefono);
        etCorreo = findViewById(R.id.etCorreo);
        spCategorias = findViewById(R.id.spCategorias);
        btnGuardar = findViewById(R.id.btnGuardarContacto);

        apiService = ApiClient.getClient().create(ApiService.class);

        cargarCategorias();

        btnGuardar.setOnClickListener(v -> {
            int pos = spCategorias.getSelectedItemPosition();
            Categoria categoria = listaCategorias.get(pos);

            Contacto nuevo = new Contacto(
                    etNombre.getText().toString(),
                    etApellido.getText().toString(),
                    etTelefono.getText().toString(),
                    etCorreo.getText().toString(),
                    categoria
            );

            apiService.crearContacto(nuevo).enqueue(new Callback<Contacto>() {
                @Override
                public void onResponse(Call<Contacto> call, Response<Contacto> response) {
                    if (response.isSuccessful()) finish();
                }

                @Override
                public void onFailure(Call<Contacto> call, Throwable t) {}
            });
        });
    }

    private void cargarCategorias() {
        apiService.getCategorias().enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaCategorias = response.body();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            FormularioContactoActivity.this,
                            android.R.layout.simple_spinner_item,
                            listaCategorias.stream().map(Categoria::getNombre).toArray(String[]::new)
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spCategorias.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {}
        });
    }
}

