package com.example.votogames.veiculosfipe;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.votogames.veiculosfipe.models.AnoModelo;
import com.example.votogames.veiculosfipe.models.Marcas;
import com.example.votogames.veiculosfipe.models.Modelos;
import com.example.votogames.veiculosfipe.models.Veiculos;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.edtSelecionaTipo)
    EditText edtSelecionaTipo;
    @BindView(R.id.tilSelecionaTipo)
    TextInputLayout tilSelecionaTipo;
    @BindView(R.id.arrowSelecionaTipo)
    ImageView arrowSelecionaTipo;
    @BindView(R.id.viewSelecionaTipo)
    View viewSelecionaTipo;
    @BindView(R.id.edtSelecionaMarca)
    EditText edtSelecionaMarca;
    @BindView(R.id.tilSelecionaMarca)
    TextInputLayout tilSelecionaMarca;
    @BindView(R.id.arrowSelecionaMarca)
    ImageView arrowSelecionaMarca;
    @BindView(R.id.viewSelecionaMarca)
    View viewSelecionaMarca;
    @BindView(R.id.edtSelecionaModelo)
    EditText edtSelecionaModelo;
    @BindView(R.id.tilSelecionaModelo)
    TextInputLayout tilSelecionaModelo;
    @BindView(R.id.arrowSelecionaModelo)
    ImageView arrowSelecionaModelo;
    @BindView(R.id.viewSelecionaModelo)
    View viewSelecionaModelo;
    @BindView(R.id.edtSelecionaAno)
    EditText edtSelecionaAno;
    @BindView(R.id.tilSelecionaAno)
    TextInputLayout tilSelecionaAno;
    @BindView(R.id.arrowSelecionaAno)
    ImageView arrowSelecionaAno;
    @BindView(R.id.viewSelecionaAno)
    View viewSelecionaAno;
    @BindView(R.id.txtMarca)
    TextView txtMarca;
    @BindView(R.id.valueMarca)
    TextView valueMarca;
    @BindView(R.id.txtModelo)
    TextView txtModelo;
    @BindView(R.id.valueModelo)
    TextView valueModelo;
    @BindView(R.id.txtPreco)
    TextView txtPreco;
    @BindView(R.id.valuePreco)
    TextView valuePreco;
    @BindView(R.id.txtCodFipe)
    TextView txtCodFipe;
    @BindView(R.id.valueCodFipe)
    TextView valueCodFipe;
    @BindView(R.id.txtCombustivel)
    TextView txtCombustivel;
    @BindView(R.id.valueCombustivel)
    TextView valueCombustivel;
    @BindView(R.id.lytValores)
    ConstraintLayout lytValores;

    private Veiculos veiculos = new Veiculos();
    private List<Marcas> listMarcas = new ArrayList<>();
    private List<Modelos> listModelos = new ArrayList<>();
    private List<AnoModelo> listAnoModelos = new ArrayList<>();

    private Retrofit retrofit;
    private FipeService service;

    PopupMenu popupTipo;
    PopupMenu popupMarca;
    PopupMenu popupModelo;
    PopupMenu popupAno;

    private String tipoVeiculo;
    private int idMarca;
    private int idModelo;

    List<String> lista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        popupTipo = new PopupMenu(this, arrowSelecionaTipo);
        popupMarca = new PopupMenu(this, arrowSelecionaMarca);
        popupModelo = new PopupMenu(this, arrowSelecionaModelo);
        popupAno = new PopupMenu(this, arrowSelecionaAno);

        retrofit = new Retrofit.Builder()
                .baseUrl(FipeService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(FipeService.class);

        lista.add("Carros");
        lista.add("Motos");
        lista.add("Caminhoes");

        popupTipo.getMenu().clear();
        for (int i = 0; i < lista.size(); i++) {
            popupTipo.getMenu().add(0, i + 1, 0, lista.get(i));
        }

        popupTipo.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                edtSelecionaTipo.setText(menuItem.getTitle());
                recuperaMarcas(menuItem.getItemId());
                return true;
            }
        });

        popupMarca.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                edtSelecionaMarca.setText(menuItem.getTitle());
                recuperaModelos(menuItem.getItemId());
                idMarca = menuItem.getItemId();
                return true;
            }
        });

        popupModelo.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                edtSelecionaModelo.setText(menuItem.getTitle());
                idModelo = menuItem.getItemId();
                recuperaAnos(idModelo);
                return true;
            }
        });

        popupAno.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                edtSelecionaAno.setText(menuItem.getTitle());
                recuperaInfoVeiculo(menuItem.getTitle().toString());
                return true;
            }
        });
    }

    public void recuperaMarcas(int tipo){
        listMarcas.clear();

        if (tipo == 1){
            tipoVeiculo = "carros";
        } else if (tipo == 2){
            tipoVeiculo = "motos";
        } else if (tipo == 3){
            tipoVeiculo = "caminhoes";
        }

        Call<List<Marcas>> chamada = service.listMarcas(tipoVeiculo);

        chamada.enqueue(new Callback<List<Marcas>>() {
            @Override
            public void onResponse(Call<List<Marcas>> call, Response<List<Marcas>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Erro", "" + response.code());
                } else {
                    listMarcas.addAll(response.body());

                    popupMarca.getMenu().clear();
                    for (int i = 0; i < listMarcas.size(); i++) {
                        popupMarca.getMenu().add(0, listMarcas.get(i).id, 0, listMarcas.get(i).name);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Marcas>> call, Throwable t) {
                Log.e("Erro", t.getMessage());
            }
        });
    }

    public void recuperaModelos(int id){
        listModelos.clear();

        Call<List<Modelos>> chamada = service.listModelos(tipoVeiculo, id);

        chamada.enqueue(new Callback<List<Modelos>>() {
            @Override
            public void onResponse(Call<List<Modelos>> call, Response<List<Modelos>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Erro", "" + response.code());
                } else {
                    listModelos.addAll(response.body());

                    popupModelo.getMenu().clear();
                    for (int i = 0; i < listModelos.size(); i++) {
                        popupModelo.getMenu().add(0, Integer.valueOf(listModelos.get(i).id), 0, listModelos.get(i).name);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Modelos>> call, Throwable t) {
                Log.e("Erro", t.getMessage());
            }
        });
    }

    public void recuperaAnos(int idModelo){
        listAnoModelos.clear();

        Call<List<AnoModelo>> chamada = service.listAnos(tipoVeiculo, idMarca, idModelo);

        chamada.enqueue(new Callback<List<AnoModelo>>() {
            @Override
            public void onResponse(Call<List<AnoModelo>> call, Response<List<AnoModelo>> response) {
                if (!response.isSuccessful()) {
                    Log.e("Erro", "" + response.code());
                } else {
                    listAnoModelos.addAll(response.body());

                    popupAno.getMenu().clear();
                    for (int i = 0; i < listAnoModelos.size(); i++) {
                        popupAno.getMenu().add(0, i, 0, listAnoModelos.get(i).id);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<AnoModelo>> call, Throwable t) {
                Log.e("Erro", t.getMessage());
            }
        });
    }

    public void recuperaInfoVeiculo(String anoModelo){
        Call<Veiculos> chamada = service.getInfoVeiculo(tipoVeiculo, idMarca, idModelo, anoModelo);

        chamada.enqueue(new Callback<Veiculos>() {
            @Override
            public void onResponse(Call<Veiculos> call, Response<Veiculos> response) {
                if (!response.isSuccessful()) {
                    Log.e("Erro", "" + response.code());
                } else {
                    veiculos = response.body();

                    valueMarca.setText(veiculos.marca);
                    valueModelo.setText(veiculos.veiculo);
                    valuePreco.setText(veiculos.preco);
                    valueCodFipe.setText(veiculos.fipe_codigo);
                    valueCombustivel.setText(veiculos.combustivel);
                }
            }

            @Override
            public void onFailure(Call<Veiculos> call, Throwable t) {
                Log.e("Erro", t.getMessage());
            }
        });
    }

    @OnClick(R.id.viewSelecionaTipo)
    public void selecionaTipo(){
        popupTipo.show();
        popupTipo.setGravity(Gravity.END);
        edtSelecionaMarca.setText("");
        edtSelecionaModelo.setText("");
        edtSelecionaAno.setText("");
        limpaCampos();
    }

    @OnClick(R.id.viewSelecionaMarca)
    public void selecionaMarca(){
        popupMarca.show();
        popupMarca.setGravity(Gravity.END);
        edtSelecionaModelo.setText("");
        edtSelecionaAno.setText("");
        limpaCampos();
    }

    @OnClick(R.id.viewSelecionaModelo)
    public void selecionaModelo(){
        popupModelo.show();
        popupModelo.setGravity(Gravity.END);
        edtSelecionaAno.setText("");
        limpaCampos();
    }

    @OnClick(R.id.viewSelecionaAno)
    public void selecionaAno(){
        popupAno.show();
        popupAno.setGravity(Gravity.END);
    }

    private void limpaCampos(){
        valueMarca.setText("");
        valueModelo.setText("");
        valuePreco.setText("");
        valueCodFipe.setText("");
        valueCombustivel.setText("");
    }
}
