package ups.poo.audiovisual.controlador;

import java.util.ArrayList;
import java.util.List;

import ups.poo.audiovisual.modelo.Actor;
import ups.poo.audiovisual.modelo.Artista;
import ups.poo.audiovisual.modelo.ContenidoAudiovisual;
import ups.poo.audiovisual.modelo.Investigador;
import ups.poo.audiovisual.vista.VistaConsola;

public class ControladorContenidos {
    private VistaConsola vista;
    private List<ContenidoAudiovisual> contenidos;
    private List<Actor> actores;
    private List<Investigador> investigadores;
    private List<Artista> artistas;

    public ControladorContenidos(VistaConsola vista) {
        this.vista = vista;
        this.contenidos = new ArrayList<>();
        this.actores = new ArrayList<>();
        this.investigadores = new ArrayList<>();
        this.artistas = new ArrayList<>();
    }

    public void iniciar() {
        int opcion;
        do {
            vista.menuPrincipal();
            opcion = vista.leerEntero("Opcion: ", 0, 6);
            switch (opcion) {
                case 1 -> vista.mostrarContenidos(contenidos);
                case 2, 3, 4, 5, 6 -> vista.mostrarMensaje("Opcion en construccion");
                case 0 -> vista.mostrarMensaje("Gracas por probar");
            }
        } while (opcion != 0);
    }

    public void agregarContenido(ContenidoAudiovisual contenido) {
        contenidos.add(contenido);
    }

    public void registrarActor(Actor actor) {
        actores.add(actor);
    }

    public void registrarInvestigador(Investigador investigador) {
        investigadores.add(investigador);
    }

    public void registrarArtista(Artista artista) {
        artistas.add(artista);
    }
}