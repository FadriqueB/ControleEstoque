package androipro.com.br.controleEstoque.database;

/**
 * Created by FadriqueB on 22/03/2016.
 */
public class ScriptSQL {

    public static String getCreateItem(){


        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("CREATE TABLE IF NOT EXISTS ListaItens ( ");
        sqlBuilder.append("_id                INTEGER       NOT NULL ");
        sqlBuilder.append("PRIMARY KEY AUTOINCREMENT, ");
        sqlBuilder.append("CODIGO                  VARCHAR (100), ");
        sqlBuilder.append("NOME                    VARCHAR (200), ");
        sqlBuilder.append("QUANTIDADE              VARCHAR (100)); ");

        return sqlBuilder.toString();


    }

}
