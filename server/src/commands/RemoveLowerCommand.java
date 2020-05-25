package commands;

import DB.CityDB;
import collection.City;
import collection.CollectionManager;
import communication.TransferObject;

import java.sql.SQLException;

/**
 * Класс, реализующий команду remove_lower
 */
public class RemoveLowerCommand extends Command {
    RemoveLowerCommand(){
        name = "remove_lower";
        helpString = "{element} удалить из коллекции все элементы, меньшие, чем заданный";
        argLen = 0;
        type = CommandType.MODIFY;
    }

    @Override
    public TransferObject execute(CollectionManager cm, TransferObject TO) throws SQLException {
        String result = CityDB.removeLower((City) TO.getComplexArgs(),TO.getLogin());
        cm.removeLower((City) TO.getComplexArgs(), TO.getLogin());
        if (!result.isEmpty()) TO.setSimpleArgs(new String[]{"Команда выполнена, но вам было отказано в доступе к объектам City с именами "+result});
        else TO.setSimpleArgs(new String[]{"Команда выполнена"});
        return TO;
    }
}
