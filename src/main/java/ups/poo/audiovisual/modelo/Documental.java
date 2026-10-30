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
    public void mostrarDetalles() {
        System.out.println("Detalles del documental:");
        System.out.println("ID: " + getId());
        System.out.println("Título: " + getTitulo());
        System.out.println("Duración en minutos: " + getDuracionEnMinutos());
        System.out.println("Género: " + getGenero());
        System.out.println("Tema: " + this.tema);
        System.out.println("Investigadores: ");
        for (Investigador investigador : investigadores) {
            System.out.println("  - " + investigador.getNombre());
        }
        System.out.println();
    }
}