package ups.poo.audiovisual.modelo;

public class Segmento {
    private String titulo;
    private int duracionEnMinutos;

    Segmento(String titulo, int duracionEnMinutos){
        this.titulo = titulo;
        this.duracionEnMinutos = duracionEnMinutos;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getDuracionEnMinutos() {
        return duracionEnMinutos;
    }
}
