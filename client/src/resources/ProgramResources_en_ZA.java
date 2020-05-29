package resources;

import java.util.ListResourceBundle;

public class ProgramResources_en_ZA extends ListResourceBundle {
    private static final Object[][] contents = {
            {"owner", "Owner"},
            {"id", "Id"},
            {"name", "Name"},
            {"x", "X"},
            {"y", "Y"},
            {"area", "Area"},
            {"population", "Population"},
            {"meters", "Meters Above Sea Level"},
            {"climate", "Climate"},
            {"government", "Government"},
            {"sol", "Standard of living"},
            {"govname", "Governor Name"},
            {"govage", "Governor Age"},
            {"govhei", "Governor Height"},
            {"time", "Creation Time"},
            {"key", "Key"},
            {"applyChanges", "Apply Changes"},
            {"removeElement", "Remove"},
            {"confirm", "Confirm"},
            {"changeUser", "Change user"},
            {"enteredAs", "You entered as "},
            {"requireSimple", "Additional argument required"},
            {"input", "Input"},
            {"invalid", "Invalid"},

            {"replace_if_lower", "Replace if Lower"},
            {"execute_script", "Execute Script"},
            {"clear", "Clear"},
            {"insert", "Insert"},
            {"update", "Update"},
            {"max_by_standard_of_living", "Max by Standard of Living"},
            {"remove_by_id", "Remove by Id"},
            {"count_by_governor", "Count by Governor"},
            {"remove_lower", "Remove Lower"},
            {"remove_lower_key", "Remove Lower Key"},
            {"min_by_population", "Min by Population"},
            {"remove_key", "Remove Key"},
            {"info", "Info"},

            {"vis", "Visualization"},
            {"table", "Table"}
    };

    @Override
    public Object[][] getContents() {
        return contents;
    }
}
