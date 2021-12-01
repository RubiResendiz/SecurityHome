package mx.ita.securityhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import mx.ita.securityhome.ui.login.LoginActivity;

public class registroTemporal extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_temporal);
        mAuth = FirebaseAuth.getInstance();
        TextView correo = findViewById(R.id.txtCorreoTemporal);
        TextView contrasena = findViewById(R.id.txtContrasTemporal);
        Button btnregistro = findViewById(R.id.btnRegistroTemp);
        btnregistro.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String correos = correo.getText().toString();
            String contrasenas = contrasena.getText().toString();
            mAuth.createUserWithEmailAndPassword(correos, contrasenas)
                    .addOnCompleteListener(registroTemporal.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Message", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent myIntent = new Intent(registroTemporal.this, LoginActivity.class);
                                registroTemporal.this.startActivity(myIntent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Message", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(registroTemporal.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }

        });
    }
}