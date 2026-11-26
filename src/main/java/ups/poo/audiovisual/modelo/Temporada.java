package ups.poo.audiovisual.modelo;

// Temporada inmutable que pertenece a una SerieDeTV
public class Temporada {
    private final int numero;
    private final int cantidadEpisodios;

    // Constructor sin public para que las temporadas se creen solo a traves de SerieDeTV
    Temporada(int numero, int cantidadEpisodios) {
        this.numero = numero;
        this.cantidadEpisodios = cantidadEpisodios;
    }

    public int getNumero() {
        return numero;
    }

    public int getCantidadEpisodios() {
        return cantidadEpisodios;
    }

    @Override
    public String toString() {
        return "Temporada " + numero + ": " + cantidadEpisodios + " episodios";
    }
}