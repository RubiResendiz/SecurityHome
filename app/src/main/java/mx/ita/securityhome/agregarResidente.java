package mx.ita.securityhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import mx.ita.securityhome.ui.login.LoginActivity;

public class agregarResidente extends AppCompatActivity {
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_residente);
        mAuth = FirebaseAuth.getInstance();

        ImageButton btnBack = findViewById(R.id.btnBackAdmin);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        TextView nombre = findViewById(R.id.txtNombreAgregarRes),
                numero = findViewById(R.id.txtNumeroAgregarRes),
                correo = findViewById(R.id.txtCorreoAgregarRes),
                pass = findViewById(R.id.txtPassAgregarRes),
                calle = findViewById(R.id.txtCalleAgregarRes);
        pass.setText(generateRandomPassword(10));
        Button concluir = findViewById(R.id.btnFinalizarResidente);
        concluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = nombre.getText().toString(),
                        num = numero.getText().toString(),
                        cor = correo.getText().toString(),
                        pas = pass.getText().toString(),
                        cal = calle.getText().toString();
                mAuth.createUserWithEmailAndPassword(cor, pas)
                        .addOnCompleteListener(agregarResidente.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("Message", "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Log.i("ID USUARIO NUEVO", mAuth.getUid());
                                    Map<String, Object> map = new HashMap<>();
                                    String IDnuevo = mAuth.getCurrentUser().getUid();
                                    map.put("id", IDnuevo);
                                    map.put("nombre", nom);
                                    map.put("calle",cal);
                                    map.put("numero", num);
                                    map.put("pass",pas);
                                    map.put("url", "lol" );
                                    map.put("access", "RES");
                                    mDatabase.child("residentes").child(IDnuevo).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task2) {
                                            if(task2.isSuccessful()){
                                                Log.i("Todo bien", "Todo bien master");
                                            }else{
                                                Log.w("Error XD 1",task2.getException());
                                            }
                                        }
                                    });
                                    Intent myIntent = new Intent(agregarResidente.this, agregarResidente.class);
                                    agregarResidente.this.startActivity(myIntent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("Message", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(agregarResidente.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(agregarResidente.this, gestionResidentes.class);
                startActivity(intent);
            }
        });
    }
    public static String generateRandomPassword(int len)
    {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }

}