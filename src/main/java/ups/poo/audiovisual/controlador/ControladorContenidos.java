package ups.poo.audiovisual.controlador;

import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.util.List;

import ups.poo.audiovisual.modelo.Actor;
import ups.poo.audiovisual.modelo.Artista;
import ups.poo.audiovisual.modelo.CatalogoContenidos;
import ups.poo.audiovisual.modelo.ContenidoAudiovisual;
import ups.poo.audiovisual.modelo.Documental;
import ups.poo.audiovisual.modelo.Investigador;
import ups.poo.audiovisual.modelo.Pelicula;
import ups.poo.audiovisual.modelo.Persona;
import ups.poo.audiovisual.modelo.SerieDeTV;
import ups.poo.audiovisual.modelo.TransmisionEnVivo;
import ups.poo.audiovisual.modelo.VideoMusical;
import ups.poo.audiovisual.modelo.persistencia.RepositorioContenidos;
import ups.poo.audiovisual.vista.VistaConsola;

// Coordina la vista de consola con el catalogo en memoria y el repositorio que guarda los datos
public class ControladorContenidos {
    private final VistaConsola vista;
    private CatalogoContenidos catalogo = new CatalogoContenidos();
    private final RepositorioContenidos repositorio;
    // Bloquea el guardado si la carga fallo para no sobrescribir los archivos con un catalogo vacio
    private boolean cargaFallida;
    // Indica si se omitieron filas al cargar para pedir confirmacion antes de guardar
    private boolean hayFilasOmitidas;

    public ControladorContenidos(VistaConsola vista, RepositorioContenidos repositorio) {
        this.vista = vista;
        this.repositorio = repositorio;
    }

    // Repite el menu principal hasta que salir confirme que el programa puede terminar
    public void iniciar() {
        cargarDatos();
        boolean terminar = false;
        while (!terminar) {
            vista.menuPrincipal();
            int opcion = vista.leerEntero("Opcion: ", 0, 7);
            switch (opcion) {
                case 1 -> vista.mostrarContenidos(catalogo.getContenidos());
                case 2 -> crearContenido();
                case 3 -> registrarPersona();
                case 4 -> agregarAContenido();
                case 5 -> modificarContenido();
                case 6 -> eliminarContenido();
                case 7 -> guardarCambios();
                case 0 -> terminar = salir();
            }
        }
    }

    // Reemplaza el catalogo con los datos guardados y muestra los avisos de la carga
    private void cargarDatos() {
        try {
            catalogo = repositorio.cargar();
            vista.mostrarMensaje("Datos cargados: " + catalogo.getContenidos().size() + " contenidos y "
                    + catalogo.getPersonas().size() + " personas");
        } catch (MalformedInputException e) {
            // Se lanza cuando un archivo se guardo con otra codificacion como la que usa Excel
            cargaFallida = true;
            vista.mostrarError("Un archivo de datos no esta en UTF-8. Se inicia con un catalogo vacio y no se podra guardar.");
        } catch (IOException e) {
            cargaFallida = true;
            vista.mostrarError("No se pudieron leer los datos (" + e.getMessage() + "). Se inicia con un catalogo vacio y no se podra guardar.");
        }
        hayFilasOmitidas = repositorio.hayFilasOmitidas();
        for (String advertencia : repositorio.getAdvertencias()) {
            vista.mostrarMensaje("Aviso: " + advertencia);
        }
    }

    // Opcion 2 crea un contenido del tipo elegido en el submenu
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

