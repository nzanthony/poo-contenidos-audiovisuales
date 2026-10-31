package ups.poo.audiovisual.modelo;

public class Artista extends Persona{
    private String nombreArtistico;

    public Artista(String nombre, String nacionalidad, int edad, String nombreArtistico){
        super(nombre, nacionalidad, edad);
        this.nombreArtistico = nombreArtistico;
    }

    public String getNombreArtistico() {
        return nombreArtistico;
    }

    public void setNombreArtistico(String nombreArtistico) {
        this.nombreArtistico = nombreArtistico;
    }
}