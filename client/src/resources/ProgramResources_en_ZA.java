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
            {"removeElement", "Remove"}
    };

    @Override
    public Object[][] getContents() {
        return contents;
    }
}
