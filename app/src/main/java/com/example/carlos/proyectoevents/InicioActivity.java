package com.example.carlos.proyectoevents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InicioActivity extends AppCompatActivity {
    Button b;
    TextView sinLogin;
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

    }
}
