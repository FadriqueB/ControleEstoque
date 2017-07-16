package androipro.com.br.controleEstoque.dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.*;

import androipro.com.br.controleEstoque.dominio.Entidades.Item;

/**
 * Created by FadriqueB on 23/03/2016.
 */
public class RepositorioItem {


    private SQLiteDatabase conn;

    /*passar referência do bd*/
    public RepositorioItem(SQLiteDatabase conn){

        this.conn = conn;

    }


    private ContentValues preencheContentValues(Item item){

        ContentValues values = new ContentValues();

        values.put(Item.CODIGO, item.getCodigo());
        values.put(Item.NOME, item.getNome());
        values.put(Item.QUANTIDADE, item.getQuantidade());


        return values;
    }

    public void excluir(long id){

        conn.delete(Item.TABELA, " _id = ?", new String[]{ String.valueOf(id)});

    }

    public void alterar(Item item){


        ContentValues values = preencheContentValues(item);
        conn.update(Item.TABELA, values, " _id = ? ", new String[]{String.valueOf(item.getId())});

    }

    public void inserir(Item item){

        ContentValues values = preencheContentValues(item);
        conn.insertOrThrow(Item.TABELA, null, values);

    }


    public ArrayAdapter<Item> buscaItens(Context context){
                                                                                /*serve para mostrar 1 item/linha no listview*/
        ArrayAdapter<Item> adpItens = new ArrayAdapter<Item>(context, android.R.layout.simple_list_item_1);

        /*passar nome da tabela que deseja procurar (tabale do script) */
        Cursor cursor = conn.query(Item.TABELA, null, null, null, null, null, null);

        if(cursor.getCount() > 0){

            cursor.moveToFirst();

            do{
                Item item = new Item();
                item.setId(cursor.getLong(cursor.getColumnIndex(Item.ID)));
                item.setCodigo(cursor.getString(cursor.getColumnIndex(Item.CODIGO)));
                item.setNome(cursor.getString(cursor.getColumnIndex(Item.NOME)));
                item.setQuantidade(cursor.getString(cursor.getColumnIndex(Item.QUANTIDADE)));

                adpItens.add(item);
            }while (cursor.moveToNext());/*mover enquanto tiver próximo no script*/

        }

        return adpItens;

    }

}
