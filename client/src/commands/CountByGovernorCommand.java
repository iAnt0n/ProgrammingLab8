package commands;

import gui.MainJFrame;
import utils.UserInterface;

/**
 * Класс, реализующий команду count_by_governor
 */
public class CountByGovernorCommand extends Command{
    public CountByGovernorCommand(){
        name = "count_by_governor";
        simpleArgLen = 0;
    }

    @Override
    public Object buildArgs(UserInterface ui, String[] simpArgs) {
        MainJFrame.readHuman();
        return ui.readHuman();
    }
}
