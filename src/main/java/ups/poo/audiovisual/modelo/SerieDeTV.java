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

    // Busca si ya hay una temporada registrada con ese numero
    private boolean existeTemporada(int numero) {
        for (Temporada temporada : temporadas) {
            if (temporada.getNumero() == numero) {
                return true;
            }
        }
        return false;
    }

    // Devuelve false si el numero de temporada ya existe para evitar duplicados
    public boolean agregarTemporada(int numero, int cantidadEpisodios) {
        if (existeTemporada(numero)) {
            return false;
        }
        temporadas.add(new Temporada(numero, cantidadEpisodios));
        return true;
    }

    public int getNumeroTemporadas() {
        return temporadas.size();
    }

    // Devuelve una copia de solo lectura para que no se modifique la lista desde afuera
    public List<Temporada> getTemporadas() {
        return List.copyOf(temporadas);
    }

    @Override
    public String getTipo() {
        return "Serie de TV";
    }

    // Agrega el resumen de temporadas al texto base del contenido
    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder(super.toString());
        texto.append("\nTemporadas: ").append(getNumeroTemporadas());
        for (Temporada temporada : temporadas) {
            texto.append("\n  - ").append(temporada);
        }
        return texto.toString();
    }
}