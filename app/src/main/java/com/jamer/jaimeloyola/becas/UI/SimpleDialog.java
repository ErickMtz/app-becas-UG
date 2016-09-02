package com.jamer.jaimeloyola.becas.UI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by Jaime Loyola on 3/5/2016.
 *
 * Clase que representa un fragmento con di?logo simple en Android
 */
public class SimpleDialog extends android.app.DialogFragment {

    public SimpleDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createSimpleDialog();
    }

    /**
     * Crea un di?logo de informaci?n sencillo
     * @return Nuevo di?logo
     */
    public AlertDialog createSimpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Cargando informaci?n")
                .setMessage("Por favor espera mientras se cargan las convocatorias...")
                .setPositiveButton("Entendido",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Acciones
                            }
                        });
        return builder.create();
    }
}
