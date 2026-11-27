package ups.poo.audiovisual.modelo;

import java.util.ArrayList;
import java.util.List;

// Guarda los contenidos y las personas del sistema y les asigna ids unicos
public class CatalogoContenidos {
    private final List<ContenidoAudiovisual> contenidos = new ArrayList<>();
    private final List<Persona> personas = new ArrayList<>();
    // Contadores separados para que contenidos y personas tengan su propia secuencia de ids
    private int siguienteIdContenido = 0;
    private int siguienteIdPersona = 0;

    // Asigna el siguiente id disponible antes de guardar el contenido
    public void agregarContenido(ContenidoAudiovisual contenido) {
        contenido.asignarId(siguienteIdContenido++);
        contenidos.add(contenido);
    }

    // Asigna el siguiente id disponible antes de guardar la persona
    public void registrarPersona(Persona persona) {
        persona.asignarId(siguienteIdPersona++);
        personas.add(persona);
    }

    // Devuelve false si el contenido no estaba en el catalogo
    public boolean eliminarContenido(ContenidoAudiovisual contenido) {
        return contenidos.remove(contenido);
    }

    // Devuelve null si no hay un contenido con ese id
    public ContenidoAudiovisual buscarContenidoPorId(int id) {
        for (ContenidoAudiovisual contenido : contenidos) {
            if (contenido.getId() == id) {
                return contenido;
            }
        }
        return null;
    }

    // Devuelve una copia de solo lectura para que no se modifique la lista desde afuera
    public List<ContenidoAudiovisual> getContenidos() {
        return List.copyOf(contenidos);
    }

    // Devuelve solo las personas de la clase indicada como Actor o Artista
    public <T extends Persona> List<T> getPersonas(Class<T> tipo) {
        List<T> resultado = new ArrayList<>();
        for (Persona persona : personas) {
            // isInstance revisa si la persona es de esa clase y cast la convierte a ese tipo
            if (tipo.isInstance(persona)) {
                resultado.add(tipo.cast(persona));
            }
        }
        return resultado;
    }

    // Conserva el id leido del archivo y ajusta el contador para que los nuevos ids no se repitan
    public void restaurarContenido(ContenidoAudiovisual contenido, int id) {
        if (buscarContenidoPorId(id) != null) {
            throw new IllegalArgumentException("ID de contenido repetido: " + id);
        }
        contenido.asignarId(id);
        contenidos.add(contenido);
        siguienteIdContenido = Math.max(siguienteIdContenido, id + 1);
    }

    // Conserva el id leido del archivo y ajusta el contador para que los nuevos ids no se repitan
    public void restaurarPersona(Persona persona, int id) {
        if (buscarPersonaPorId(id) != null) {
            throw new IllegalArgumentException("ID de persona repetido: " + id);
        }
        persona.asignarId(id);
        personas.add(persona);
        siguienteIdPersona = Math.max(siguienteIdPersona, id + 1);
    }

    // Devuelve null si no hay una persona con ese id
    public Persona buscarPersonaPorId(int id) {
        for (Persona persona : personas) {
            if (persona.getId() == id) {
                return persona;
            }
        }
        return null;
    }

    // Devuelve una copia de solo lectura con todas las personas sin importar su tipo
    public List<Persona> getPersonas() {
        return List.copyOf(personas);
    }
}