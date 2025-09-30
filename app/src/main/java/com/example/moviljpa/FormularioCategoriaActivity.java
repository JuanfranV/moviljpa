package com.example.moviljpa;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moviljpa.models.Categoria;

import java.util.ArrayList;
import java.util.List;

public class FormularioCategoriaActivity extends AppCompatActivity {

    private EditText etNombreCategoria;
    private Button btnGuardarCategoria;

    // 👇 agrega esta lista
    private List<String> listaCategorias = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_categoria);

        etNombreCategoria = findViewById(R.id.etNombreCategoria);
        btnGuardarCategoria = findViewById(R.id.btnGuardarCategoria);

        btnGuardarCategoria.setOnClickListener(v -> guardarCategoria());
    }

    private void guardarCategoria() {
        String nombre = etNombreCategoria.getText().toString();

        if (!nombre.isEmpty()) {
            // Agregar a la lista local
            listaCategorias.add(nombre);

            // Aquí podrías llamar a tu API con Retrofit para guardarla en la BD
            // apiService.agregarCategoria(new Categoria(nombre))...

            Toast.makeText(this, "Categoría agregada: " + nombre, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Por favor ingresa un nombre", Toast.LENGTH_SHORT).show();
        }
    }
}


