package com.jamer.jaimeloyola.becas.UI;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.jamer.jaimeloyola.becas.Modelo.ScriptDatabase;
import com.jamer.jaimeloyola.becas.R;
import com.jamer.jaimeloyola.becas.Web.VolleySingleton;

/**
 * Created by Jaime Loyola on 10/03/2016.
 *
 * Adaptador para inflar la lista dde entradas.
 */
public class FeedAdapter extends CursorAdapter {

    //Etiqueta de depuración
    private static final String TAG = FeedAdapter.class.getSimpleName();

    /**
     * View holder para evitar multiples llamadas de findViewById()
     */
    static class ViewHolder {
        TextView titulo;
        TextView descripcion;
        NetworkImageView imagen;

        int tituloI;
        int descripcionI;
        int imagenI;
    }

    public FeedAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);

    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.item_layout, null, false);

        ViewHolder vh = new ViewHolder();

        //Almacenar referencias
        vh.titulo = (TextView) view.findViewById(R.id.titulo);
        vh.descripcion = (TextView) view.findViewById(R.id.descripcion);
        vh.imagen = (NetworkImageView) view.findViewById(R.id.imagen);

        //Ajustar índices
        vh.tituloI = cursor.getColumnIndex(ScriptDatabase.ColumnEntries.TITULO);
        vh.descripcionI = cursor.getColumnIndex(ScriptDatabase.ColumnEntries.DESCRIPCION);
        vh.imagenI = cursor.getColumnIndex(ScriptDatabase.ColumnEntries.URL_IMAGE);

        view.setTag(vh);

        return view;
    }

    public void bindView(View view, Context context, Cursor cursor) {
        final ViewHolder vh = (ViewHolder) view.getTag();

        //Establecer texto del titulo
        vh.titulo.setText(cursor.getString(vh.tituloI));

        //Obtener acceso a la descripción  y su longitud
        int ln = cursor.getString(vh.descripcionI).length();
        String descripcion = cursor.getString(vh.descripcionI);

        //Establecer descripción dentro del límite.
        if(ln >= 150)
            vh.descripcion.setText(descripcion.substring(0, 150) + "...");
        else vh.descripcion.setText(descripcion);

        //Obtener link de la imagen
        String imageUrl = cursor.getString(vh.imagenI);
        ImageLoader imageLoader = VolleySingleton.getInstance(context).getImageLoader();

        //Establecer imagen en image view
        vh.imagen.setImageUrl(imageUrl, imageLoader);
    }
}

