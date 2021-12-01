package mx.ita.securityhome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.Locale;


public class invitadoslista extends AppCompatActivity {
    Button btnAgregar;
    Button btnReunion;
    TextView idioma;
    ImageButton btnEditar;
    boolean espa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitadoslista);
        fragmentInvitado taf = new fragmentInvitado();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout); // get the reference of TabLayout
        TabLayout.Tab firstTab = tabLayout.newTab(); // Create a new Tab names
        idioma = findViewById(R.id.btnCambiarIdioma);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(invitadoslista.this);
        idioma.setText(R.string.bandera);
        espa = true;
        if(preferences.getString("idioma",null).equals("es")){ espa = true; }else{espa = false;}

        idioma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(espa){
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("idioma", "en");
                    editor.commit();
                    Locale localizacion = new Locale("en", "US");
                    Locale.setDefault(localizacion);
                    Configuration config = new Configuration();
                    config.locale = localizacion;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                    Log.i("Cocacola","espuma1");
                    espa = false;

                }else{
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("idioma", "es");
                    editor.commit();
                    Locale localizacion = new Locale("es", "ES");
                    Locale.setDefault(localizacion);
                    Configuration config = new Configuration();
                    config.locale = localizacion;
                    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                    Log.i("Cocacola","espuma1");
                    espa = true;

                }
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });



        firstTab.setText(R.string.invitados); // set the Text for the first Tab
        btnAgregar = findViewById(R.id.btnAddInvitado);
        btnReunion = findViewById(R.id.btnAddReunion);
        //btnEditar = findViewById(R.id.btnEditInvitado);
        ImageButton btnReturn = findViewById(R.id.imageButton2);
        TabLayout.Tab secondTab = tabLayout.newTab(); // Create a new Tab names
        secondTab.setText(R.string.reunion); // set the Text for the first Tab
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(invitadoslista.this, Principal.class);
                startActivity(intent);
            }
        });
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(invitadoslista.this, agregarinvitado.class);
                startActivity(intent);
            }
        });
        btnReunion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(invitadoslista.this, agregarReunion.class);
                startActivity(intent);
            }
        });
        tabLayout.addTab(firstTab);
        tabLayout.addTab(secondTab);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame, taf);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = new fragmentInvitado();
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new fragmentInvitado();
                        break;
                    case 1:
                        fragment = new fragmentReunion();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}