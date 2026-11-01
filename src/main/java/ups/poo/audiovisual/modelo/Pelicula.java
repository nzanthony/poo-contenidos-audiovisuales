/**
 * Class Pelicula
 */
package ups.poo.audiovisual.modelo;
import java.util.ArrayList;
import java.util.List;

// Subclase Pelicula que extiende de ContenidoAudiovisual
public class Pelicula extends ContenidoAudiovisual {
    private String estudio;
    private List<Actor> actores;

    public Pelicula(String titulo, int duracionEnMinutos, String genero, String estudio) {
        super(titulo, duracionEnMinutos, genero);
        actores = new ArrayList<Actor>();
        this.estudio = estudio;
    }

    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    public boolean agregarActor(Actor actor) {
        if (actor == null || actores.contains(actor)) {
            return false;
        }
        actores.add(actor);
        return true;
    }

    public List<Actor> getActores() {
        return List.copyOf(actores);
    }
    
    @Override
    public String getTipo() {
        return "Pelicula";
    }

    @Override
    public String toString() {
        String texto = super.toString() + "\nEstudio: " + estudio + "\nActores: ";
        for (Actor actor : actores){
            texto += "\n  - " + actor.getNombre();
        }
        return texto;
    }
}