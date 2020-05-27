package resources;

import java.util.ListResourceBundle;

public class ProgramResources_pt extends ListResourceBundle {
    private static final Object[][] contents = {
            {"columnNames", new String[]{"Owner", "Id", "Name", "X", "Y", "Area", "Population", "Meters Above Sea Level", "Climate",
                    "Government", "Standard of living", "Governor Name", "Governor Age", "Governor Height", "Creation Time", "Key"}},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
            {},
    };

    @Override
    public Object[][] getContents() {
        return contents;
    }
}
