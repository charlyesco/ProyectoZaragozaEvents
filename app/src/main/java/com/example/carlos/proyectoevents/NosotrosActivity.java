package com.example.carlos.proyectoevents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class NosotrosActivity extends AppCompatActivity {
    TextView tv_preguntas, tv_zaragoza, tv_6, tv_7, tv_8;
    EditText et_1, et_2, et_3, et_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nosotros);
        tv_preguntas = findViewById(R.id.tv_preguntas);
        tv_preguntas.setKeyListener(null);

        tv_zaragoza = findViewById(R.id.tv_zaragoza);
        tv_zaragoza.setKeyListener(null);

        tv_6 = findViewById(R.id.textView6);
        tv_6.setKeyListener(null);

        tv_7 = findViewById(R.id.textView7);
        tv_7.setKeyListener(null);

        tv_8 = findViewById(R.id.textView8);
        tv_8.setKeyListener(null);

        et_1 = findViewById(R.id.editText);
        et_1.setKeyListener(null);

        et_2 = findViewById(R.id.editText2);
        et_2.setKeyListener(null);

        et_3 = findViewById(R.id.editText3);
        et_3.setKeyListener(null);

        et_4 = findViewById(R.id.editText4);
        et_4.setKeyListener(null);


    }
}
