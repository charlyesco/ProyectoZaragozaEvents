package com.example.carlos.proyectoevents;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class InicioActivity extends AppCompatActivity {
    Button b;
    TextView sinLogin;
    AlertDialog.Builder ventana;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);


        sinLogin=findViewById(R.id.tv_sin_login);
        b=findViewById(R.id.button_inicio);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento=new Intent(InicioActivity.this,LoginActivity.class);
                startActivity(intento);
            }
        });
        sinLogin.setText(Html.fromHtml("<u>Iniciar sin login</u>"));
        sinLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(InicioActivity.this,NavigationActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    public void onBackPressed() {

        showDialog(2);
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        switch (id) {
            case 2:

                ventana = new AlertDialog.Builder(this);
                ventana.setTitle("Información");
                ventana.setMessage("¿Estas seguro que quieres salir del la aplicación?");
                ventana.setIcon(R.mipmap.ic_launcher);

                ventana.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       finish();
                    }
                });
                ventana.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                ventana.setNeutralButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                break;
        }
        return ventana.create();
    }
}
