package mx.ita.securityhome;

public class ListElementComentarios {
    public String name;
    public String comentario;
    public int estrellas;

    public ListElementComentarios(String name, String comentario, int estrellas){
        this.name= name;
        this.comentario = comentario;
        this.estrellas = estrellas;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(int estrellas) {
        this.estrellas = estrellas;
    }
}
