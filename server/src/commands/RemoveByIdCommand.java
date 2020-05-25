package commands;

import DB.CityDB;
import collection.CollectionManager;
import communication.TransferObject;

import java.sql.SQLException;

public class RemoveByIdCommand extends Command{
    RemoveByIdCommand(){
        name = "remove_by_id";
        helpString = "service";
        argLen = 1;
        type = CommandType.MODIFY;
    }

    @Override
    public TransferObject execute(CollectionManager cm, TransferObject TO) throws SQLException {
        int id = Integer.parseInt(TO.getSimpleArgs()[0]);
        CityDB.removeId(id ,TO.getLogin());
        cm.removeById(id, TO.getLogin());
        return TO;
    }
}
