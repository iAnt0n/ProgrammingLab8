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
            {"enteredAs", "Voc\u00EA entrou como "},

            {"replace_if_lower", "Substituir se inferior"},
            {"execute_script", "Executar o programa"},
            {"clear", "Desmarcar"},
            {"insert", "Inserir"},
            {"update", "Actualizacao"},
            {"max_by_standard_of_living", "M\u00E1x por n\u00EDvel de Vida"},
            {"remove_by_id", "Remover por Id"},
            {"count_by_governor", "Contagem por Governador"},
            {"remove_lower", "Remover inferior"},
            {"remove_lower_key", "Remover a tecla inferior"},
            {"min_by_population", "Min por popula\u00E7\u00E3o"},
            {"remove_key", "Remover a chave"},
            {"info", "Info", },
    };

    @Override
    public Object[][] getContents() {
        return contents;
    }
}
