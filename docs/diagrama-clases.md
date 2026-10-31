```mermaid
classDiagram
    class ContenidoAudiovisual {
        <<abstract>>
        -contar : int$
        -titulo : String
        -duracionEnMinutos : int
        -genero : String
        -id : int
        +ContenidoAudiovisual(titulo: String, duracionEnMinutos: int, genero: String)
        +mostrarDetalles()* void
    }
    class Pelicula {
        -estudio : String
        +Pelicula(titulo: String, duracionEnMinutos: int, genero: String, estudio: String)
        +agregarActor(actor: Actor) void
        +mostrarDetalles() void
    }
    class SerieDeTV {
        +SerieDeTV(titulo: String, duracionEnMinutos: int, genero: String)
        +agregarTemporada(numero: int, cantidadEpisodios: int) void
        +getNumeroTemporadas() int
        +mostrarDetalles() void
    }
    class Documental {
        -tema : String
        +Documental(titulo: String, duracionEnMinutos: int, genero: String, tema: String)
        +agregarInvestigador(investigador: Investigador) void
        +mostrarDetalles() void
    }
    class VideoMusical {
        -album : String
        -anioLanzamiento : int
        +VideoMusical(titulo: String, duracionEnMinutos: int, genero: String, album: String, anioLanzamiento: int)
        +agregarArtista(artista: Artista) void
        +mostrarDetalles() void
    }
    class TransmisionEnVivo {
        -plataforma : String
        +TransmisionEnVivo(titulo: String, duracionEnMinutos: int, genero: String, plataforma: String)
        +agregarSegmento(titulo: String, duracionEnMinutos: int) void
        +calcularDuracion() int
        +mostrarDetalles() void
    }
    class Persona {
        <<abstract>>
        -nombre : String
        -nacionalidad : String
        -edad : int
        #Persona(nombre: String, nacionalidad: String, edad: int)
    }
    class Actor {
        +Actor(nombre: String, nacionalidad: String, edad: int)
    }
    class Investigador {
        -especialidad : String
        +Investigador(nombre: String, nacionalidad: String, especialidad: String, edad: int)
    }
    class Artista {
        -nombreArtistico : String
        +Artista(nombre: String, nacionalidad: String, edad: int, nombreArtistico: String)
    }
    class Temporada {
        -numero : int
        -cantidadEpisodios : int
        ~Temporada(numero: int, cantidadEpisodios: int)
    }
    class Segmento {
        -titulo : String
        -duracionEnMinutos : int
        ~Segmento(titulo: String, duracionEnMinutos: int)
    }
    class PruebaAudioVisual {
        +main(args: String[]) void$
    }

    ContenidoAudiovisual <|-- Pelicula
    ContenidoAudiovisual <|-- SerieDeTV
    ContenidoAudiovisual <|-- Documental
    ContenidoAudiovisual <|-- VideoMusical
    ContenidoAudiovisual <|-- TransmisionEnVivo
    Persona <|-- Actor
    Persona <|-- Investigador
    Persona <|-- Artista
    Pelicula "0..*" --> "0..*" Actor : actores
    SerieDeTV "1" *-- "0..*" Temporada : temporadas
    Documental "0..*" o-- "0..*" Investigador : investigadores
    VideoMusical "0..*" --> "0..*" Artista : artistas
    TransmisionEnVivo "1" *-- "0..*" Segmento : segmentos
    PruebaAudioVisual ..> ContenidoAudiovisual : usa
    PruebaAudioVisual ..> Pelicula : crea
    PruebaAudioVisual ..> SerieDeTV : crea
    PruebaAudioVisual ..> Documental : crea
    PruebaAudioVisual ..> VideoMusical : crea
    PruebaAudioVisual ..> TransmisionEnVivo : crea
    PruebaAudioVisual ..> Actor : crea
    PruebaAudioVisual ..> Investigador : crea
    PruebaAudioVisual ..> Artista : crea
```