        // Pide los datos propios de cada tipo antes de crear el objeto
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
            // Evita un NullPointerException si el menu acepta un tipo que el switch no maneja
            default -> {
                vista.mostrarError("Tipo de contenido no valido");
                return;
            }
        }
        catalogo.agregarContenido(nuevo);
        vista.mostrarMensaje(nuevo.getTipo() + " \"" + nuevo.getTitulo() + "\" creado con ID " + nuevo.getId());
    }

    // Opcion 3 registra una persona para luego agregarla a un contenido
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
                catalogo.registrarPersona(new Actor(nombre, nacionalidad, edad));
                vista.mostrarMensaje("Actor \"" + nombre + "\" registrado");
            }
            case 2 -> {
                String especialidad = vista.leerTexto("Especialidad: ");
                catalogo.registrarPersona(new Investigador(nombre, nacionalidad, especialidad, edad));
                vista.mostrarMensaje("Investigador \"" + nombre + "\" registrado");
            }
            case 3 -> {
                String nombreArtistico = vista.leerTexto("Nombre artistico: ");
                catalogo.registrarPersona(new Artista(nombre, nacionalidad, edad, nombreArtistico));
                vista.mostrarMensaje("Artista \"" + nombreArtistico + "\" registrado");
            }
        }
    }

    // Opcion 4 agrega personas o temporadas o segmentos segun el tipo de contenido
    private void agregarAContenido() {
        ContenidoAudiovisual contenido = pedirContenido();
        if (contenido == null) {
            return;
        }
        vista.mostrarMensaje("Agregando a " + contenido.getTipo() + " \"" + contenido.getTitulo() + "\"");

        // El instanceof con variable revisa el tipo real y lo convierte en un solo paso
        if (contenido instanceof Pelicula pelicula) {
            Actor actor = elegirPersona(catalogo.getPersonas(Actor.class), "Actores");
            if (actor != null) {
                if (pelicula.agregarActor(actor)) {
                    vista.mostrarMensaje(actor.getNombre() + " agregado a \"" + pelicula.getTitulo() + "\"");
                } else {
                    vista.mostrarError(actor.getNombre() + " ya esta en \"" + pelicula.getTitulo() + "\"");
                }
            }
        } else if (contenido instanceof Documental documental) {
            Investigador investigador = elegirPersona(catalogo.getPersonas(Investigador.class), "Investigadores");
            if (investigador != null) {
                if (documental.agregarInvestigador(investigador)) {
                    vista.mostrarMensaje(investigador.getNombre() + " agregado a \"" + documental.getTitulo() + "\"");
                } else {
                    vista.mostrarError(investigador.getNombre() + " ya esta en \"" + documental.getTitulo() + "\"");
                }
            }
        } else if (contenido instanceof VideoMusical video) {
            Artista artista = elegirPersona(catalogo.getPersonas(Artista.class), "Artistas");
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

    // Metodo generico que sirve para cualquier lista de personas y devuelve null si esta vacia
    private <T extends Persona> T elegirPersona(List<T> personas, String tipo) {
        if (personas.isEmpty()) {
            vista.mostrarError("No hay " + tipo + " registrados. Registre uno con la opcion 3.");
            return null;
        }
        vista.mostrarMensaje(tipo + " registrados:");
        vista.mostrarPersonas(personas);
        int numero = vista.leerEntero("Elija un numero: ", 1, personas.size());
        // Se resta 1 porque la lista se muestra desde 1 pero los indices empiezan en 0
        return personas.get(numero - 1);
    }

    // Opcion 5 modifica un campo del contenido elegido
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
                // Usa cambiarDuracion para respetar las reglas propias de cada tipo
                int nuevaDuracion = vista.leerEntero("Nueva duracion en minutos: ", 1, Integer.MAX_VALUE);
                if (!contenido.cambiarDuracion(nuevaDuracion)) {
                    vista.mostrarError("La duracion no es valida para este contenido");
                    return;
                }
            }
            case 3 -> contenido.setGenero(vista.leerTexto("Nuevo genero: "));
            case 0 -> { return; }
        }
        vista.mostrarMensaje("Contenido actualizado:");
        vista.mostrarMensaje(contenido.toString());
    }

    // Opcion 6 elimina un contenido despues de pedir confirmacion
    private void eliminarContenido() {
        ContenidoAudiovisual contenido = pedirContenido();
        if (contenido == null) {
            return;
        }
        vista.mostrarMensaje("Se eliminara " + contenido.getTipo() + " \"" + contenido.getTitulo() + "\"");
        int confirmar = vista.leerEntero("Confirmar (1 = Si, 0 = No): ", 0, 1);
        if (confirmar == 1) {
            catalogo.eliminarContenido(contenido);
            vista.mostrarMensaje("Contenido eliminado");
        } else {
            vista.mostrarMensaje("Eliminacion cancelada");
        }
    }

    // Opcion 7 guarda el catalogo y devuelve false si la carga fallo o el usuario cancelo o hubo un error
    private boolean guardarCambios() {
        if (cargaFallida) {
            vista.mostrarError("No se guarda porque los archivos no se pudieron leer y se perderian sus datos");
            return false;
        }
        if (hayFilasOmitidas && !confirmarGuardadoConAvisos()) {
            vista.mostrarMensaje("Guardado cancelado");
            return false;
        }
        try {
            repositorio.guardar(catalogo);
            // Despues de guardar los archivos ya coinciden con el catalogo y no hay filas pendientes
            hayFilasOmitidas = false;
            vista.mostrarMensaje("Cambios guardados");
            return true;
        } catch (IOException e) {
            vista.mostrarError("No se pudieron guardar los cambios: " + e.getMessage());
            return false;
        }
    }

    // Pide confirmacion porque guardar borra de los archivos las filas que se omitieron al cargar
    private boolean confirmarGuardadoConAvisos() {
        vista.mostrarMensaje("Hubo avisos al cargar: las filas omitidas se eliminaran de los archivos.");
        return vista.leerEntero("¿Guardar de todas formas? (1 = Si, 0 = No): ", 0, 1) == 1;
    }

    // Opcion 0 ofrece guardar y devuelve false para volver al menu si el guardado pedido no se completo
    private boolean salir() {
        if (cargaFallida) {
            // No tiene sentido preguntar porque guardar esta bloqueado desde la carga
            vista.mostrarMensaje("Los cambios no se guardan porque los datos no se pudieron leer");
        } else {
            int guardar = vista.leerEntero("¿Guardar los cambios antes de salir? (1 = Si, 0 = No): ", 0, 1);
            if (guardar == 1 && !guardarCambios()) {
                vista.mostrarMensaje("No se guardo y se vuelve al menu para no perder los cambios");
                return false;
            }
        }
        vista.mostrarMensaje("Hasta luego");
        return true;
    }

    // Pide un id al usuario y muestra un error si el contenido no existe
    private ContenidoAudiovisual pedirContenido() {
        int id = vista.leerEntero("ID del contenido: ");
        ContenidoAudiovisual contenido = catalogo.buscarContenidoPorId(id);
        if (contenido == null) {
            vista.mostrarError("No existe un contenido con ID " + id);
        }
        return contenido;
    }
}