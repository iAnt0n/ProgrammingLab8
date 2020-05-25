import collection.City;
import commands.CommandBuilder;
import communication.*;
import gui.CitiesTableModel;
import gui.MainWindow;
import gui.TablePanel;
import utils.UserInterface;

import javax.swing.*;
import java.io.*;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public class ClientMain {
    public static void main(String[] args) throws IOException, SQLException {
        String host = null;
        int port = 0;
        Connector connector = null;
        UserInterface ui = new UserInterface(new InputStreamReader(System.in), new OutputStreamWriter(System.out), true);

        if (args.length == 2) {
            try {
                host = args[0];
                port = Integer.parseInt(args[1]);
                connector = Connector.connectToServ(host, port, ui);
                ui.writeln("Соединение установлено");
            } catch (IllegalArgumentException e) {
                ui.writeln("Неверные параметры запуска");
                ui.writeln("Usage: java -jar client17.jar <host> <port>");
                System.exit(1);
            }
        } else {
            ui.writeln("Usage: java -jar client17.jar <host> <port>");
            System.exit(1);
        }

        User user = User.getNewUser(ui, connector);
        CommandBuilder cb = new CommandBuilder();
        connector.sendTO(new TransferObject.Builder().setName("get_table").setSimpleArgs(null)
                .setComplexArgs(null).setLogin(user.getLogin()).setPassword(user.getPassword()).build(), ui);
        TransferObject table = connector.readResponse(ui);
        MainWindow frame = new MainWindow("TableDemo", connector, ui, cb);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CitiesTableModel tableModel = new CitiesTableModel();
        tableModel.updateTable((ConcurrentHashMap<String, City>) table.getComplexArgs());
        TablePanel tablePanel = new TablePanel(frame, tableModel, user);

        tablePanel.setOpaque(true); //content panes must be opaque
        frame.setContentPane(tablePanel);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

        new Thread(new ServerWriter(connector, ui, user, host, port)).start();
        new Thread(new ServerReader(connector, ui, tableModel)).start();

    }
}
