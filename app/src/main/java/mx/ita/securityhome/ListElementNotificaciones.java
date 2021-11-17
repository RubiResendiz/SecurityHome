package mx.ita.securityhome;

import android.graphics.Bitmap;

public class ListElementNotificaciones {
    public String txtnotificacion;
    public Bitmap imgnot;
    public String txtalerta;
    public String txtrecuerda;
    public String txtfecha;

    public ListElementNotificaciones(String txtnotificacion, Bitmap imgnot, String txtalerta, String txtrecuerda,String txtfecha ){
        this.txtnotificacion= txtnotificacion;
        this.imgnot = imgnot;
        this.txtalerta = txtalerta;
        this.txtrecuerda = txtrecuerda;
        this.txtfecha = txtfecha;

    }

    public String getNot() {
        return txtnotificacion;
    }

    public void setNot(String txtnotificacion) {
        this.txtnotificacion = txtnotificacion;
    }

    public Bitmap getImgnot() {
        return imgnot;
    }

    public void setImgnot(Bitmap imgnot) {
        this.imgnot = imgnot;
    }

    public String getalerta() {
        return txtalerta;
    }

    public void setalerta(String txtalerta) {
        this.txtalerta = txtalerta;
    }

    public String getrecuerda() {
        return txtrecuerda;
    }

    public void setrecuerda(String txtrecuerda) {
        this.txtrecuerda = txtrecuerda;
    }

    public String getfecha() {
        return txtfecha;
    }

    public void setfecha(String txtfecha) {
        this.txtfecha = txtfecha;
    }





}
