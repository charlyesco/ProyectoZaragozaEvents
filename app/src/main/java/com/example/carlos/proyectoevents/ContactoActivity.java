package com.example.carlos.proyectoevents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ContactoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        TextView tv_2,tv_4,tv_9,tv_10;

        tv_2=findViewById(R.id.textView2);
        tv_2.setKeyListener(null);
        tv_4=findViewById(R.id.textView4);
        tv_4.setKeyListener(null);
        tv_9=findViewById(R.id.textView9);
        tv_9.setKeyListener(null);
        tv_10=findViewById(R.id.textView10);
        tv_10.setKeyListener(null);

    }
}
