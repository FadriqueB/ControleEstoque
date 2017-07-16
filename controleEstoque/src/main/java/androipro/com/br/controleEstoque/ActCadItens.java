package androipro.com.br.controleEstoque;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import androipro.com.br.controleEstoque.database.DataBase;
import androipro.com.br.controleEstoque.dominio.Entidades.Item;
import androipro.com.br.controleEstoque.dominio.RepositorioItem;
import androipro.com.br.controlecemig.R;

public class ActCadItens extends AppCompatActivity {

    private EditText edtCod;
    private EditText edtNome;
    private EditText edtQuant;

    private TextView txtEnt_Sai;
    private EditText edtEnt_Sai;
    private Button btnMais;
    private Button btnMenos;

    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RepositorioItem repositorioItem;
    private Item item;

    private AlertDialog alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cad_itens);


        edtCod = (EditText) findViewById(R.id.edtCod);
        edtNome = (EditText) findViewById(R.id.edtNome);
        edtQuant = (EditText) findViewById(R.id.edtQuant);

        txtEnt_Sai = (TextView) findViewById(R.id.txtEnt_Sai);
        edtEnt_Sai = (EditText) findViewById(R.id.edtEnt_Sai);
        btnMais = (Button) findViewById(R.id.btnMais);
        btnMenos = (Button) findViewById(R.id.btnMenos);




        /*método para retornar a referencia do itent do actContato*/
        Bundle  bundle = getIntent().getExtras();

        if((bundle != null) && (bundle.containsKey(ActControle.parItem)))
        {
            item = (Item) bundle.getSerializable(ActControle.parItem);
            preencheDados();
        }
        else {

            txtEnt_Sai.setVisibility(View.INVISIBLE);
            edtEnt_Sai.setVisibility(View.INVISIBLE);
            btnMais.setVisibility(View.INVISIBLE);
            btnMenos.setVisibility(View.INVISIBLE);
            item = new Item();
        }

        try {
            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();

            repositorioItem = new RepositorioItem(conn);

        }catch (SQLException ex)
        {
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);

            dlg.setMessage("Erro ao criar o banco"+ ex.getMessage());
            dlg.setNeutralButton("OK",null);
            dlg.show();
        }



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




    }

    /*serve para criar o menu na act*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_cad_itens, menu);

        if(item.getId() != 0) //se for um item já existente
        {
            menu.getItem(1).setVisible(true); //menu excluir
            menu.getItem(2).setVisible(true); //menu alterar
        }

        return super.onCreateOptionsMenu(menu);

    }

    /*método responsável pela seleção dos itens de menu, qual itens foram selecionados*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){

            case R.id.mni_acao1:


                salvar();
                finish();

                break;

            case R.id.mni_acao2:

                excluir();
               // finish();

                break;

            case R.id.mni_acao3:

                alterar();

                break;

        }

        return super.onOptionsItemSelected(item);
    }


    private void preencheDados(){

        /*aqui ele ira preencher os dados qunado selecionar um item*/

        edtCod.setText(item.getCodigo());
        edtCod.setEnabled(false);

        edtNome.setText(item.getNome());
        edtNome.setEnabled(false);

        edtQuant.setText(item.getQuantidade());
        edtQuant.setEnabled(false);

    }

    private void excluir(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção!");
        builder.setMessage("Confirma exclusão do item:");

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                repositorioItem.excluir(item.getId());
                Toast.makeText(ActCadItens.this, "Dados excluídos com sucesso.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //não faz nada, apenas cancela a confirmação.
            }
        });

        alerta = builder.create();
        alerta.show();



    }


   private void salvar(){



        try {
           // item = new Item(); - aqui estava dando erro ao EDITAR itens, apenas criava outro (salvava)

            item.setCodigo(edtCod.getText().toString());
            item.setNome(edtNome.getText().toString());
            item.setQuantidade(edtQuant.getText().toString());

            if(item.getId() == 0) {
                repositorioItem.inserir(item);
                Toast.makeText(ActCadItens.this, "Dados salvos com sucesso.", Toast.LENGTH_SHORT).show();
            }
            else {
                repositorioItem.alterar(item);
                Toast.makeText(ActCadItens.this, "Dados editados com sucesso.", Toast.LENGTH_SHORT).show();
            }


            }catch (Exception ex) /*usa-se apenas excepiton pq é geral e n apenas do BD, como no caso do SQLExcpetion*/
            {
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);

                dlg.setMessage("Erro ao inserir os dados"+ ex.getMessage());
                dlg.setNeutralButton("OK",null);
                dlg.show();
            }

    }

    private void alterar()
    {
        edtCod.setEnabled(true);
        edtNome.setEnabled(true);
        edtQuant.setEnabled(true);
    }

    public void Acrescentar(View v)
    {

        int aux1, aux2, resul;

        if(edtEnt_Sai.getText().toString() == null || edtEnt_Sai.getText().toString().equals("")){
            Toast.makeText(ActCadItens.this, "Não existe valor preenchido!", Toast.LENGTH_SHORT).show();
        }
        else {
            aux1 = Integer.parseInt(edtQuant.getText().toString());
            aux2 = Integer.parseInt(edtEnt_Sai.getText().toString());
            resul = aux1 + aux2;
            String resultado = String.valueOf(resul);

            item.setQuantidade(resultado);
            repositorioItem.alterar(item);
            edtQuant.setText(resultado);

            Toast.makeText(ActCadItens.this, "Valor adicionado!", Toast.LENGTH_SHORT).show();
        }


    }

    public void Diminuir(View v)
    {

        int aux1, aux2, resul;

        if(edtEnt_Sai.getText().toString() == null || edtEnt_Sai.getText().toString().equals("")){
            Toast.makeText(ActCadItens.this, "Não existe valor preenchido!", Toast.LENGTH_SHORT).show();
        }
        else {
            aux1 = Integer.parseInt(edtQuant.getText().toString());
            aux2 = Integer.parseInt(edtEnt_Sai.getText().toString());

            if(aux1 - aux2 < 0){

                Toast.makeText(ActCadItens.this, "Valor excede a quantidade de itens!", Toast.LENGTH_SHORT).show();
            }
            else {
                resul = aux1 - aux2;
                String resultado = String.valueOf(resul);

                item.setQuantidade(resultado);
                repositorioItem.alterar(item);
                edtQuant.setText(resultado);

                Toast.makeText(ActCadItens.this, "Valor subtraido!", Toast.LENGTH_SHORT).show();
            }
        }


    }


}
