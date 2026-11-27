package ups.poo.audiovisual.modelo.persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Consumer;

import ups.poo.audiovisual.modelo.*;

// Guarda y carga el catalogo en cinco archivos CSV separados por punto y coma
public class RepositorioCsv implements RepositorioContenidos {
    private static final String SEPARADOR = ";";
    private static final String CONTENIDOS = "contenidos.csv";
    private static final String PERSONAS = "personas.csv";
    private static final String PARTICIPACIONES = "participaciones.csv";
    private static final String TEMPORADAS = "temporadas.csv";
    private static final String SEGMENTOS = "segmentos.csv";

    private final Path carpeta;
    private final List<String> advertencias = new ArrayList<>();
    // Separado de las advertencias porque un archivo que no existe no es una fila perdida
    private boolean filasOmitidas;

    public RepositorioCsv(Path carpeta) {
        this.carpeta = carpeta;
    }

    @Override
    public List<String> getAdvertencias() {
        return List.copyOf(advertencias);
    }

    @Override
    public boolean hayFilasOmitidas() {
        return filasOmitidas;
    }

    // Lectura de los archivos

    @Override
    public CatalogoContenidos cargar() throws IOException {
        advertencias.clear();
        filasOmitidas = false;
        CatalogoContenidos catalogo = new CatalogoContenidos();
        // Primero se cargan contenidos y personas porque las relaciones los buscan por id
        procesar(CONTENIDOS, fila -> cargarContenido(catalogo, fila));
        procesar(PERSONAS, fila -> cargarPersona(catalogo, fila));
        procesar(PARTICIPACIONES, fila -> cargarParticipacion(catalogo, fila));
        procesar(TEMPORADAS, fila -> cargarTemporada(catalogo, fila));
        procesar(SEGMENTOS, fila -> cargarSegmento(catalogo, fila));
        return catalogo;
    }

    // Lee el archivo linea por linea y convierte cada fila invalida en una advertencia en lugar de detener la carga
    private void procesar(String archivo, Consumer<String[]> cargarFila) throws IOException {
        Path ruta = carpeta.resolve(archivo);
        if (!Files.exists(ruta)) {
            advertencias.add("No existe " + archivo + "; se omite");
            return;
        }
        try (BufferedReader lector = Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {
            // Descarta la primera linea porque es el encabezado
            lector.readLine();
            String linea;
            int numeroLinea = 1;
            while ((linea = lector.readLine()) != null) {
                numeroLinea++;
                if (linea.isBlank()) {
                    continue;
                }
                try {
                    // El limite -1 conserva las columnas vacias del final como extra2 en una pelicula
                    cargarFila.accept(linea.split(SEPARADOR, -1));
                } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
                    // Incluye NumberFormatException y los datos que rechaza el modelo porque ambos son IllegalArgumentException
                    advertencias.add(archivo + ", linea " + numeroLinea + ": " + e.getMessage());
                    filasOmitidas = true;
                }
            }
        }
    }

    private void cargarContenido(CatalogoContenidos catalogo, String[] fila) {
        int id = Integer.parseInt(fila[0]);
        String titulo = fila[2];
        int duracion = Integer.parseInt(fila[3]);
        String genero = fila[4];
        ContenidoAudiovisual contenido = switch (fila[1]) {
            case "PELICULA" -> new Pelicula(titulo, duracion, genero, fila[5]);
            case "SERIE" -> new SerieDeTV(titulo, duracion, genero);
            case "DOCUMENTAL" -> new Documental(titulo, duracion, genero, fila[5]);
            case "VIDEO_MUSICAL" -> new VideoMusical(titulo, duracion, genero, fila[5], Integer.parseInt(fila[6]));
            case "TRANSMISION" -> new TransmisionEnVivo(titulo, duracion, genero, fila[5]);
            default -> throw new IllegalArgumentException("tipo de contenido desconocido: " + fila[1]);
        };
        catalogo.restaurarContenido(contenido, id);
    }

