package com.example.carlos.proyectoevents;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordActivity extends AppCompatActivity {
    EditText correo;
    Button b;
    private String email = "";
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        b = findViewById(R.id.bt_enviar_pass);
        correo = findViewById(R.id.et_email_pass);
        mAuth = FirebaseAuth.getInstance();
        mDialog=new ProgressDialog(this);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = correo.getText().toString().trim();
                if (!email.isEmpty()) {
                    mDialog.setMessage("Espere un momento...");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    resetPassword();
                } else {
                    Toast.makeText(PasswordActivity.this, "Debe de ingresar el correo", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void resetPassword() {
        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(PasswordActivity.this,"Se ha enviado un correo para restablecer tu contraseña.",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(PasswordActivity.this,InicioActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(PasswordActivity.this,"Error al enviar el correo.",Toast.LENGTH_SHORT).show();
                }
                mDialog.dismiss();
            }
        });
    }
}
