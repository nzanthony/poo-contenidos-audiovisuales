package ups.poo.audiovisual.modelo;

import java.util.List;

public class Documental extends ContenidoAudiovisual {
    private String tema;
    private final Participantes<Investigador> investigadores = new Participantes<>();

    public Documental(String titulo, int duracionEnMinutos, String genero, String tema) {
        super(titulo, duracionEnMinutos, genero);
        this.tema = tema;
    }

    public boolean agregarInvestigador(Investigador investigador) {
        return investigadores.agregar(investigador);
    }

    public List<Investigador> getInvestigadores() {
        return investigadores.comoLista();
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
        return super.toString() + "\nTema: " + tema + "\nInvestigadores: " + investigadores.listar();
    }
}