package commands;

import gui.MainJFrame;
import utils.UserInterface;

/**
 * Класс, реализующий команду insert
 */
public class InsertCommand extends Command {
    public InsertCommand() {
        name = "insert";
        simpleArgLen = 1;
    }

    @Override
    public Object buildArgs(UserInterface ui, String[] simpArgs) {
        MainJFrame.readCity();
        return ui.readCity();
    }
}
