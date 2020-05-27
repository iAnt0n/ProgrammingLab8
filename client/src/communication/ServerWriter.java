package communication;

import commands.CommandBuilder;
import exceptions.InvalidArgumentsException;
import utils.UserInterface;

import java.io.IOException;

public class ServerWriter implements Runnable {
    private Connector connector;
    private UserInterface ui;
    private User user;
    private String host;
    private int port;

    public ServerWriter(Connector connector, UserInterface ui, User user, String host, int port) {
        this.connector = connector;
        this.ui = ui;
        this.user = user;
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        while (ui.hasNextLine() && !Thread.currentThread().isInterrupted()) {
            try {
                String cmd = ui.readLine();
                if (cmd.trim().isEmpty()) {
                    continue;
                }
                if (cmd.trim().equals("exit")) {
                    System.out.println("CMD exit check");
                    user = User.getNewUser(ui,connector);
                    continue;
                }
                Object[] cmds = CommandBuilder.getInstance().buildCommand(ui, cmd);
                for (Object o : cmds) {
                    TransferObject.Builder transferObjectBuilder = (TransferObject.Builder) o;
                    TransferObject TO = transferObjectBuilder.setLogin(user.getLogin()).setPassword(user.getPassword()).build();
                    try {
                        connector.sendTO(TO);
                    } catch (IOException e) {
                        ui.writeln("Ошибка при передаче данных. Попробую восстановить соединение");
                        Connector.retainsConnection = false;
                        connector = Connector.connectToServ();
                        ui.writeln("Соединение восстановлено");
                        connector.sendTO(TO);
                    }
                }
            } catch (IOException ignored) {}
            catch (InvalidArgumentsException e) {
                ui.writeln(e.getMessage());
            } catch (NullPointerException e) {
                ui.writeln("Такой команды нет");
            }
        }
    }
}
