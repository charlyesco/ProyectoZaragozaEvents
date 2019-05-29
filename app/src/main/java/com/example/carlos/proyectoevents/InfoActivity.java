package com.example.carlos.proyectoevents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        TextView tv1,tv2,tv3,tv4;

        tv1=findViewById(R.id.tv1);
        tv1.setKeyListener(null);

        tv2=findViewById(R.id.tv2);
        tv2.setKeyListener(null);

        tv3=findViewById(R.id.tv3);
        tv3.setKeyListener(null);

        tv4=findViewById(R.id.tv4);
        tv4.setKeyListener(null);

    }
}
