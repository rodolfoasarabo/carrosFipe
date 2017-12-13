package com.example.votogames.veiculosfipe;

import com.example.votogames.veiculosfipe.models.AnoModelo;
import com.example.votogames.veiculosfipe.models.Marcas;
import com.example.votogames.veiculosfipe.models.Modelos;
import com.example.votogames.veiculosfipe.models.Veiculos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by VOTOGAMES on 04/09/2017.
 */

public interface FipeService {

    public static final String URL_BASE = "http://fipeapi.appspot.com/api/1/";

    @GET("{tipo}/marcas.json")
    Call<List<Marcas>> listMarcas(@Path("tipo") String tipo);

    @GET("{tipo}/veiculos/{id}.json")
    Call<List<Modelos>> listModelos(@Path("tipo") String tipo,
                                    @Path("id") int id);

    @GET("{tipo}/veiculo/{id}/{idModelo}.json")
    Call<List<AnoModelo>> listAnos(@Path("tipo") String tipo,
                                   @Path("id") int id,
                                   @Path("idModelo") int idModelo);

    @GET("{tipo}/veiculo/{id}/{idModelo}/{anoModelo}.json")
    Call<Veiculos> getInfoVeiculo(@Path("tipo") String tipo,
                                         @Path("id") int id,
                                         @Path("idModelo") int idModelo,
                                         @Path("anoModelo") String anoModelo);


}
