package com.example.carlos.proyectoevents;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class LoginActivity extends AppCompatActivity {
    private EditText et_email;
    private EditText et_password;
    private Button b,b_inicio;
    private ProgressDialog pd;
    private TextView olvido;

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
        b = findViewById(R.id.bt_registrarse);
        b_inicio=findViewById(R.id.bt_iniciar2);
        pd = new ProgressDialog(this);
        olvido=findViewById(R.id.tv_olvido);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });

        b_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUsuario();
            }
        });

        olvido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,PasswordActivity.class);
                startActivity(i);
            }
        });
    }


    public void registrarUsuario() {
        final String email = et_email.getText().toString().trim();
        final String pas = et_password.getText().toString().trim();
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


        firebaseAuth.createUserWithEmailAndPassword(email, pas).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Se ha registrado con el email: " + email, Toast.LENGTH_LONG).show();
                    Intent i =new Intent(LoginActivity.this,InicioActivity.class);
                    startActivity(i);
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(LoginActivity.this, "Ese usuario ya existe", Toast.LENGTH_LONG).show();
                    } else {
                        if(pas.length()<6){
                            Toast.makeText(LoginActivity.this, "La contraseña debe contener más de 6 caracteres.", Toast.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(LoginActivity.this, "Error en el registro.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                pd.dismiss();
            }

        });
    }

    public void loginUsuario() {
        final String email = et_email.getText().toString().trim();
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


        firebaseAuth.signInWithEmailAndPassword(email, pas).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //asignamos 1 porque hemos iniciado sesion
                    NavigationActivity.login=1;
                  Intent i=new Intent(LoginActivity.this,NavigationActivity.class);
                  startActivity(i);
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(LoginActivity.this, "Ese usuario ya existe", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Error en el inicio de sesión", Toast.LENGTH_LONG).show();

                    }
                }
                pd.dismiss();
            }

        });
    }
}
