package resources;

import java.util.ListResourceBundle;

public class ProgramResources_ca extends ListResourceBundle {
    private static final Object[][] contents = {
            {"owner", "Propietari"},
            {"id", "Id"},
            {"name", "Nom"},
            {"x", "X"},
            {"y", "Y"},
            {"area", "\u00C0rea"},
            {"population", "Poblaci\u00F3"},
            {"meters", "Metres Sobre el Nivell del Mar"},
            {"climate", "Clima"},
            {"government", "Govern"},
            {"sol", "Nivell de vida"},
            {"govname", "Governador Nom"},
            {"govage", "Governador Edat"},
            {"govhei", "Governor Al\u00E7ada"},
            {"time", "Temps de Creaci\u00F3"},
            {"key", "Clau"},
            {"applyChanges", "Aplica els Canvis"},
            {"removeElement", "Eliminar"},
            {"changeUser", "Canvi d'usuari"},
            {"enteredAs", "Vost\u00E8 va entrar com a "}
    };

    @Override
    public Object[][] getContents() {
        return contents;
    }
}
