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
            {"confirm", "Подтвердить"},
            {"changeUser", "Сменить пользователя"},
            {"enteredAs", "Вы вошли в систему как "},
            {"requireSimple", "Требуется дополнительный аргумент"},
            {"input", "Ввод"},
            {"invalid", "Неверное поле"},

            {"replace_if_lower", "Заменить если меньше"},
            {"execute_script", "Выполнить скрипт"},
            {"clear", "Очистить"},
            {"insert", "Добавить"},
            {"update", "Обновить"},
            {"max_by_standard_of_living", "Лучший по уровню жизни"},
            {"remove_by_id", "Удалить по Id"},
            {"count_by_governor", "Посчитать по губернатору"},
            {"remove_lower", "Удалить все меньшие"},
            {"remove_lower_key", "Удалить все с меньшими ключами"},
            {"min_by_population", "Минимум по населению"},
            {"remove_key", "Удалить по ключу"},
            {"info", "Информация"},

            {"vis", "Визуализация"},
            {"table", "Таблица"}
    };

    @Override
    public Object[][] getContents() {
        return contents;
    }

}
