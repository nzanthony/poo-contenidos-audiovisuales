package ups.poo.audiovisual.controlador;

import java.util.ArrayList;
import java.util.List;

import ups.poo.audiovisual.modelo.Actor;
import ups.poo.audiovisual.modelo.Artista;
import ups.poo.audiovisual.modelo.ContenidoAudiovisual;
import ups.poo.audiovisual.modelo.Documental;
import ups.poo.audiovisual.modelo.Investigador;
import ups.poo.audiovisual.modelo.Pelicula;
import ups.poo.audiovisual.modelo.SerieDeTV;
import ups.poo.audiovisual.modelo.TransmisionEnVivo;
import ups.poo.audiovisual.modelo.VideoMusical;
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
                case 2 -> crearContenido();
                case 3 -> registrarPersona();
                case 4 -> agregarAContenido();
                case 5 -> modificarContenido();
                case 6 -> eliminarContenido();
                case 0 -> vista.mostrarMensaje("Hasta luego");
            }
        } while (opcion != 0);
    }

    // Submenu opcion 2
    private void crearContenido() {
        vista.menuCrearContenidos();
        int tipo = vista.leerEntero("Tipo: ", 0, 5);
        if (tipo == 0) {
            return;
        }
        // Datos comunes a todos los tipos
        String titulo = vista.leerTexto("Titulo: ");
        int duracion = vista.leerEntero("Duracion en minutos: ", 1, Integer.MAX_VALUE);
        String genero = vista.leerTexto("Genero: ");

        ContenidoAudiovisual nuevo = null;
        switch (tipo) {
            case 1 -> {
                String estudio = vista.leerTexto("Estudio: ");
                nuevo = new Pelicula(titulo, duracion, genero, estudio);
            }
            case 2 -> nuevo = new SerieDeTV(titulo, duracion, genero);
            case 3 -> {
                String tema = vista.leerTexto("Tema: ");
                nuevo = new Documental(titulo, duracion, genero, tema);
            }
            case 4 -> {
                String album = vista.leerTexto("Album: ");
                int anio = vista.leerEntero("Año de lanzamiento: ", 1900, 2100);
                nuevo = new VideoMusical(titulo, duracion, genero, album, anio);
            }
            case 5 -> {
                String plataforma = vista.leerTexto("Plataforma: ");
                nuevo = new TransmisionEnVivo(titulo, duracion, genero, plataforma);
            }
        }
        agregarContenido(nuevo);
        vista.mostrarMensaje(nuevo.getTipo() + " \"" + nuevo.getTitulo() + "\" creado con ID " + nuevo.getId());
    }

    // Submenu opcion 3
    private void registrarPersona() {
        vista.menuRegistrarPersona();
        int tipo = vista.leerEntero("Tipo: ", 0, 3);
        switch (tipo) {
            case 1 -> vista.mostrarMensaje("Registrar actor: en construccion");
            case 2 -> vista.mostrarMensaje("Registrar investigador: en construccion");
            case 3 -> vista.mostrarMensaje("Registrar artista: en construccion");
            case 0 -> { }
        }
    }

    // Opcion 4
    private void agregarAContenido() {
        ContenidoAudiovisual contenido = pedirContenido();
        if (contenido == null) {
            return;
        }
        vista.mostrarMensaje("Agregar a " + contenido.getTipo() + ": en construccion");
    }
    
    // Submenu opcion 5
    private void modificarContenido() {
        ContenidoAudiovisual contenido = pedirContenido();
        if (contenido == null) {
            return;
        }
        vista.menuModificarContenido();
        int campo = vista.leerEntero("Campo: ", 0, 3);
        switch (campo) {
            case 1 -> vista.mostrarMensaje("Modificar titulo: en construccion");
            case 2 -> vista.mostrarMensaje("Modificar duracion: en construccion");
            case 3 -> vista.mostrarMensaje("Modificar genero: en construccion");
            case 0 -> { }
        }
    }

    // Opcion 6
    private void eliminarContenido() {
        ContenidoAudiovisual contenido = pedirContenido();
        if (contenido == null) {
            return;
        }
        vista.mostrarMensaje("Eliminar contenido: en construccion");
    }

    // Metodos auxiliares
    private ContenidoAudiovisual buscarContenidoPorId(int id) {
        for (ContenidoAudiovisual contenido : contenidos) {
            if (contenido.getId() == id) {
                return contenido;
            }
        }
        return null;
    }

    private ContenidoAudiovisual pedirContenido() {
        int id = vista.leerEntero("ID del contenido: ");
        ContenidoAudiovisual contenido = buscarContenidoPorId(id);
        if (contenido == null) {
            vista.mostrarError("No existe un contenido con ID " + id);
        }
        return contenido;
    }

    // Metodos para registrar datos
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