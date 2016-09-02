package com.jamer.jaimeloyola.becas.Modelo;

import android.provider.BaseColumns;

/**
 * Created by Jaime Loyola on 11/03/2016.
 *
 * Clase que representa un script restaurador del estado inicial de la base de datos
 */
public class ScriptDatabase {

    //Etiqueta de depuración
    private static final String TAG = ScriptDatabase.class.getSimpleName();

    //Metainformación de la base de datos
    public static final String CONSULTA_TABLE_NAME = "entrada";
    public static final String STRING_TYPE = "TEXT";
    public static final String INT_TYPE = "INTEGER";

    //Campos de la tabla
    public static class ColumnEntries {
        public static final String ID = BaseColumns._ID;
        public static final String TITULO = "titulo";
        public static final String DESCRIPCION = "descripcion";
        public static final String URL = "url";
        public static final String URL_IMAGE = "thumb_url";
    }

    //Comando CREATE para crear la tabla que almacene los últimos datos descargados.
    public static final String CREAR_CONSULTA =
            "CREATE TABLE " + CONSULTA_TABLE_NAME + "(" +
                    ColumnEntries.ID + " " + INT_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                    ColumnEntries.TITULO + " " + STRING_TYPE + "NOT NULL," +
                    ColumnEntries.DESCRIPCION + " " + STRING_TYPE + "," +
                    ColumnEntries.URL + " " + STRING_TYPE + "," +
                    ColumnEntries.URL_IMAGE + " " + STRING_TYPE + ")";
}
