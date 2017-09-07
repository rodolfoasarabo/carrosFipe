package com.example.votogames.veiculosfipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.votogames.veiculosfipe.models.Veiculos;
import com.example.votogames.veiculosfipe.models.VeiculosCatalog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private List<Veiculos> todosVeiculos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buscaCarros();
    }

    public void buscaCarros(){
        todosVeiculos.clear();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FipeService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FipeService service = retrofit.create(FipeService.class);

        Call<VeiculosCatalog> requestCatalog = service.listaVeiculos();

        requestCatalog.enqueue(new Callback<VeiculosCatalog>() {
            @Override
            public void onResponse(Call<VeiculosCatalog> call, Response<VeiculosCatalog> response) {
                    int statusCode = response.code();
                    VeiculosCatalog catalogo = response.body();



                    for (Veiculos v : catalogo.veiculos){
                        Log.i("Veiculo", v.name);
                    }

            }

            @Override
            public void onFailure(Call<VeiculosCatalog> call, Throwable t) {
                Log.e("ERRO", "Erro" + t.getMessage());
            }
        });

    }
}
