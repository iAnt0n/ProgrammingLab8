package commands;

import DB.ClientDB;
import collection.CollectionManager;
import communication.TransferObject;

import java.sql.SQLException;

public class RegisterCommand extends Command {
    RegisterCommand(){
        name = "register";
        helpString = "service";
        type = CommandType.INFO;
    }

    @Override
    public TransferObject execute(CollectionManager cm, TransferObject TO) throws SQLException {
        TO.setSimpleArgs(new String[]{ClientDB.register(TO.getLogin(), TO.getPassword())});
        return TO;
    }
}
