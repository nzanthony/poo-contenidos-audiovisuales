package ups.poo.audiovisual.modelo;

import java.util.List;

public class Pelicula extends ContenidoAudiovisual {
    private String estudio;
    private final Participantes<Actor> actores = new Participantes<>();

    public Pelicula(String titulo, int duracionEnMinutos, String genero, String estudio) {
        super(titulo, duracionEnMinutos, genero);
        this.estudio = estudio;
    }

    public boolean agregarActor(Actor actor) {
        return actores.agregar(actor);
    }

    public List<Actor> getActores() {
        return actores.comoLista();
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    @Override
    public String getTipo() {
        return "Pelicula";
    }

    @Override
    public String toString() {
        return super.toString() + "\nEstudio: " + estudio + "\nActores: " + actores.listar();
    }
}