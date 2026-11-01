package ups.poo.audiovisual.controlador;

import java.util.ArrayList;
import java.util.List;

import ups.poo.audiovisual.modelo.Actor;
import ups.poo.audiovisual.modelo.Artista;
import ups.poo.audiovisual.modelo.ContenidoAudiovisual;
import ups.poo.audiovisual.modelo.Documental;
import ups.poo.audiovisual.modelo.Investigador;
import ups.poo.audiovisual.modelo.Pelicula;
import ups.poo.audiovisual.modelo.Persona;
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
        if (tipo == 0) {
            return;
        }

        // Datos comunes a todas las personas
        String nombre = vista.leerTexto("Nombre: ");
        String nacionalidad = vista.leerTexto("Nacionalidad: ");
        int edad = vista.leerEntero("Edad: ", 0, 120);

        switch (tipo) {
            case 1 -> {
                registrarActor(new Actor(nombre, nacionalidad, edad));
                vista.mostrarMensaje("Actor \"" + nombre + "\" registrado");
            }
            case 2 -> {
                String especialidad = vista.leerTexto("Especialidad: ");
                registrarInvestigador(new Investigador(nombre, nacionalidad, especialidad, edad));
                vista.mostrarMensaje("Investigador \"" + nombre + "\" registrado");
            }
            case 3 -> {
                String nombreArtistico = vista.leerTexto("Nombre artistico: ");
                registrarArtista(new Artista(nombre, nacionalidad, edad, nombreArtistico));
                vista.mostrarMensaje("Artista \"" + nombreArtistico + "\" registrado");
            }
        }
    }

    // Opcion 4
    private void agregarAContenido() {
        ContenidoAudiovisual contenido = pedirContenido();
        if (contenido == null) {
            return;
        }
        vista.mostrarMensaje("Agregando a " + contenido.getTipo() + " \"" + contenido.getTitulo() + "\"");

        if (contenido instanceof Pelicula pelicula) {
            Actor actor = elegirPersona(actores, "Actores");
            if (actor != null) {
                if (pelicula.agregarActor(actor)) {
                    vista.mostrarMensaje(actor.getNombre() + " agregado a \"" + pelicula.getTitulo() + "\"");
                } else {
                    vista.mostrarError(actor.getNombre() + " ya esta en \"" + pelicula.getTitulo() + "\"");
                }
            }
        } else if (contenido instanceof Documental documental) {
            Investigador investigador = elegirPersona(investigadores, "Investigadores");
            if (investigador != null) {
                if (documental.agregarInvestigador(investigador)) {
                    vista.mostrarMensaje(investigador.getNombre() + " agregado a \"" + documental.getTitulo() + "\"");
                } else {
                    vista.mostrarError(investigador.getNombre() + " ya esta en \"" + documental.getTitulo() + "\"");
                }
            }
        } else if (contenido instanceof VideoMusical video) {
            Artista artista = elegirPersona(artistas, "Artistas");
            if (artista != null) {
                if (video.agregarArtista(artista)) {
                    vista.mostrarMensaje(artista.getNombreArtistico() + " agregado a \"" + video.getTitulo() + "\"");
                } else {
                    vista.mostrarError(artista.getNombreArtistico() + " ya esta en \"" + video.getTitulo() + "\"");
                }
            }
        } else if (contenido instanceof SerieDeTV serie) {
            int numero = vista.leerEntero("Numero de temporada: ", 1, Integer.MAX_VALUE);
            int episodios = vista.leerEntero("Cantidad de episodios: ", 1, Integer.MAX_VALUE);
            if (serie.agregarTemporada(numero, episodios)) {
                vista.mostrarMensaje("Temporada " + numero + " agregada a \"" + serie.getTitulo() + "\"");
            } else {
                vista.mostrarError("La temporada " + numero + " ya existe en \"" + serie.getTitulo() + "\"");
            }
        } else if (contenido instanceof TransmisionEnVivo transmision) {
            String titulo = vista.leerTexto("Titulo del segmento: ");
            int duracion = vista.leerEntero("Duracion del segmento (min): ", 1, Integer.MAX_VALUE);
            if (transmision.agregarSegmento(titulo, duracion)) {
                vista.mostrarMensaje("Segmento \"" + titulo + "\" agregado a \"" + transmision.getTitulo() + "\"");
            } else {
                vista.mostrarError("El segmento excede la duracion de la transmision");
            }
        }
    }
    //Metodo auxiliar para elegir persona
    private <T extends Persona> T elegirPersona(List<T> personas, String tipo) {
        if (personas.isEmpty()) {
            vista.mostrarError("No hay " + tipo + " registrados. Registre uno con la opcion 3.");
            return null;
        }
        vista.mostrarMensaje(tipo + " registrados:");
        vista.mostrarPersonas(personas);
        int numero = vista.leerEntero("Elija un numero: ", 1, personas.size());
        return personas.get(numero - 1);
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
            case 1 -> contenido.setTitulo(vista.leerTexto("Nuevo titulo: "));
            case 2 -> {
                int nuevaDuracion = vista.leerEntero("Nueva duracion en minutos: ", 1, Integer.MAX_VALUE);
                if (contenido instanceof TransmisionEnVivo transmision && nuevaDuracion < transmision.calcularDuracion()) {
                    vista.mostrarError("La duracion no puede ser menor que la agenda (" + transmision.calcularDuracion() + " min)");
                    return;
                }
                contenido.setDuracionEnMinutos(nuevaDuracion);
            }
            case 3 -> contenido.setGenero(vista.leerTexto("Nuevo genero: "));
            case 0 -> { return; }
        }
        vista.mostrarMensaje("Contenido actualizado:");
        vista.mostrarMensaje(contenido.toString());
    }

    // Opcion 6
    private void eliminarContenido() {
        ContenidoAudiovisual contenido = pedirContenido();
        if (contenido == null) {
            return;
        }
        vista.mostrarMensaje("Se eliminara " + contenido.getTipo() + " \"" + contenido.getTitulo() + "\"");
        int confirmar = vista.leerEntero("Confirmar (1 = Si, 0 = No): ", 0, 1);
        if (confirmar == 1) {
            contenidos.remove(contenido);
            vista.mostrarMensaje("Contenido eliminado");
        } else {
            vista.mostrarMensaje("Eliminacion cancelada");
        }
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