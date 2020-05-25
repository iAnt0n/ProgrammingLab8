package commands;

import collection.CollectionManager;
import communication.TransferObject;

/**
 * Класс, реализующий команду show
 */
public class ShowCommand extends Command {
    ShowCommand(){
        name = "show";
        helpString = "вывести все элементы коллекции в строковом представлении";
        type = CommandType.INFO;
    }

    @Override
    public TransferObject execute(CollectionManager cm, TransferObject TO) {
        TO.setSimpleArgs(new String[]{cm.show()});
        return TO;
    }
}
