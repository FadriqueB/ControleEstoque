package androipro.com.br.controleEstoque.dominio.Entidades;

import java.io.Serializable;

/**
 * Created by FadriqueB on 24/03/2016.
 * ESTA CLASSE SERVE PARA TRANSPORTAR OS DADOS ENTRE A INTERFACE E A CLASSE REPOSITÓRIO
 */
public class Item implements Serializable{

    public static String TABELA = "ListaItens";
    public static String ID = "_id";
    public static String CODIGO = "CODIGO";
    public static String NOME = "NOME";
    public static String QUANTIDADE = "QUANTIDADE";

    private long id;
    private String codigo;
    private String nome;
    private String quantidade;

    public Item(){

        id = 0; /*isso serve para a condição em ActCadItens para saber se é um novo item, em ***case R.id.mni_acao1:*** */

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString(){

        return "COD  : " + codigo + " \nNOME : " + nome + " \nQUANT: " + quantidade;

    }
}
