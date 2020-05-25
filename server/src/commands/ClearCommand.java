package commands;

import DB.CityDB;
import collection.CollectionManager;
import communication.TransferObject;

import java.sql.SQLException;

/**
 * Класс, реализующий команду clear
 */
public class ClearCommand extends Command {
    ClearCommand(){
        name = "clear";
        helpString = "очистить коллекцию";
        type = CommandType.MODIFY;
    }

    @Override
    public TransferObject execute(CollectionManager cm, TransferObject TO) throws SQLException {
        String result = CityDB.clear(TO.getLogin());
        cm.clear(TO.getLogin());
        if (!result.isEmpty())  TO.setSimpleArgs(new String[]{"Команда выполнена, но вам было отказано в доступе к объектам City с именами "+result});
        else TO.setSimpleArgs(new String[]{"Коллекция очищена"});
        return TO;
    }
}
