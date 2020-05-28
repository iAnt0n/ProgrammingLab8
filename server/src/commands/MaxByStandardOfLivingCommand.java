package commands;

import collection.CollectionManager;
import communication.TransferObject;

import java.util.NoSuchElementException;

/**
 * Класс, реализующий команду max_by_standard_of_living
 */
public class MaxByStandardOfLivingCommand extends Command {
    MaxByStandardOfLivingCommand(){
        name = "max_by_standard_of_living";
        helpString = "вывести любой объект из коллекции, значение поля standardOfLiving которого является максимальным";
        argLen = 0;
        type = CommandType.INFO;
    }

    @Override
    public TransferObject execute(CollectionManager cm, TransferObject TO) {
        try{
            TO.setSimpleArgs(new String[]{cm.max_by_standard_of_living()});
        }
        catch(NoSuchElementException e){
            TO.setSimpleArgs(new String[]{"No data"});
        }
        return TO;
    }
}
