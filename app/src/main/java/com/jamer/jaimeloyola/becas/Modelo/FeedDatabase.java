package com.jamer.jaimeloyola.becas.Modelo;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jamer.jaimeloyola.becas.RssParse.Item;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Jaime Loyola on 01/04/2016.
 *
 * Clase que administra el acceso y operaciones hacia la base de datos.
 */
public final class FeedDatabase extends SQLiteOpenHelper {

    //Índices
    private static final int COLUMN_ID = 0;
    private static final int COLUMN_TITULO = 1;
    private static final int COLUMN_DESC = 2;
    private static final int COLUMN_URL = 3;

    /*
    Instancia singleton
     */
    private static FeedDatabase singleton;

    //Etiqueta de depuración
    private static final String TAG = FeedDatabase.class.getSimpleName();

    /*
    Nombre de la base de datos
     */
    public static final String DATABASE_NAME = "Feed.db";

    /*
    Versión actual de la base de datos
     */
    public static final int DATABASE_VERSION = 1;

    private FeedDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Retorna la instancia única del singleton
     *
     * @param context Contexto donde se ejecutarán las peticiones
     * @return Instancia
     */
    public static synchronized FeedDatabase getInstance(Context context) {
        if(singleton == null)
            singleton = new FeedDatabase(context.getApplicationContext());
        return singleton;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Crear la tabla 'consulta'
        db.execSQL(ScriptDatabase.CREAR_CONSULTA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Añade los cambios que se realizarán en el esquema
        db.execSQL("DROP TABLE IF EXISTS " + ScriptDatabase.CONSULTA_TABLE_NAME);
        onCreate(db);
    }

    /**
     * Obtiene todos los registros de la tabla consulta
     *
     * @return cursor con los registros
     */
    public Cursor obtenerEntradas() {
        //Seleccionamos todas las filas de la tabla 'consulta'
        return getWritableDatabase().rawQuery(
                "SELECT * FROM " + ScriptDatabase.CONSULTA_TABLE_NAME, null);
    }

    /**
     * Inserta un registro en la tabla entrada
     *
     * @param titulo    titulo de la entrada
     * @param descr     descripción de la entrada
     * @param url       url del artículo
     * @param thumb_url url de la miniatura
     */
    public void insertarEntrada(
            String titulo,
            String descr,
            String url,
            String thumb_url) {
        ContentValues values = new ContentValues();
        values.put(ScriptDatabase.ColumnEntries.TITULO, titulo);
        values.put(ScriptDatabase.ColumnEntries.DESCRIPCION, descr);
        values.put(ScriptDatabase.ColumnEntries.URL, url);
        values.put(ScriptDatabase.ColumnEntries.URL_IMAGE, thumb_url);

        //Insertando el registro en la base de datos
        getWritableDatabase().insert(
                ScriptDatabase.CONSULTA_TABLE_NAME,
                null,
                values
        );
    }


    /**
     * Modiica los valores de las columnas de una entrada
     *
     * @param id        identificador de la entrada
     * @param titulo    titulo nuevo de la entrada
     * @param descr     descripción nueva de la entrada
     * @param url       url nueva para el artículo
     * @param thumb_url url nueva para la miniatura
     */
    public void actualizarEntrada(int id,
            String titulo,
            String descr,
            String url,
            String thumb_url) {

        ContentValues values = new ContentValues();
        values.put(ScriptDatabase.ColumnEntries.TITULO, titulo);
        values.put(ScriptDatabase.ColumnEntries.DESCRIPCION, descr);
        values.put(ScriptDatabase.ColumnEntries.URL, url);
        values.put(ScriptDatabase.ColumnEntries.URL_IMAGE, thumb_url);

        //Modificar entrada
        getWritableDatabase().update(
                ScriptDatabase.CONSULTA_TABLE_NAME,
                values,
                ScriptDatabase.ColumnEntries.ID + "=?",
                new String[]{String.valueOf(id)});
    }

    /**
     * Procesa una lista de items para su almacenamiento local
     * y sincronización.
     *
     * @param entries lista de items
     */
    public void sincronizarEntradas(List<Item> entries) {
        /*
        1. Mapear temporalmente las entradas nuevas para realizar una comparación con las locales
         */
        HashMap<String, Item> entryMap = new HashMap<String, Item>();
        for(Item e : entries) {
            entryMap.put(e.getTitle(), e);
        }

        /*
        2. Obtener las entradas locales
         */
        Log.i(TAG, "Consultar items actualmente almacenados");
        Cursor c = obtenerEntradas();
        assert c != null;
        Log.i(TAG, "Se encontraron " + c.getCount() + " entradas, computando...");

        /*
        3. Comenzar a comparar las entradas
         */
        int id;
        String titulo;
        String descr;
        String url;

        while (c.moveToNext()) {
            id = c.getInt(COLUMN_ID);
            titulo = c.getString(COLUMN_TITULO);
            descr = c.getString(COLUMN_DESC);
            url = c.getString(COLUMN_URL);

            Item match = entryMap.get(titulo);
            if(match != null) {
                //Filtrar entradas existentes. Remover para prevenir futura inserción
                entryMap.remove(titulo);

                /*
                3.1 Comprobar si la entrada necesita ser actualizada
                 */
                if((match.getTitle() != null && !match.getTitle().equals(titulo))
                        || (match.getDescripcion() != null && !match.getDescripcion().equals(descr))
                        || (match.getLink() != null && !match.getLink().equals(url))) {

                    //Actualizar entradas
                    actualizarEntrada(id,
                            match.getTitle(),
                            match.getDescripcion(),
                            match.getLink(),
                            match.getContent().getUrl()
                    );
                }
            }
        }
        c.close();

        /*
        4. Añadir entradas nuevas
         */
        for(Item e : entryMap.values()) {
            Log.i(TAG, "Insertando: titulo=" + e.getTitle());
            insertarEntrada(
                    e.getTitle(),
                    e.getDescripcion(),
                    e.getLink(),
                    e.getContent().getUrl()
            );
        }
        Log.i(TAG, "Se actualizaron los registros");
    }

}
