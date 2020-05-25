package communication;

import collection.City;
import gui.CitiesTableModel;
import utils.UserInterface;

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
            else ui.write(response.getSimpleArgs()[0]);
        }
    }
}
