package commands;

import gui.MainJFrame;
import utils.UserInterface;

/**
 * Класс, реализующий команду remove_lower
 */
public class RemoveLowerCommand extends Command {
    public RemoveLowerCommand() {
        name = "remove_lower";
        simpleArgLen = 0;
    }

    @Override
    public Object buildArgs(UserInterface ui, String[] simpArgs) {
        MainJFrame.readCity();
        return ui.readCity();
    }
}