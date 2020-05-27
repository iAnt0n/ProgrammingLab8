package resources;

import java.util.ListResourceBundle;

public class ProgramResources_ru extends ListResourceBundle {
    private static final Object[][] contents = {
            {"owner", "Владелец"},
            {"id", "Id"},
            {"name", "Имя"},
            {"x", "X"},
            {"y", "Y"},
            {"area", "Площадь"},
            {"population", "Население"},
            {"meters", "Метров над уровнем моря"},
            {"climate", "Климат"},
            {"government", "Правительство"},
            {"sol", "Уровень жизни"},
            {"govname", "Имя губернатора"},
            {"govage", "Возраст губернатора"},
            {"govhei", "Рост губернатора"},
            {"time", "Время создания"},
            {"key", "Ключ"},
            {"applyChanges", "Подтвердить изменения"},
            {"removeElement", "Удалить"},
            {"changeUser", "Сменить пользователя"},
            {"enteredAs", "Вы вошли в систему как "}
    };

    @Override
    public Object[][] getContents() {
        return contents;
    }

}
