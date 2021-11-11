package mx.ita.securityhome;

import android.content.Intent;
import android.content.res.Configuration;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Locale;

import mx.ita.securityhome.ui.login.LoginActivity;

public class Principal extends AppCompatActivity implements
        GestureOverlayView.OnGesturePerformedListener  {

    private AppBarConfiguration mAppBarConfiguration;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    String userID = mAuth.getCurrentUser().getUid();
    ImageView btnEditar;
    Button btnAgregar;
    private GestureLibrary libreria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        libreria = GestureLibraries.fromRawResource(this,
                R.raw.gesture);
        if (!libreria.load()) {
            finish();
        }

        GestureOverlayView gestosView = (GestureOverlayView)
                findViewById(R.id.gestos);
        gestosView.addOnGesturePerformedListener(this);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        ImageView btnQA = findViewById(R.id.btnQAH);
        btnQA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Principal.this, HowTo.class));
            }
        });
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        btnEditar = findViewById(R.id.btnEditPerfil);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Principal.this, editarPerfil.class);
                startActivity(i);
            }
        });

        ref.child("residentes").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String calle = dataSnapshot.child("calle").getValue(String.class);
                    String numero = dataSnapshot.child("numero").getValue(String.class);

                    TextView txtNombre = (TextView) headerView.findViewById(R.id.txtCalleHeader);

                    String full = calle+ " #" +numero;
                    txtNombre.setText(full);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Principal.this,"Fallo la lectura: " + databaseError.getCode(),Toast.LENGTH_LONG);
            }
        });

    }
    public void onGesturePerformed(GestureOverlayView ov,
                                   Gesture gesto){
        ArrayList<Prediction> predicciones = libreria.recognize(gesto);
        for(Prediction prediccion : predicciones){
            Log.i("ojito",prediccion.name+"" + prediccion.score+"\n");
                startActivity(new Intent(Principal.this, agregarinvitado.class));
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        MenuItem esp = menu.getItem(0), en = menu.getItem(1);
        esp.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                    Locale localizacion = new Locale("es", "ES");
                    Locale.setDefault(localizacion);
                    Configuration config = new Configuration();
                    config.locale = localizacion;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                    Log.i("Cocacola","espuma1");
                return false;
            }
        });
        en.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Locale localizacion = new Locale("en", "US");
                Locale.setDefault(localizacion);
                Configuration config = new Configuration();
                config.locale = localizacion;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                Log.i("Cocacola","espuma2");
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}