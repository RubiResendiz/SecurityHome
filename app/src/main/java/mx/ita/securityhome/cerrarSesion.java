package mx.ita.securityhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import mx.ita.securityhome.ui.login.LoginActivity;

public class cerrarSesion extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerrar_sesion);

        mAuth = FirebaseAuth.getInstance();
        Log.i("User logged in",""+mAuth.getCurrentUser().getEmail());
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(cerrarSesion.this, LoginActivity.class);
        startActivity(i);
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user!=null){
                    Log.i("User logged in",""+mAuth.getCurrentUser().getEmail());

                }
                else{
                    Log.i("No loggeado",":(");
                }
            }
        };
    }
}