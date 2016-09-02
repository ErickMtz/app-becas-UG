package com.jamer.jaimeloyola.becas.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jamer.jaimeloyola.becas.R;

/**
 * Created by Jaime Loyola on 10/03/2016.
 *
 * Actividad que muestra a detalle el artículo del feed.
 */

public class DetailActivity extends AppCompatActivity{
    //Etiqueta de depración
    private static final String TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Deshabilitar titulo de la actividad
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        //Recuperar URL
        String urlExtra = getIntent().getStringExtra("url-extra");

        //Obtener WebView
        WebView webview = (WebView)findViewById(R.id.webview);

        //Habilitar Javascript en el renderizado
        webview.getSettings().setJavaScriptEnabled(true);

        //Transmitir localmente
        webview.setWebViewClient(new WebViewClient());

        //Cargar el contendio de la URL
        webview.loadUrl(urlExtra);
    }
}
