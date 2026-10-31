package ups.poo.audiovisual.app;
import ups.poo.audiovisual.modelo.*;

public class PruebaAudioVisual {
	public static void main(String[] args) {
        System.out.println("=== Catalogo de contenidos audiovisuales ===\n");
        // Crear instancias de Pelicula y agregar actores
        Pelicula laLaLand = new Pelicula("La La Land", 128, "Musical", "Summit Entertainment");
        Actor ryanGosling = new Actor("Ryan Gosling", "Canadiense", 30);
        Actor emmaStone = new Actor("Emma Stone", "Estadounidense", 28);
        laLaLand.agregarActor(ryanGosling);
        laLaLand.agregarActor(emmaStone);
        Pelicula crazyStupidLove = new Pelicula("Crazy, Stupid, Love", 118, "Comedia romántica", "Warner Bros");
        crazyStupidLove.agregarActor(ryanGosling);
        crazyStupidLove.agregarActor(emmaStone);
        // Crear instacias de serie y agregar temporadas
        SerieDeTV got = new SerieDeTV("Game of Thrones",60,"Fantasy");
        got.agregarTemporada(1, 10);
        got.agregarTemporada(2, 10);
        got.agregarTemporada(3, 10);
        //Crear instancias de Documental e investigadores
        Documental cosmos = new Documental("Cosmos", 45, "Ciencia","Astronomia");
        Investigador neil = new Investigador("Neil deGrasse Tyson","Estadounidense","Astrofisico",68);
        cosmos.agregarInvestigador(neil);
        Documental cosmosPossibleWorlds = new Documental("Cosmos: Possible Worlds", 44, "Ciencia", "Astrobiología");
        cosmosPossibleWorlds.agregarInvestigador(neil);
        // Crear instancias de VideoMusical y Artistas
        Artista luisFonsi = new Artista("Luis Alfonso Rodríguez", "Puertorriqueña", 48, "Luis Fonsi");
        Artista daddyYankee = new Artista("Ramón Ayala", "Puertorriqueña", 49, "Daddy Yankee");
        VideoMusical despacito = new VideoMusical("Despacito", 5, "Reguetón", "Vida", 2017);
        despacito.agregarArtista(luisFonsi);
        despacito.agregarArtista(daddyYankee);
        VideoMusical gasolina = new VideoMusical("Gasolina", 3, "Reguetón", "Barrio Fino", 2004);
        gasolina.agregarArtista(daddyYankee);
        // Crear instancias de TransmisionEnVivo y agregar segmentos
        TransmisionEnVivo velada = new TransmisionEnVivo("La Velada del Año", 255, "Entretenimiento", "Twitch");
        velada.agregarSegmento("Previa", 30);
        velada.agregarSegmento("Combates", 180);
        velada.agregarSegmento("Concierto final", 45);
        // Crear instancias de las subclases
        ContenidoAudiovisual[] contenidos = new ContenidoAudiovisual[8];
        contenidos[0] = laLaLand;
        contenidos[1] = crazyStupidLove;
        contenidos[2] = got;
        contenidos[3] = cosmos;
        contenidos[4] = cosmosPossibleWorlds;
        contenidos[5] = despacito;
        contenidos[6] = gasolina;
        contenidos[7] = velada;
        // Mostrar los detalles de cada contenido audiovisual
        for (ContenidoAudiovisual contenido : contenidos) {
            System.out.println(contenido);
            System.out.println();
        }
    }
}