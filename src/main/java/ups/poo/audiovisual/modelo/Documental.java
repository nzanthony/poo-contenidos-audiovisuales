/**
 * Class Documental
 */
package ups.poo.audiovisual.modelo;
import java.util.ArrayList;
import java.util.List;

// Subclase Documental que extiende de ContenidoAudiovisual
public class Documental extends ContenidoAudiovisual {
    private String tema;
    private List<Investigador> investigadores;

    public Documental(String titulo, int duracionEnMinutos, String genero, String tema) {
        super(titulo, duracionEnMinutos, genero);
        investigadores = new ArrayList<Investigador>();
        this.tema = tema;
    }
    public void agregarInvestigador(Investigador investigador){
        investigadores.add(investigador);
    }

    public List <Investigador> getInvestigadores(){
        return List.copyOf(investigadores);
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }
    
    @Override
    public String getTipo() {
        return "Documental";
    }

    @Override
    public String toString() {
        String texto = super.toString() + "\nTema: " + tema + "\nInvestigadores: ";
        for (Investigador investigador : investigadores){
            texto += "\n  - " + investigador.getNombre();
        }
        return texto;
    }
}