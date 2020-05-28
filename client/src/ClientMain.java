import collection.City;
import commands.CommandBuilder;
import communication.*;
import gui.CitiesTableModel;
import gui.MainJFrame;
import gui.TablePanel;
import gui.VisualJPanel;
import utils.UserInterface;

import java.io.*;
import java.util.Locale;
import java.util.ResourceBundle;
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
        UserInterface ui = new UserInterface(cmdReader, resultWriter, true);

        connector = Connector.connectToServ();

        Locale[] localeArray = {new Locale("ru"), new Locale("en", "ZA"),
                new Locale("ca"), new Locale("pt")};
        Locale locale = localeArray[0];
        connector.sendTO(new TransferObject.Builder().setName("get_table").setSimpleArgs(null)
                .setComplexArgs(null).setLogin(null).setPassword(null).build());
        TransferObject table = connector.readResponse(ui);
        ResourceBundle res = ResourceBundle.getBundle("resources.ProgramResources", locale);
        VisualJPanel visPanel = new VisualJPanel(table,res);
        CitiesTableModel tableModel = new CitiesTableModel(ui, res, table);
        MainJFrame frame = new MainJFrame(visPanel, tableModel, resultReader, cmdWriter, localeArray);
        new Thread(new ServerWriter(connector, ui, host, port)).start();
        new Thread(new ServerReader(connector, ui, tableModel,frame.getVisPanel())).start();

        User.getNewUser(ui,connector);

        frame.setVisible(true);
//        frame.updateVisual();
    }
}
