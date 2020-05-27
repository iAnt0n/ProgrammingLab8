package commands;


import gui.MainJFrame;
import utils.UserInterface;

/**
 * Класс, реализующий команду update
 */
public class UpdateIdCommand extends Command {
    public UpdateIdCommand(){
        name = "update";
        simpleArgLen = 1;
    }

    @Override
    public Object buildArgs(UserInterface ui, String[] simpArgs) {
        if(MainJFrame.standart)MainJFrame.readCity();
        MainJFrame.standart=false;
        return ui.readCity();
    }
}
