package ups.poo.audiovisual.app;

import ups.poo.audiovisual.controlador.ControladorContenidos;
import ups.poo.audiovisual.modelo.*;
import ups.poo.audiovisual.vista.VistaConsola;

public class Main {

    public static void main(String[] args) {
        VistaConsola vista = new VistaConsola();
        ControladorContenidos controlador = new ControladorContenidos(vista);
        cargarDatosIniciales(controlador);
        controlador.iniciar();
    }

    private static void cargarDatosIniciales(ControladorContenidos controlador) {
        // Personas
        Actor ryanGosling = new Actor("Ryan Gosling", "Canadiense", 30);
        Actor emmaStone = new Actor("Emma Stone", "Estadounidense", 28);
        Investigador neil = new Investigador("Neil deGrasse Tyson", "Estadounidense", "Astrofisico", 68);
        Artista luisFonsi = new Artista("Luis Alfonso Rodríguez", "Puertorriqueña", 48, "Luis Fonsi");
        Artista daddyYankee = new Artista("Ramón Ayala", "Puertorriqueña", 49, "Daddy Yankee");

        controlador.registrarActor(ryanGosling);
        controlador.registrarActor(emmaStone);
        controlador.registrarInvestigador(neil);
        controlador.registrarArtista(luisFonsi);
        controlador.registrarArtista(daddyYankee);

        // Películas
        Pelicula laLaLand = new Pelicula("La La Land", 128, "Musical", "Summit Entertainment");
        laLaLand.agregarActor(ryanGosling);
        laLaLand.agregarActor(emmaStone);
        Pelicula crazyStupidLove = new Pelicula("Crazy, Stupid, Love", 118, "Comedia romántica", "Warner Bros");
        crazyStupidLove.agregarActor(ryanGosling);
        crazyStupidLove.agregarActor(emmaStone);

        // Serie
        SerieDeTV got = new SerieDeTV("Game of Thrones", 60, "Fantasy");
        got.agregarTemporada(1, 10);
        got.agregarTemporada(2, 10);
        got.agregarTemporada(3, 10);

        // Documentales
        Documental cosmos = new Documental("Cosmos", 45, "Ciencia", "Astronomia");
        cosmos.agregarInvestigador(neil);
        Documental cosmosPossibleWorlds = new Documental("Cosmos: Possible Worlds", 44, "Ciencia", "Astrobiología");
        cosmosPossibleWorlds.agregarInvestigador(neil);

        // Videos musicales
        VideoMusical despacito = new VideoMusical("Despacito", 5, "Reguetón", "Vida", 2017);
        despacito.agregarArtista(luisFonsi);
        despacito.agregarArtista(daddyYankee);
        VideoMusical gasolina = new VideoMusical("Gasolina", 3, "Reguetón", "Barrio Fino", 2004);
        gasolina.agregarArtista(daddyYankee);

        // Transmisión en vivo
        TransmisionEnVivo velada = new TransmisionEnVivo("La Velada del Año", 255, "Entretenimiento", "Twitch");
        velada.agregarSegmento("Previa", 30);
        velada.agregarSegmento("Combates", 180);
        velada.agregarSegmento("Concierto final", 45);

        controlador.agregarContenido(laLaLand);
        controlador.agregarContenido(crazyStupidLove);
        controlador.agregarContenido(got);
        controlador.agregarContenido(cosmos);
        controlador.agregarContenido(cosmosPossibleWorlds);
        controlador.agregarContenido(despacito);
        controlador.agregarContenido(gasolina);
        controlador.agregarContenido(velada);
    }
}