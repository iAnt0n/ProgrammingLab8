package commands;

import DB.CityDB;
import collection.CollectionManager;
import communication.TransferObject;

import java.sql.SQLException;


/**
 * Класс, реализующий команду remove_key
 */
public class RemoveKeyCommand extends Command {
    RemoveKeyCommand(){
        name = "remove_key";
        helpString = "key удалить элемент из коллекции по его ключу";
        argLen = 1;
        type = CommandType.MODIFY;
    }

    @Override
    public TransferObject execute(CollectionManager cm, TransferObject TO) throws SQLException {
        String key = TO.getSimpleArgs()[0];
        if (cm.getCollection().getCityMap().containsKey(key)) {
            CityDB.removeKey(key,TO.getLogin());
            cm.remove(key, TO.getLogin());
            TO.setSimpleArgs(new String[]{"" + key});
        } else TO.setSimpleArgs(new String[]{"No such key"});
        return TO;
    }
}
