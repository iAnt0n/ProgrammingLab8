package resources;

import java.util.ListResourceBundle;

public class ProgramResources_pt extends ListResourceBundle {
    private static final Object[][] contents = {
            {"owner", "Proprietario"},
            {"id", "Id"},
            {"name", "Nome"},
            {"x", "X"},
            {"y", "Y"},
            {"area", "Area"},
            {"population", "Populacao"},
            {"meters", "metros acima do n\u00EDvel do mar"},
            {"climate", "Clima"},
            {"government", "Governo"},
            {"sol", "N\u00EDvel de vida"},
            {"govname", "Nome do Governador"},
            {"govage", "Idade do Governador"},
            {"govhei", "Altura do Governador"},
            {"time", "Criacao"},
            {"key", "Chave"},
            {"applyChanges", "Aplicar as Altera\u00E7\u00F5es"},
            {"removeElement", "Remover"},
            {"changeUser", "Mudar o utilizador"},
            {"enteredAs", "Voc\u00EA entrou como "}
    };

    @Override
    public Object[][] getContents() {
        return contents;
    }
}
