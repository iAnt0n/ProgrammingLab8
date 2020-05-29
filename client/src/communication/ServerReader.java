package communication;

import collection.City;
import gui.CitiesTableModel;
import gui.VisualJPanel;
import utils.UserInterface;

import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.ConcurrentHashMap;

public class ServerReader implements Runnable {
    private Connector connector;
    private UserInterface ui;
    private CitiesTableModel tableModel;
    private VisualJPanel visPanel;

    public ServerReader(Connector connector, UserInterface ui, CitiesTableModel tableModel, VisualJPanel visPanel) {
        this.tableModel = tableModel;
        this.connector = connector;
        this.visPanel=visPanel;
        this.ui = ui;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            TransferObject response = null;
            try {
                response = connector.readResponse(ui);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (response.getName().equals("TableUpdated")) {
                tableModel.updateTable((ConcurrentHashMap<String, City>) response.getComplexArgs());
                visPanel.updateVisual((ConcurrentHashMap<String, City>) response.getComplexArgs());
            }
            if (response.getName().equals("login") | response.getName().equals("register")) {
                synchronized (User.class) {
                    if (response.getSimpleArgs()[0].equals("Вход произошел успешно")) {
                        User.setPermission(true);
                    } else User.showError(response.getSimpleArgs()[0]);
                    User.class.notify();
                }
            } else if (!response.getName().equals("TableUpdated")) ui.write(response.getSimpleArgs()[0]);
        }
    }
}

