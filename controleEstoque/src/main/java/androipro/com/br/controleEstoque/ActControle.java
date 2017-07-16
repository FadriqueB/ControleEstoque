package androipro.com.br.controleEstoque;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.database.sqlite.*;
import android.database.*;


import androipro.com.br.controleEstoque.dominio.Entidades.Item;
import androipro.com.br.controleEstoque.dominio.RepositorioItem;
import androipro.com.br.controlecemig.R;
import androipro.com.br.controleEstoque.database.DataBase;

public class ActControle extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{


    private ImageButton btnAdd;
    private EditText edtPesquisa;
    private ListView lstItens;
    private ArrayAdapter<Item> adpItens;

    private TextView txtEnt_Sai;
    private EditText edtEnt_Sai;
    private Button btnMais;
    private Button btnMenos;

    private EditText edtQuant;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RepositorioItem repositorioItem;
    private FiltraDados filtraDados;

    public static final String parItem = "ITEM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_controle);

        btnAdd = (ImageButton) findViewById(R.id.btnAdd);
        edtPesquisa = (EditText) findViewById(R.id.edtPesquisa);
        lstItens = (ListView) findViewById(R.id.lstItens);

        btnAdd.setOnClickListener(this);
        lstItens.setOnItemClickListener(this);

        try {
            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();

            repositorioItem = new RepositorioItem(conn);

            adpItens = repositorioItem.buscaItens(this);

            lstItens.setAdapter(adpItens);


           filtraDados = new FiltraDados(adpItens);

            edtPesquisa.addTextChangedListener(filtraDados);


        }catch (SQLException ex)
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);

            dlg.setMessage("Erro ao criar o banco"+ ex.getMessage());
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    public void onClick(View v) {

        Intent it = new Intent(this, ActCadItens.class);
        startActivityForResult(it, 0);

    }

    public void chamaAnota(View v){

        Intent it = new Intent(this, ActAnota.class);
        startActivityForResult(it,0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        /*método chamado quando uma act é fechada, no caso ele volta para o actMAIN já atualizando*/
        adpItens = repositorioItem.buscaItens(this);

            filtraDados.setArrayAdapter(adpItens);

        lstItens.setAdapter(adpItens);

    }

    /*método usado para selecionar um item quando ele for clicado*/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Item item = adpItens.getItem(position);

        Intent it = new Intent(this, ActCadItens.class);

        it.putExtra(parItem, item);

        startActivityForResult(it, 0);

    }

    // classe para pesquisar os itens
    private class FiltraDados implements TextWatcher
    {
        private ArrayAdapter<Item> arrayAdapter;

        private FiltraDados(ArrayAdapter<Item> arrayAdapter)
        {

            this.arrayAdapter = arrayAdapter;

        }

        //
        public void setArrayAdapter(ArrayAdapter<Item> arrayAdapter)
        {
            //para atualizar o listView, vide: onActResulre -> filtraDados.setArrayAdapter(adpItens);
            this.arrayAdapter = arrayAdapter;

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            //método para fazer a pesquisa DEPOIS de concluir o texto, não vai ser usado mas deve ser implementado
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            arrayAdapter.getFilter().filter(s);
            //com esse método a classe Item deveria estar retornando um método ToString (no caso o está) pois só assim
            //será possível filtrar os resultados certos.

        }

        @Override
        public void afterTextChanged(Editable s) {

            //método para fazer a pesquisa DEPOIS de concluir o texto, não vai ser usado mas deve ser implementado
        }
    }



}
