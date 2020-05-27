package communication;

import collection.City;
import gui.CitiesTableModel;
import utils.UserInterface;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class ServerReader implements Runnable {
    private Connector connector;
    private UserInterface ui;
    private CitiesTableModel tableModel;

    public ServerReader(Connector connector, UserInterface ui, CitiesTableModel tableModel) {
        this.connector = connector;
        this.ui = ui;
        this.tableModel = tableModel;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            TransferObject response = connector.readResponse(ui);
            if (response.getName().equals("update")){
                tableModel.updateTable((ConcurrentHashMap<String, City>)response.getComplexArgs());
            }
            if (response.getName().equals("login") | response.getName().equals("register")) {
                synchronized (User.class) {
                    if (response.getSimpleArgs()[0].equals("Вход произошел успешно")) {
                        User.setPermission(true);
                    } else User.showError(response.getSimpleArgs()[0]);
                    User.class.notify();
                }
            }
            else ui.write(response.getSimpleArgs()[0]);
        }
    }
}
