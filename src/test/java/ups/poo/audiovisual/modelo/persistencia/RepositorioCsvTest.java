package ups.poo.audiovisual.modelo.persistencia;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ups.poo.audiovisual.modelo.*;

class RepositorioCsvTest {
    private static final String ENCABEZADO_CONTENIDOS = "id;tipo;titulo;duracion;genero;extra1;extra2\n";

    @TempDir
    Path carpeta;

    private RepositorioCsv repositorio;

    @BeforeEach
    void crearRepositorio() {
        repositorio = new RepositorioCsv(carpeta);
    }

    // Crea un contenido de cada tipo con sus personas y sus partes
    private CatalogoContenidos crearCatalogoDeEjemplo() {
        CatalogoContenidos catalogo = new CatalogoContenidos();
        Actor actor = new Actor("Ryan Gosling", "Canadiense", 30);
        Investigador investigador = new Investigador("Neil deGrasse Tyson", "Estadounidense", "Astrofisico", 68);
        Artista artista = new Artista("Luis Alfonso Rodriguez", "Puertorriqueña", 48, "Luis Fonsi");
        catalogo.registrarPersona(actor);
        catalogo.registrarPersona(investigador);
        catalogo.registrarPersona(artista);

        Pelicula pelicula = new Pelicula("La La Land", 128, "Musical", "Summit");
        pelicula.agregarActor(actor);
        SerieDeTV serie = new SerieDeTV("Game of Thrones", 60, "Fantasia");
        serie.agregarTemporada(1, 10);
        serie.agregarTemporada(2, 10);
        Documental documental = new Documental("Cosmos", 60, "Ciencia", "Universo");
        documental.agregarInvestigador(investigador);
        VideoMusical video = new VideoMusical("Despacito", 5, "Reggaeton", "Vida", 2017);
        video.agregarArtista(artista);
        TransmisionEnVivo transmision = new TransmisionEnVivo("La Velada del Año", 255, "Entretenimiento", "Twitch");
        transmision.agregarSegmento("Previa", 30);
        transmision.agregarSegmento("Combates", 180);

        catalogo.agregarContenido(pelicula);    // ID 0
        catalogo.agregarContenido(serie);       // ID 1
        catalogo.agregarContenido(documental);  // ID 2
        catalogo.agregarContenido(video);       // ID 3
        catalogo.agregarContenido(transmision); // ID 4
        return catalogo;
    }

    @Test
    void guardaYCargaLaMismaCantidadDeDatos() throws IOException {
        repositorio.guardar(crearCatalogoDeEjemplo());
        CatalogoContenidos cargado = repositorio.cargar();

        assertEquals(5, cargado.getContenidos().size());
        assertEquals(3, cargado.getPersonas().size());
        assertTrue(repositorio.getAdvertencias().isEmpty());
    }

    @Test
    void conservaLosTiposYLasRelaciones() throws IOException {
        repositorio.guardar(crearCatalogoDeEjemplo());
        CatalogoContenidos cargado = repositorio.cargar();

        Pelicula pelicula = (Pelicula) cargado.buscarContenidoPorId(0);
        SerieDeTV serie = (SerieDeTV) cargado.buscarContenidoPorId(1);
        VideoMusical video = (VideoMusical) cargado.buscarContenidoPorId(3);
        TransmisionEnVivo transmision = (TransmisionEnVivo) cargado.buscarContenidoPorId(4);

        assertEquals("Ryan Gosling", pelicula.getActores().get(0).getNombre());
        assertEquals(2, serie.getNumeroTemporadas());
        assertEquals("Luis Fonsi", video.getArtistas().get(0).getNombreArtistico());
        assertEquals(210, transmision.calcularDuracionAgenda());
    }

    @Test
    void continuaLaNumeracionDespuesDeCargar() throws IOException {
        repositorio.guardar(crearCatalogoDeEjemplo());
        CatalogoContenidos cargado = repositorio.cargar();
        Pelicula nueva = new Pelicula("Nueva", 100, "Drama", "Estudio");
        cargado.agregarContenido(nueva);

        assertEquals(5, nueva.getId());
    }

