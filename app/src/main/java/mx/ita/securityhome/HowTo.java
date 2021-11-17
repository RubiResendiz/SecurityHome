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
    private void verificar(){
        if(contador == 0){
            btnReg.setText("Salir");
        }
        if(contador<0 || contador > 4){
            startActivity(new Intent(HowTo.this,Principal.class));
        }
        if(contador == 4){
            btnSig.setText("Finalizar");
        }
        switch (contador){
            case 0:
                mlayout.setBackgroundResource(R.drawable.slide1);
                break;
            case 1:
                mlayout.setBackgroundResource(R.drawable.slide2);
                break;
            case 2:
                mlayout.setBackgroundResource(R.drawable.slide3);
                break;
            case 3:
                mlayout.setBackgroundResource(R.drawable.slide4);
                break;
            case 4:
                mlayout.setBackgroundResource(R.drawable.slide5);
                break;
        }
    }
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