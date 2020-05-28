package commands;

import DB.CityDB;
import collection.CollectionManager;
import communication.TransferObject;

import java.sql.SQLException;

/**
 * Класс, реализующий команду remove_lower_key
 */
public class RemoveLowerKeyCommand extends Command {
    RemoveLowerKeyCommand(){
        name = "remove_lower_key";
        helpString = "key удалить из коллекции все элементы, ключ которых меньше, чем заданный";
        argLen = 1;
        type = CommandType.MODIFY;
    }

    @Override
    public TransferObject execute(CollectionManager cm, TransferObject TO) throws SQLException {
        String result = CityDB.removeLowerKey(TO.getSimpleArgs()[0],TO.getLogin());
        cm.removeLowerKey(TO.getSimpleArgs()[0], TO.getLogin());
        if (!result.isEmpty()) TO.setSimpleArgs(new String[]{""});
        else TO.setSimpleArgs(new String[]{""});
        return TO;
    }
}