    @Test
    void reemplazaElPuntoYComaParaNoRomperLasColumnas() throws IOException {
        CatalogoContenidos catalogo = new CatalogoContenidos();
        catalogo.agregarContenido(new Pelicula("Uno; Dos", 100, "Drama", "Estudio"));
        repositorio.guardar(catalogo);

        CatalogoContenidos cargado = repositorio.cargar();

        assertEquals("Uno, Dos", cargado.buscarContenidoPorId(0).getTitulo());
    }

    @Test
    void sinArchivosCargaUnCatalogoVacio() throws IOException {
        CatalogoContenidos cargado = repositorio.cargar();

        assertTrue(cargado.getContenidos().isEmpty());
        assertEquals(5, repositorio.getAdvertencias().size());
        assertFalse(repositorio.hayFilasOmitidas());
    }

    @Test
    void omiteUnaFilaMalFormadaYCargaElResto() throws IOException {
        Files.writeString(carpeta.resolve("contenidos.csv"), ENCABEZADO_CONTENIDOS
                + "0;PELICULA;La La Land;128;Musical;Summit;\n"
                + "1;PELICULA;Mala;abc;Drama;Estudio;\n");

        CatalogoContenidos cargado = repositorio.cargar();

        assertEquals(1, cargado.getContenidos().size());
        assertTrue(repositorio.getAdvertencias().toString().contains("contenidos.csv, linea 3"));
        assertTrue(repositorio.hayFilasOmitidas());
    }

    @Test
    void omiteUnaFilaConValoresInvalidosParaElModelo() throws IOException {
        Files.writeString(carpeta.resolve("contenidos.csv"), ENCABEZADO_CONTENIDOS
                + "0;PELICULA;La La Land;-5;Musical;Summit;\n"
                + "1;PELICULA;;100;Drama;Estudio;\n");

        CatalogoContenidos cargado = repositorio.cargar();

        assertTrue(cargado.getContenidos().isEmpty());
        String advertencias = repositorio.getAdvertencias().toString();
        assertTrue(advertencias.contains("La duracion debe ser mayor que cero"));
        assertTrue(advertencias.contains("El titulo no puede estar vacio"));
    }

    @Test
    void unaCargaCorrectaReiniciaLasFilasOmitidasDeLaAnterior() throws IOException {
        Files.writeString(carpeta.resolve("contenidos.csv"), ENCABEZADO_CONTENIDOS + "0;PELICULA;Mala;abc;Drama;Estudio;\n");
        repositorio.cargar();
        repositorio.guardar(new CatalogoContenidos());

        repositorio.cargar();

        assertFalse(repositorio.hayFilasOmitidas());
    }

    @Test
    void omiteUnTipoDeContenidoDesconocido() throws IOException {
        Files.writeString(carpeta.resolve("contenidos.csv"), ENCABEZADO_CONTENIDOS
                + "0;PODCAST;Mi podcast;30;Charla;;\n");

        CatalogoContenidos cargado = repositorio.cargar();

        assertTrue(cargado.getContenidos().isEmpty());
        assertTrue(repositorio.getAdvertencias().toString().contains("PODCAST"));
    }

    @Test
    void omiteUnaParticipacionConPersonaInexistente() throws IOException {
        Files.writeString(carpeta.resolve("contenidos.csv"), ENCABEZADO_CONTENIDOS
                + "0;PELICULA;La La Land;128;Musical;Summit;\n");
        Files.writeString(carpeta.resolve("participaciones.csv"), "idContenido;idPersona\n0;99\n");

        CatalogoContenidos cargado = repositorio.cargar();
        Pelicula pelicula = (Pelicula) cargado.buscarContenidoPorId(0);

        assertTrue(pelicula.getActores().isEmpty());
        assertTrue(repositorio.getAdvertencias().toString().contains("no existe la persona 99"));
    }
}