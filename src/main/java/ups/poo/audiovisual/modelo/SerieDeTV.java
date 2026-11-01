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

    public boolean agregarTemporada(int numero, int cantidadEpisodios) {
        for (Temporada temporada : temporadas) {
            if (temporada.getNumero() == numero) {
                return false;
            }
        }
        temporadas.add(new Temporada(numero, cantidadEpisodios));
        return true;
    }

    public int getNumeroTemporadas() {
        return temporadas.size();
    }

    public List<Temporada> getTemporadas() {
        return List.copyOf(temporadas);
    }

    @Override
    public String getTipo() {
        return "Serie de TV";
    }

    @Override
    public String toString() {
        String texto = super.toString() + "\nTemporadas: " + getNumeroTemporadas();
        for (Temporada temporada : temporadas) {
            texto += "\n  - Temporada " + temporada.getNumero() + ": " + temporada.getCantidadEpisodios() + " episodios";
        }
        return texto;
    }
}