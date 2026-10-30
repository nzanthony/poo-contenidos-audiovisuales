/**
 * Class SerieDeTV
 */
package ups.poo.audiovisual.modelo;
import java.util.ArrayList;
import java.util.List;

// Subclase SerieDeTV que extiende de ContenidoAudiovisual
public class SerieDeTV extends ContenidoAudiovisual {
    private List<Temporada> temporadas;

    public SerieDeTV(String titulo, int duracionEnMinutos, String genero) {
        super(titulo, duracionEnMinutos, genero);
        this.temporadas = new ArrayList<>();
    }

    public void agregarTemporada(int numero, int cantidadEpisodios) {
        Temporada temporada = new Temporada(numero, cantidadEpisodios);
        this.temporadas.add(temporada);
    }

    public int getNumeroTemporadas() {
        return temporadas.size();
    }

    public List<Temporada> getTemporadas() {
        return List.copyOf(temporadas);
    }
    
    @Override
    public void mostrarDetalles() {
        System.out.println("Detalles de la serie de TV:");
        System.out.println("ID: " + getId());
        System.out.println("Título: " + getTitulo());
        System.out.println("Duración en minutos: " + getDuracionEnMinutos());
        System.out.println("Género: " + getGenero());
        System.out.println("Temporadas: " + getNumeroTemporadas());
        for (Temporada temporada : temporadas) {
            System.out.println("  Temporada " + temporada.getNumero() + ": " + temporada.getCantidadEpisodios() + " episodios");
        }
        System.out.println();
    }
}