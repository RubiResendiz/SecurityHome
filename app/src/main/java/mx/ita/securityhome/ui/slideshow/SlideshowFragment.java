package mx.ita.securityhome.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import mx.ita.securityhome.Calificanos;
import mx.ita.securityhome.Contacto;
import mx.ita.securityhome.HowTo;
import mx.ita.securityhome.R;
import mx.ita.securityhome.acercade;

public class SlideshowFragment extends Fragment implements View.OnClickListener {
    private SlideshowViewModel slideshowViewModel;
    Button btnAcercade;
    Button btnContacto;
    Button btnHowToBasic;
    Button btnCal;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        btnAcercade = root.findViewById(R.id.btnAcercade);
        btnAcercade.setOnClickListener(this);
        btnContacto = root.findViewById(R.id.btnContacto);
        btnContacto.setOnClickListener(this);
        btnHowToBasic = root.findViewById(R.id.btnHowTo);
        btnHowToBasic.setOnClickListener(this);
        btnCal = root.findViewById(R.id.btnCalifica);
        btnCal.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnAcercade:
                Intent intent = new Intent(getActivity(), acercade.class);
                startActivity(intent);
                break;
            case R.id.btnContacto:
                Intent contacto = new Intent(getActivity(), Contacto.class);
                startActivity(contacto);
                break;
            case R.id.btnHowTo:
                startActivity(new Intent(getActivity(), HowTo.class));
                break;
            case R.id.btnCalifica:
                startActivity(new Intent(getActivity(), Calificanos.class));
                break;
        }
    }

}