package androipro.com.br.controleEstoque;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androipro.com.br.controlecemig.R;

public class ActAnota extends AppCompatActivity {

    private EditText edtTeste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_anota);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //PRIMEIRO RECUPERA O EDIT, POIS USAREMOS ELE LOGO ABAIXO
        edtTeste = (EditText) findViewById(R.id.edtAnota);

        //METODO PARA CARREGAR O VALOR SALVO
        carregaTexto();

        //AO CLICAR NO BOTAO SALVAR, SALVA O TEXTO E FINALIZA A ATIVIDADE
        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvaTexto();
                finish();
            }
        });
    }

    //METODO PARA CARREGAR TEXTO (EDIT TEXT NAO PODE ESTAR NULL)
    private void carregaTexto() {
        SharedPreferences sp = this.getPreferences(Context.MODE_PRIVATE);
        //PRIMEIRO PARAMETRO É UM ID, SEGUNDO PARAMETRO É O VALOR DEFAULT(PARA CASO NAO EXISTA NADA SALVO NESSA CHAVE
        String anotacao = sp.getString("ANOTACOES.ID", "");
        edtTeste.setText(anotacao);
    }

    private void salvaTexto() {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        //ABRE EDITOR DO SHAREDPREFERENCES
        SharedPreferences.Editor editor = sharedPref.edit();
        //SALVA NA CHAVE DO PRIMEIRO PARAMETRO O VALOR DO SEGUNDO PARAMETRO
        editor.putString("ANOTACOES.ID", edtTeste.getText().toString());
        //SALVA E FECHA ARQUIVO
        editor.commit();
        Toast.makeText(ActAnota.this, "Anotações salvas!", Toast.LENGTH_SHORT).show();


    }
}
