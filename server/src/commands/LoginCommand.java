package commands;

import DB.ClientDB;
import collection.CollectionManager;
import communication.TransferObject;

import java.sql.SQLException;

public class LoginCommand extends Command {
    LoginCommand(){
        name = "login";
        helpString = "service";
        type = CommandType.INFO;
    }

    @Override
    public TransferObject execute(CollectionManager cm, TransferObject TO) throws SQLException {
        TO.setSimpleArgs(new String[]{ClientDB.login(TO.getLogin(), TO.getPassword())});
        return TO;
    }
}
