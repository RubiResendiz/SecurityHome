package mx.ita.securityhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;

public class HowTo extends AppCompatActivity {
    String[] fondos={"Slide1","Slide2","Slide3","Slide4","Slide5"};
    byte contador = 0;
    View mlayout;
    Button btnReg, btnSig;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);
        btnReg = findViewById(R.id.btnRegresar);
        btnSig = findViewById(R.id.btnRegresar2);
        mlayout = findViewById(R.id.howToLayout);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador--;
                verificar();
            }
        });
        btnSig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador++;
                verificar();
            }
        });

    }
}