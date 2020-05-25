package commands;

import collection.CollectionManager;
import communication.TransferObject;

/**
 * Класс, реализующий команду help
 */
public class InfoCommand extends Command {
    InfoCommand(){
        name = "info";
        helpString = "вывести информацию о коллекции";
        argLen = 0;
        type = CommandType.INFO;
    }

    @Override
    public TransferObject execute(CollectionManager cm, TransferObject TO) {
        TO.setSimpleArgs(new String[]{cm.info()});
        return TO;
    }
}