    private void cargarPersona(CatalogoContenidos catalogo, String[] fila) {
        int id = Integer.parseInt(fila[0]);
        String nombre = fila[2];
        String nacionalidad = fila[3];
        int edad = Integer.parseInt(fila[4]);
        Persona persona = switch (fila[1]) {
            case "ACTOR" -> new Actor(nombre, nacionalidad, edad);
            case "INVESTIGADOR" -> new Investigador(nombre, nacionalidad, fila[5], edad);
            case "ARTISTA" -> new Artista(nombre, nacionalidad, edad, fila[5]);
            default -> throw new IllegalArgumentException("tipo de persona desconocido: " + fila[1]);
        };
        catalogo.restaurarPersona(persona, id);
    }

    private void cargarParticipacion(CatalogoContenidos catalogo, String[] fila) {
        ContenidoAudiovisual contenido = buscarContenido(catalogo, fila[0]);
        Persona persona = catalogo.buscarPersonaPorId(Integer.parseInt(fila[1]));
        if (persona == null) {
            throw new IllegalArgumentException("no existe la persona " + fila[1]);
        }
        if (contenido instanceof Pelicula pelicula && persona instanceof Actor actor) {
            pelicula.agregarActor(actor);
        } else if (contenido instanceof Documental documental && persona instanceof Investigador investigador) {
            documental.agregarInvestigador(investigador);
        } else if (contenido instanceof VideoMusical video && persona instanceof Artista artista) {
            video.agregarArtista(artista);
        } else {
            throw new IllegalArgumentException(persona.getNombre() + " no puede participar en " + contenido.getTitulo());
        }
    }

    private void cargarTemporada(CatalogoContenidos catalogo, String[] fila) {
        if (!(buscarContenido(catalogo, fila[0]) instanceof SerieDeTV serie)) {
            throw new IllegalArgumentException("el contenido " + fila[0] + " no es una serie");
        }
        serie.agregarTemporada(Integer.parseInt(fila[1]), Integer.parseInt(fila[2]));
    }

    private void cargarSegmento(CatalogoContenidos catalogo, String[] fila) {
        if (!(buscarContenido(catalogo, fila[0]) instanceof TransmisionEnVivo transmision)) {
            throw new IllegalArgumentException("el contenido " + fila[0] + " no es una transmision");
        }
        if (!transmision.agregarSegmento(fila[1], Integer.parseInt(fila[2]))) {
            throw new IllegalArgumentException("el segmento " + fila[1] + " excede la duracion de la transmision");
        }
    }

    private ContenidoAudiovisual buscarContenido(CatalogoContenidos catalogo, String id) {
        ContenidoAudiovisual contenido = catalogo.buscarContenidoPorId(Integer.parseInt(id));
        if (contenido == null) {
            throw new IllegalArgumentException("no existe el contenido " + id);
        }
        return contenido;
    }

    // Escritura de los archivos

    @Override
    public void guardar(CatalogoContenidos catalogo) throws IOException {
        // Crea la carpeta si no existe para poder guardar la primera vez
        Files.createDirectories(carpeta);
        List<String> contenidos = new ArrayList<>();
        List<String> participaciones = new ArrayList<>();
        List<String> temporadas = new ArrayList<>();
        List<String> segmentos = new ArrayList<>();
        for (ContenidoAudiovisual contenido : catalogo.getContenidos()) {
            contenidos.add(filaContenido(contenido));
            agregarPartes(contenido, participaciones, temporadas, segmentos);
        }
        List<String> personas = new ArrayList<>();
        for (Persona persona : catalogo.getPersonas()) {
            personas.add(filaPersona(persona));
        }
        escribir(CONTENIDOS, "id;tipo;titulo;duracion;genero;extra1;extra2", contenidos);
        escribir(PERSONAS, "id;tipo;nombre;nacionalidad;edad;extra", personas);
        escribir(PARTICIPACIONES, "idContenido;idPersona", participaciones);
        escribir(TEMPORADAS, "idSerie;numero;episodios", temporadas);
        escribir(SEGMENTOS, "idTransmision;titulo;duracion", segmentos);
    }

