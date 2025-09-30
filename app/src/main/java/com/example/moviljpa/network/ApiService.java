package com.example.moviljpa.network;

import com.example.moviljpa.models.Categoria;
import com.example.moviljpa.models.Contacto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    // ---- CONTACTOS ----
    @GET("contactos")
    Call<List<Contacto>> getContactos();

    @POST("contactos")
    Call<Contacto> crearContacto(@Body Contacto contacto);

    // ---- CATEGORÍAS ----
    @GET("categorias")
    Call<List<Categoria>> getCategorias();

    @POST("categorias")
    Call<Categoria> crearCategoria(@Body Categoria categoria);
}

