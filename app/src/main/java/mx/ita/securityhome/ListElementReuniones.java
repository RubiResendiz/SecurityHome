package mx.ita.securityhome;

public class ListElementReuniones {

    public String date;
    public String hour;
    public String id;

    public ListElementReuniones(String date, String hour,String id){
        this.date = date;
        this.hour = hour;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
