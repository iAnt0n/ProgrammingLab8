package commands;

import DB.CityDB;
import collection.City;
import collection.CollectionManager;
import communication.TransferObject;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Класс, реализующий команду replace_if_lower
 */
public class ReplaceIfLowerCommand extends Command {
    ReplaceIfLowerCommand(){
        name = "replace_if_lower";
        helpString = "key {element} заменить значение по ключу, если новое значение меньше старого";
        argLen = 1;
        type = CommandType.MODIFY;
    }

    @Override
    public TransferObject execute(CollectionManager cm, TransferObject TO) throws SQLException {
        String key = TO.getSimpleArgs()[0];
        City city = (City) TO.getComplexArgs();
        city.setMaxNewId();
        city.setUser(TO.getLogin());
        city.setCreationDate(LocalDateTime.now());
        if (cm.getCollection().getCityMap().containsKey(key)){
            CityDB.replaceIfLower((City) TO.getComplexArgs(), key);
            if (cm.replaceIfLower(key, (City) TO.getComplexArgs(), TO.getLogin())) {
                TO.setSimpleArgs(new String[]{"Замена произошла успешно"});
            } else TO.setSimpleArgs(new String[]{"Новое значение больше старого"});
        }
        else TO.setSimpleArgs(new String[]{"Такого ключа в коллекции нет"});
        return TO;
    }
}
