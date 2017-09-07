package com.example.votogames.veiculosfipe;

import com.example.votogames.veiculosfipe.models.AnoModelo;
import com.example.votogames.veiculosfipe.models.AnoModeloCatalog;
import com.example.votogames.veiculosfipe.models.Marcas;
import com.example.votogames.veiculosfipe.models.MarcasCatalog;
import com.example.votogames.veiculosfipe.models.Modelos;
import com.example.votogames.veiculosfipe.models.ModelosCatalog;
import com.example.votogames.veiculosfipe.models.Veiculos;
import com.example.votogames.veiculosfipe.models.VeiculosCatalog;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by VOTOGAMES on 04/09/2017.
 */

public interface FipeService {

    public static final String URL_BASE = "http://fipeapi.appspot.com/";
    public static final String URL_BASE_CARROS = "http://fipeapi.wipsites.com.br/carros";
    public static final String URL_BASE_MOTOS = "http://fipeapi.wipsites.com.br/motos";
    public static final String URL_BASE_CAMINHOES = "http://fipeapi.wipsites.com.br/caminhoes";

    @GET("marcas")
    Call<MarcasCatalog> listMarcas();

    @GET("13")
    Call<ModelosCatalog> listModelos();

    @GET("6418")
    Call<AnoModeloCatalog> listAnoModelo();

    @GET("2013-1")
    Call<VeiculosCatalog> listVeiculos();

    @GET("api/1/carros/marcas.json")
    Call<VeiculosCatalog> listaVeiculos();



}
