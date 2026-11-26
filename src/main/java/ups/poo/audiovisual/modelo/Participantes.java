package ups.poo.audiovisual.modelo;

import java.util.ArrayList;
import java.util.List;

// Lista de personas de un contenido: sin nulos ni duplicados
class Participantes<T extends Persona> {
    private final List<T> personas = new ArrayList<>();

    boolean agregar(T persona) {
        if (persona == null || personas.contains(persona)) {
            return false;
        }
        personas.add(persona);
        return true;
    }

    List<T> comoLista() {
        return List.copyOf(personas);
    }

    String listar() {
        StringBuilder texto = new StringBuilder();
        for (T persona : personas) {
            texto.append("\n  - ").append(persona);
        }
        return texto.toString();
    }
}