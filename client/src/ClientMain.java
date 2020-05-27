import collection.City;
import commands.CommandBuilder;
import communication.*;
import gui.CitiesTableModel;
import gui.MainJFrame;
import gui.TablePanel;
import utils.UserInterface;

import java.io.*;


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

        CitiesTableModel tableModel = new CitiesTableModel(connector, ui);
        TablePanel tablePanel = new TablePanel(tableModel);
        MainJFrame frame = new MainJFrame("TableDemo",tablePanel,resultReader,cmdWriter);
        new Thread(new ServerWriter(connector, ui, host, port)).start();
        new Thread(new ServerReader(connector, ui, tableModel)).start();

        User.getNewUser(ui,connector);


        frame.setVisible(true);

    }
}
