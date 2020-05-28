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
            {"enteredAs", "Vost\u00E8 va entrar com a "},

            {"replace_if_lower", "Reempla\u00E7a si Menor"},
            {"execute_script", "Executa l'Script"},
            {"clear", "Clara"},
            {"insert", "Inserir"},
            {"update", "Actualitzar"},
            {"max_by_standard_of_living", "Max pel nivell de Vida"},
            {"remove_by_id", "Eliminar Id"},
            {"count_by_governor", "Comte pel Governador"},
            {"remove_lower", "Eliminar Inferior"},
            {"remove_lower_key", "Eliminar Inferior Clau"},
            {"min_by_population", "Min per la Població"},
            {"remove_key", "Eliminar Clau"},
            {"info", "Info"}

    };

    @Override
    public Object[][] getContents() {
        return contents;
    }
}
