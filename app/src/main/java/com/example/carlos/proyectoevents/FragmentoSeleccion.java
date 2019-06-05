package com.example.carlos.proyectoevents;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;


public class FragmentoSeleccion extends DialogFragment {
    Idioma idioma;

    //Sobreescribimos este metodo(Despues de crear la clase interface)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        idioma = (Idioma) getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String[] idiomas = {"Whatsapp", "Facebook"};

        AlertDialog.Builder ventana = new AlertDialog.Builder(getActivity());

        ventana.setTitle("Selecciona una opción");

        ventana.setItems(idiomas, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int lenguaje) {
                idioma.idiomaSeleccionado(idiomas[lenguaje]);
            }
        });

        return ventana.create();
    }

    //Creamos la clase interface dentro de la clase FragmetoDialogo, hemos definido un objeto de la clase arriba
    public interface Idioma {
        public void idiomaSeleccionado(String idioma);
    }
}
