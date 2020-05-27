import collection.City;
import commands.CommandBuilder;
import communication.*;
import gui.CitiesTableModel;
import gui.MainJFrame;
import gui.TablePanel;
import utils.UserInterface;

import javax.swing.*;
import java.io.*;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public class ClientMain {
    public static void main(String[] args) throws IOException {
        new CommandBuilder();
        String host = null;
        int port = 0;
        Connector connector;
        PipedWriter cmdWriter = new PipedWriter();
        PipedReader cmdReader = new PipedReader(cmdWriter);
        PipedReader resultReader = new PipedReader();
        PipedWriter resultWriter = new PipedWriter(resultReader);
        UserInterface ui = new UserInterface(cmdReader,resultWriter, true);

        connector = Connector.connectToServ();

        User user = User.getNewUser(ui,connector);

        CitiesTableModel tableModel = new CitiesTableModel(connector,user, ui);
        TablePanel tablePanel = new TablePanel(tableModel, user);
        MainJFrame frame = new MainJFrame("TableDemo",tablePanel,resultReader,cmdWriter);
        frame.setVisible(true);

        new Thread(new ServerWriter(connector, ui, user, host, port)).start();
        new Thread(new ServerReader(connector, ui, tableModel)).start();

    }
}