    // Las columnas extra guardan el dato propio de cada tipo como el estudio o el album y el anio
    private String filaContenido(ContenidoAudiovisual contenido) {
        if (contenido instanceof Pelicula pelicula) {
            return fila(contenido, "PELICULA", pelicula.getEstudio(), "");
        } else if (contenido instanceof SerieDeTV) {
            return fila(contenido, "SERIE", "", "");
        } else if (contenido instanceof Documental documental) {
            return fila(contenido, "DOCUMENTAL", documental.getTema(), "");
        } else if (contenido instanceof VideoMusical video) {
            return fila(contenido, "VIDEO_MUSICAL", video.getAlbum(), video.getAnioLanzamiento());
        } else if (contenido instanceof TransmisionEnVivo transmision) {
            return fila(contenido, "TRANSMISION", transmision.getPlataforma(), "");
        }
        throw new IllegalArgumentException("Tipo de contenido no soportado: " + contenido.getTipo());
    }

    private String fila(ContenidoAudiovisual contenido, String tipo, Object extra1, Object extra2) {
        return unir(contenido.getId(), tipo, contenido.getTitulo(), contenido.getDuracionEnMinutos(),
                contenido.getGenero(), extra1, extra2);
    }

    private String filaPersona(Persona persona) {
        if (persona instanceof Actor) {
            return unir(persona.getId(), "ACTOR", persona.getNombre(), persona.getNacionalidad(), persona.getEdad(), "");
        } else if (persona instanceof Investigador investigador) {
            return unir(persona.getId(), "INVESTIGADOR", persona.getNombre(), persona.getNacionalidad(),
                    persona.getEdad(), investigador.getEspecialidad());
        } else if (persona instanceof Artista artista) {
            return unir(persona.getId(), "ARTISTA", persona.getNombre(), persona.getNacionalidad(),
                    persona.getEdad(), artista.getNombreArtistico());
        }
        throw new IllegalArgumentException("Tipo de persona no soportado");
    }

    private void agregarPartes(ContenidoAudiovisual contenido, List<String> participaciones,
            List<String> temporadas, List<String> segmentos) {
        List<? extends Persona> personas = List.of();
        if (contenido instanceof Pelicula pelicula) {
            personas = pelicula.getActores();
        } else if (contenido instanceof Documental documental) {
            personas = documental.getInvestigadores();
        } else if (contenido instanceof VideoMusical video) {
            personas = video.getArtistas();
        }
        for (Persona persona : personas) {
            participaciones.add(unir(contenido.getId(), persona.getId()));
        }
        if (contenido instanceof SerieDeTV serie) {
            for (Temporada temporada : serie.getTemporadas()) {
                temporadas.add(unir(contenido.getId(), temporada.getNumero(), temporada.getCantidadEpisodios()));
            }
        }
        if (contenido instanceof TransmisionEnVivo transmision) {
            for (Segmento segmento : transmision.getSegmentos()) {
                segmentos.add(unir(contenido.getId(), segmento.getTitulo(), segmento.getDuracionEnMinutos()));
            }
        }
    }

    // Une los valores con el separador y cambia por coma cualquier punto y coma del texto para no romper las columnas
    private String unir(Object... valores) {
        StringJoiner fila = new StringJoiner(SEPARADOR);
        for (Object valor : valores) {
            fila.add(String.valueOf(valor).replace(SEPARADOR, ","));
        }
        return fila.toString();
    }

    private void escribir(String archivo, String encabezado, List<String> filas) throws IOException {
        try (BufferedWriter escritor = Files.newBufferedWriter(carpeta.resolve(archivo), StandardCharsets.UTF_8)) {
            escritor.write(encabezado);
            escritor.newLine();
            for (String fila : filas) {
                escritor.write(fila);
                escritor.newLine();
            }
        }
    }
}