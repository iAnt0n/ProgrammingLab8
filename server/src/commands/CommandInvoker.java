package commands;

import collection.CollectionManager;
import communication.TransferObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


/**
 * Реализующий singleton класс, который служит для вызова команд
 */
public class CommandInvoker {
    private static CommandInvoker instance;

    public static CommandInvoker getInstance() {
        if (instance == null) {
            instance = new CommandInvoker();
        }
        return instance;
    }

    private HashMap<String, Command> commands = new HashMap<>();

    public CommandInvoker(){
        addCmd(new InfoCommand());
        addCmd(new ShowCommand());
        addCmd(new ClearCommand());
        addCmd(new InsertCommand());
        addCmd(new RemoveKeyCommand());
        addCmd(new UpdateIdCommand());
        addCmd(new CountByGovernorCommand());
        addCmd(new RemoveLowerCommand());
        addCmd(new RemoveLowerKeyCommand());
        addCmd(new ReplaceIfLowerCommand());
        addCmd(new MinByPopulationCommand());
        addCmd(new MaxByStandardOfLivingCommand());
        addCmd(new HelpCommand());
        addCmd(new LoginCommand());
        addCmd(new RegisterCommand());
        addCmd(new GetTableCommand());
    }

    private void addCmd(Command cmd){
        commands.put(cmd.getName(), cmd);
    }

    Collection<Command> getAllCommands() {
        return commands.values();
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }

    public TransferObject executeCommand(CollectionManager cm, TransferObject TO) throws IOException, ClassNotFoundException {
        Logger log = Logger.getLogger(CommandInvoker.class.getName());
        Command execCmd = commands.get(TO.getName());
        try {
            TransferObject response = execCmd.execute(cm, TO);
            log.info("Команда " + TO.getName() + " обработана. Ответ сформирован");
            return response;
        }
        catch (SQLException e){
            TO.setSimpleArgs(new String[]{e.getMessage()+"\n"+"Прозошла ошибка при работе с базой данных. Повторите попытку"});
            return TO;
        }
    }
}
