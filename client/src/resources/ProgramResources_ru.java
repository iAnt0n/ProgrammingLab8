package resources;

import java.util.ListResourceBundle;

public class ProgramResources_ru extends ListResourceBundle {
    private static final Object[][] contents = {
            {"columnNames", new String[]{"Владелец", "Id", "Имя", "X", "Y", "Площадь", "Население", "Высота над уровнем моря", "Климат",
                    "Правительство", "Уровень жизни", "Имя губернатора", "Возраст губернатора", "Рост губернатора", "Время создания", "Ключ"}},
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
