package ups.poo.audiovisual.modelo;

// Segmento inmutable que pertenece a una TransmisionEnVivo
public class Segmento {
    private final String titulo;
    private final int duracionEnMinutos;

    // Constructor sin public para que los segmentos se creen solo a traves de TransmisionEnVivo
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

    @Override
    public String toString() {
        return titulo + " (" + duracionEnMinutos + " min)";
    }
}
