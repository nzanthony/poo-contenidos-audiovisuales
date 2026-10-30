package ups.poo.audiovisual.modelo;

public class Temporada {
    private int numero;
    private int cantidadEpisodios;

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

}