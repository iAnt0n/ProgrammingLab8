package commands;

import collection.CollectionManager;
import communication.TransferObject;

import java.util.NoSuchElementException;

/**
 * Класс, реализующий команду min_by_population
 */
public class MinByPopulationCommand extends Command {
    MinByPopulationCommand(){
        name = "min_by_population";
        helpString = "вывести любой объект из коллекции, значение поля population которого является минимальным";
        argLen = 0;
        type = CommandType.INFO;
    }

    @Override
    public TransferObject execute(CollectionManager cm, TransferObject TO) {
        try {
            TO.setSimpleArgs(new String[]{cm.minByPopulation()});
        }
        catch (NoSuchElementException e){
            TO.setSimpleArgs(new String[]{"No Data"});
        }
        return TO;
    }
}
