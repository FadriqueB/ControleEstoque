package androipro.com.br.controleEstoque.database;

/**
 * Created by FadriqueB on 22/03/2016.
 */

import android.content.Context;
import android.database.sqlite.*;

public class DataBase extends SQLiteOpenHelper{

    public DataBase(Context context)
    {

        /*construtor*/
        super(context, "Lista", null, 1);

    }

    @Override
    /*verifica se existe, caso não ele vai criar o BD*/
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(ScriptSQL.getCreateItem());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
