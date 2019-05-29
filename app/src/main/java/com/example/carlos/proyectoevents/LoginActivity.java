package com.example.carlos.proyectoevents;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText et_email;
    private EditText et_password;
    private Button b;
    private ProgressDialog pd;

    //declaramos un objecto de FirebaseAuth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        //inicializamos
        firebaseAuth = FirebaseAuth.getInstance();

        //REFERENCIAMOS LOS VIEWS
        et_email = findViewById(R.id.et_correo);
        et_password = findViewById(R.id.password);
        b = findViewById(R.id.bt_iniciar);
        pd = new ProgressDialog(this);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrsrUsuario();
            }
        });
    }

    public void registrsrUsuario() {
        String email = et_email.getText().toString().trim();
        String pas = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Se debe ingresar un email.", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(pas)) {
            Toast.makeText(this, "Se debe ingresar una contraseña.", Toast.LENGTH_LONG).show();
            return;
        }

        pd.setMessage("Realiando registro en linea...");
        pd.show();


        firebaseAuth.createUserWithEmailAndPassword(email,pas).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Registro con éxito.", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(LoginActivity.this, "No se pudo realizar el registro..", Toast.LENGTH_LONG).show();

                }
                pd.dismiss();
            }

        });
    }

}
