package commands;

import DB.CityDB;
import collection.CollectionManager;
import collection.Human;
import communication.TransferObject;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetTableCommand extends Command{
    GetTableCommand(){
        name = "get_table";
        helpString = "service";
        type = CommandType.INFO;
    }

    @Override
    public TransferObject execute(CollectionManager cm, TransferObject TO) throws IOException, ClassNotFoundException, SQLException {
        TO.setComplexArgs(cm.getCollection().getCityMap());
        return TO;
    }
}
