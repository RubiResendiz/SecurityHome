package mx.ita.securityhome;

import android.graphics.Bitmap;
import android.widget.ImageButton;

public class ListElementResidentes {
    public String id;
    public String txtnomrescard;
    public Bitmap imagencardresidente;
    public String txtcorreorescard;

    public ListElementResidentes(String id, String txtnomrescard, Bitmap imagencardresidente,
                                 String txtcorreorescard){
        this.id=id;
        this.txtnomrescard= txtnomrescard;
        this.imagencardresidente = imagencardresidente;
        this.txtcorreorescard = txtcorreorescard;


    }

    public String getidd() {
        return id;
    }

    public void setidd(String id) {
        this.id = id;
    }

    public String getnomrescard() {
        return txtnomrescard;
    }

    public void setnomrescard(String txtnomrescard) {
        this.txtnomrescard = txtnomrescard;
    }

    public Bitmap getimagencardresidente() {
        return imagencardresidente;
    }

    public void setimagencardresidente(Bitmap imagencardresidente) { this.imagencardresidente = imagencardresidente ;
    }

    public String getcorreorescard() {
        return txtcorreorescard;
    }

    public void setrecuerda(String txtcorreorescard) {
        this.txtcorreorescard = txtcorreorescard;
    }







}
