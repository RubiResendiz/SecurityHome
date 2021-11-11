package mx.ita.securityhome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;



public class invitadoslista extends AppCompatActivity {
    Button btnAgregar;
    Button btnReunion;
    ImageButton btnEditar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitadoslista);
        fragmentInvitado taf = new fragmentInvitado();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout); // get the reference of TabLayout
        TabLayout.Tab firstTab = tabLayout.newTab(); // Create a new Tab names
        firstTab.setText("Invitados"); // set the Text for the first Tab
        btnAgregar = findViewById(R.id.btnAddInvitado);
        btnReunion = findViewById(R.id.btnAddReunion);
        //btnEditar = findViewById(R.id.btnEditInvitado);
        ImageButton btnReturn = findViewById(R.id.imageButton2);
        TabLayout.Tab secondTab = tabLayout.newTab(); // Create a new Tab names
        secondTab.setText("Reuniones"); // set the Text for the first Tab
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